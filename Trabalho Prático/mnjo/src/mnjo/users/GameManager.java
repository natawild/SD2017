/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import mnjo.client.Timeout;
import mnjo.exceptions.DuplicatedUserException;
import mnjo.exceptions.InvalidCredentialsException;
import mnjo.server.MainServer;

/**
 *
 * @author Celia
 */

//esta classe servirá para armazenar e gerir todos os dados 
public class GameManager implements Serializable{
    
    /*Map com todos os utilizadores registados*/
    private Map<String, User> users;
    
    /*Lista com os herois*/
    private List<Hero> heroes; 
    private ReentrantLock usersLock;
    private Map<Integer,List<User>> wantGame; 
    private ReentrantLock wantTeamLock; 
    private Condition wantTeamCondition; 
    private int gameNumber;
    private Map<Integer, Game> games; 
    private ReentrantLock gamesLock;


    public GameManager() {
        this.users = new HashMap<>();
        this.wantGame=new HashMap<>();
        this.usersLock = new ReentrantLock();
        this.wantTeamLock = new ReentrantLock();
        this.wantTeamCondition = wantTeamLock.newCondition(); 
        this.gameNumber=1;
        this.games= new HashMap<>();
        this.heroes=new ArrayList<>(); 
        this.gamesLock = new ReentrantLock();
    }

     public Map<Integer, Game> getGames() {
        return games;
    }

