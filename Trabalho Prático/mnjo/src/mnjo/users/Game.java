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
    private List<Hero> heroes; 
    private ReentrantLock heroesLock;

    public Game(int id, List<User> teamA, List<User> teamB, List<Hero> heroes) {
        this.id = id;
        this.winner = null;
        this.teamA = teamA;
        this.teamB = teamB;
        this.heroes = heroes; 
        this.heroesLock = new ReentrantLock();
    }
    
    public Game(int id, Integer winner, List<User> teamA, List<User> teamB, List<Hero> heroes) {
        this.id = id;
        this.winner = winner;
        this.teamA = teamA;
        this.teamB = teamB;
        this.heroes = heroes; 
        this.heroesLock = new ReentrantLock();
    }
    
    public Game(Game game) {
        this.id = game.getId();
        this.winner = game.getWinner();
        this.teamA = game.getTeamA();
        this.teamB = game.getTeamB();
        this.heroes = game.getHeroes(); 
        this.heroesLock = new ReentrantLock();
    }
    
    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
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
        hash = 67 * hash + Objects.hashCode(this.heroes);
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
        if (!Objects.equals(this.heroes, other.heroes)) {
            return false;
        }
        return true;
    }
    
    
    
    //seleciona o heroi e consoante o sucesso diz 
    public boolean selectHero(String index, User user){
        int i = Integer.valueOf(index);
        boolean sucess = false;
        synchronized(heroes.get(i)){
            if (heroes.get(i).isUsed() == false) {
                heroes.get(i).setUsed(true);
                synchronized(teamA){
                       synchronized(teamB){
                            if (teamContainsUser(teamA, user)) {
                                teamA.get(getIndexTeamUser(teamA, user)).setHero(heroes.get(i));
                            } else {
                                teamB.get(getIndexTeamUser(teamB, user)).setHero(heroes.get(i));
                            } 
                       }
                }
                sucess = true;
            }
        } 
        return sucess;
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
    
}
