/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.users;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Celia
 */
public class Game implements Serializable {
    private int id; 
    private Integer winner; 
    private List<User> teamA;
    private List<User> teamB; 
    private List<Hero> heroesA; 
    private List<Hero> heroesB; 
    private ReentrantLock heroesLock;

    public Game(int id, List<User> teamA, List<User> teamB, List<Hero> heroesA, List<Hero> heroesB) {
        this.id = id;
        this.winner = null;
        this.teamA = teamA;
        this.teamB = teamB;
        this.heroesA = heroesA; 
        this.heroesB = heroesB; 
        this.heroesLock = new ReentrantLock();
    }
    
    public Game(int id, Integer winner, List<User> teamA, List<User> teamB, List<Hero> heroesA, List<Hero> heroesB) {
        this.id = id;
        this.winner = winner;
        this.teamA = teamA;
        this.teamB = teamB;
        this.heroesA = heroesA; 
        this.heroesB = heroesB; 
        this.heroesLock = new ReentrantLock();
    }
    
    public Game(Game game) {
        this.id = game.getId();
        this.winner = game.getWinner();
        this.teamA = game.getTeamA();
        this.teamB = game.getTeamB();
        this.heroesA = game.getHeroesA(); 
        this.heroesB = game.getHeroesB(); 
        this.heroesLock = new ReentrantLock();
    }

    public List<Hero> getHeroesA() {
        return heroesA;
    }

    public void setHeroesA(List<Hero> heroesA) {
        this.heroesA = heroesA;
    }

    public List<Hero> getHeroesB() {
        return heroesB;
    }

    public void setHeroesB(List<Hero> heroesB) {
        this.heroesB = heroesB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public List<User> getTeamA() {
        return teamA;
    }

    public void setTeamA(List<User> teamA) {
        this.teamA = teamA;
    }

    public List<User> getTeamB() {
        return teamB;
    }

    public void setTeamB(List<User> teamB) {
        this.teamB = teamB;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.winner);
        hash = 67 * hash + Objects.hashCode(this.teamA);
        hash = 67 * hash + Objects.hashCode(this.teamB);
        hash = 67 * hash + Objects.hashCode(this.heroesA);
        hash = 67 * hash + Objects.hashCode(this.heroesB);
        return hash;
    }

    public ReentrantLock getHeroesLock() {
        return heroesLock;
    }

    public void setHeroesLock(ReentrantLock heroesLock) {
        this.heroesLock = heroesLock;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Game other = (Game) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.winner, other.winner)) {
            return false;
        }
        if (!Objects.equals(this.teamA, other.teamA)) {
            return false;
        }
        if (!Objects.equals(this.teamB, other.teamB)) {
            return false;
        }
        if (!Objects.equals(this.heroesA, other.heroesA)) {
            return false;
        }
        if (!Objects.equals(this.heroesB, other.heroesB)) {
            return false;
        }
        return true;
    }
    
    
    
    //seleciona o heroi e consoante o sucesso diz 
    public boolean selectHero(String indexHero, User user){
        int index = Integer.valueOf(indexHero);
        boolean success = false;
        
        if(teamContainsUser(this.teamA, user)){
           success = selectHero(this.teamA, heroesA, user, index);
        }
        else {
            success = selectHero(this.teamB, heroesB, user, index);
        }
        
        return success;
    }
    
    public boolean alternateHero(String indexHero, User user){
        int actualHero = getHeroIndex(user);
        int futureHero = Integer.valueOf(indexHero);
        boolean success = false;
        
        if(teamContainsUser(this.teamA, user)){
           success = alternateHero(this.teamA, heroesA, user, actualHero, futureHero);
        }
        else {
            success = alternateHero(this.teamB, heroesB, user, actualHero, futureHero);
        }
        
        return success;
    }

    private int getHeroIndex(User user) {
        int actualHero;
        Hero h = user.getHero();
        if(teamContainsUser(this.teamA, user)){
            actualHero = heroesA.indexOf(h);
        }
        else {
            actualHero = heroesB.indexOf(h);
        }
        return actualHero;
    }
    
    
    private boolean teamContainsUser(List<User> team, User user){
        boolean exist = false;
        for(User u: team){
            if(u.getUsername().equals(user.getUsername())){
                exist = true;
                break;
            }
        }
        return exist;
    }
    
    private int getIndexTeamUser(List<User> team, User user){
        int index = 0;
        for(User u: team){
            if(u.getUsername().equals(user.getUsername())){
                return index;
            }
            index++;
        }
        return -1;
    }
    
    public boolean isTeamAMyTeam(User user){
        if(this.teamA.contains(user)){
            return true;
        }
        return false;
    }

    private boolean selectHero(List<User> team, List<Hero> heroes, User user, int indexHero) {
        boolean success = false;
        synchronized(heroes.get(indexHero)){
            if (heroes.get(indexHero).isUsed() == false) {
                heroes.get(indexHero).setUsed(true);
                user.setHero(heroes.get(indexHero));
                team.get(getIndexTeamUser(team, user)).setHero(heroes.get(indexHero));
                success = true;
            }
        } 
        return success;
    }

    private boolean alternateHero(List<User> team, List<Hero> heroes, User user, int actualHero, int futureHero) {
        boolean sucess = false;
        if(futureHero == actualHero){
           return true; 
        }
        synchronized(heroes.get(actualHero)){
            synchronized(heroes.get(futureHero)){
                if (heroes.get(futureHero).isUsed() == false ) {
                    heroes.get(futureHero).setUsed(true);
                    user.setHero(heroes.get(futureHero));
                    team.get(getIndexTeamUser(team, user)).setHero(heroes.get(futureHero));
                    heroes.get(actualHero).setUsed(false);
                    sucess = true;
                }
            }
        } 
        return sucess;
    }
    
}