    synchronized public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }
    
    public Map<String, User> getUsers() {
        //Se fosse em POO tinhas que ter cuidado com isto que estas a fazer. Precisavas de devolver uma copia da lista e nao a propria lista (um clone do Map)
        return users;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }
    
    public List<Hero> getHeroesClone (){
        List<Hero> clonedList = new ArrayList<>();
        for(Hero h: heroes){
            clonedList.add(h.clone());
        }
        return clonedList; 
    }
    
    public void addHero (Hero hero){
        this.heroes.add(hero); 
    }
    
    public Game getGame(int id){
        Game game;
        this.gamesLock.lock();
        try{
           game = this.games.get(id);
        }finally{
            this.gamesLock.unlock();
        }
        return game;
    }
    
    private int incrementorGameNumber(){
        int i = gameNumber;
        gameNumber++; 
        return i; 
    }
    
    
    /**
     * Adicionar um utilizador a base de dados
     * @param user: utilizador que se pretende adiconar
     * @throws mnjo.exceptions.DuplicatedUserException
     */
    public void registerUser(User user) throws DuplicatedUserException{
        this.usersLock.lock();
        try {
            //TODO: CUidado com este codigo se tiveres varias thereds podes ter problemas. O que fazer aqui? :)
            if(users.get(user.getUsername()) != null){
                throw new DuplicatedUserException("Utilzador ja esta registado");
            }
            else {
               users.put(user.getUsername(), user);
            }
        } finally {
            this.usersLock.unlock();
        }
        
    }
    
    /**
     * Adicionar um utilizador a base de dados
     * @param username: nome do utilizador que se pretende adiconar
     * @param password: password do utilizador que se pretende adiconar
     * @return 
     * @throws mnjo.exceptions.DuplicatedUserException 
     */
    public User registerUser(String username, String password) throws DuplicatedUserException{
        User user = null;
        this.usersLock.lock();
        try {
            //TODO: CUidado com este codigo se tiveres varias thereds podes ter problemas. O que fazer aqui? :)
            if(users.get(username) != null){
                throw new DuplicatedUserException("Utilzador ja esta registado");
            }
            else {
               user = new User(username, password);
               users.put(user.getUsername(), user);
            }
        } finally {
            this.usersLock.unlock();
        }
        
        return user;
    }

    public User login(String username, String password) throws InvalidCredentialsException{
        User user;
        this.usersLock.lock();
        try{
            user = users.get(username);
            if(user != null){
                if(user.getPassword().equals(password)){
                    return user;
                }
                else {
                   throw new InvalidCredentialsException("Credenciais incorrectas"); 
                }
            }
            else {
                throw new InvalidCredentialsException("Credenciais incorrectas");
            }
        }
        finally{
            this.usersLock.unlock();
        }
        
    }
    
    //depois de adicionar o utilizador a esta lista devem ser acordadas as threads que 
    //estão á espera para formar equipa
    public void addWantGamePlayer(User user){
        this.wantTeamLock.lock();
        try{
        List<User> usersWithSameRate = this.wantGame.get(user.getRate()); 
        if(usersWithSameRate==null){
            usersWithSameRate= new ArrayList<>();
            this.wantGame.put(user.getRate(), usersWithSameRate);
        }
        usersWithSameRate.add(user); 
        }finally{
            this.wantTeamLock.unlock();
        }        
    }
    
    public void findTeam(User user){
        List<User> team = new ArrayList<>(); 
        team.add(user);
        this.wantTeamLock.lock();
        try{
            searchForTeam(user, team); 
            searchForTeamWithVariation(user, team, -1);
            searchForTeamWithVariation(user, team, +1);
  
                
            if (isTeamFull(team)) { //se a equipa estiver preenchida -> utima thread (a que encontra ) ira executar este codigo
                //altera o estado de cada jogador da equipa para não está espera de formar equipa
                updateWaitingTeamStatus(team);  
                //cria o objeto jogo e passa a equipa
                Game game = saveTeam(team);
                //remove os elementos da equipa que estavam à espera de formar equipa
                removeTeamFromWaitingList(team);
                //criar classe timeout 
                Timeout timer = new Timeout(MainServer.TIMEOUT, game);
                this.wantTeamCondition.signalAll();
                timer.start();
            }
            else {
                //adicionar jogador à lista que prentendem equipa
                this.addWantGamePlayer(user);
                //alterar o estado para à espera de encontrar equipa
                user.setWaiting(true);
                while(user.getWaiting()==true){
                    this.wantTeamCondition.await();
                }
            }
        }
        catch (InterruptedException ex) {        
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, "Erro ao tentar adormecer Thread", ex);
        }        
        finally{
            this.wantTeamLock.unlock();    
        }
    }

    private static boolean isTeamFull(List<User> team) {
        return team.size() == MainServer.TEAM_SIZE;
    }

    private static boolean notHaveTeam(boolean haveTeam) {
        return haveTeam==false;
    }

    private void updateWaitingTeamStatus(List<User> team) {
        for(User u : team){
            u.setWaiting(false);
        }
    }

    private void searchForTeam(User user, List<User> team) {
        List<User> usersWithSameRate = this.wantGame.get(user.getRate());
        int usersWithSameRateSize = 0;
        if(usersWithSameRate != null){
            usersWithSameRateSize = usersWithSameRate.size();
        }
        if(usersWithSameRateSize>0){
            for(int i =0; i<usersWithSameRateSize && team.size()<MainServer.TEAM_SIZE; i++){
                User u = usersWithSameRate.get(i);
                if(u.getWaiting() == true){
                    team.add(u);
                }
            }
        }
    }

    private void searchForTeamWithVariation(User user, List<User> team, int variation) {
        List<User> usersWithVariationRate = this.wantGame.get(user.getRate() + variation);
        int usersWithVariationRateSize=0;
        if(usersWithVariationRate!=null){
            usersWithVariationRateSize = usersWithVariationRate.size();
        }
        if(team.size()<MainServer.TEAM_SIZE && usersWithVariationRateSize>0){
            for(int i =0; i< MainServer.TEAM_SIZE - team.size() && i < usersWithVariationRateSize; i++){
                User u = usersWithVariationRate.get(i);
                if(u.getWaiting() == true){
                    team.add(u);
                }
            }
        }
    }

    private Game saveTeam(List<User> team) {
        Game game =null;
        this.gamesLock.lock();
        try{ 
            List<User> teamA = new ArrayList<>();  
            List<User> teamB = new ArrayList<>();  

            int gameId=incrementorGameNumber();

            for(int i=0; i<team.size();i=i+2){
                team.get(i).setGameId(gameId);
                teamA.add(team.get(i).clone());
                team.get(i+1).setGameId(gameId);
                teamB.add(team.get(i+1).clone());
            }
            
            game = new Game(gameId, teamA, teamB, getHeroesClone(), getHeroesClone()); 
            this.games.put(game.getId(), game); 
        } finally{
            this.gamesLock.unlock();
        }
        return game;  
    }

    public void upateRate(User user) {
        this.usersLock.lock();
            try{
            Game game = this.getGame(user.getGameId()); 
            if(getMyTeam(game, user) == game.getWinner()){
                user.upadteRate(9);
            }
            else {
                user.upadteRate(0);
            }
        }finally{
            this.usersLock.unlock();
        }
    }
    
    public boolean myTeamWin(User u){
        this.gamesLock.lock();
        try{
            Game game = this.getGame(u.getGameId()); 
            if(getMyTeam(game, u) == game.getWinner()){
                return true;
            }
            else {
                return false;
            }
        }finally{
            this.gamesLock.unlock();
        }
    }
    
    
    private int getMyTeam(Game game , User user){
        if(game.getTeamA().contains(user)){
            return 0;
        }
        else {
            return 1;
        }
    }

    private void removeTeamFromWaitingList(List<User> team) {
        for(User u: team){
           //this.wantGame.remove(team);
           List<User> list = wantGame.get(u.getRate());
           list.remove(u);
        }   
       
    }
  
}
