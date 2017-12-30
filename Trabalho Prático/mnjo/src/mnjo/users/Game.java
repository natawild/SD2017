/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.users;

import java.io.Serializable;
import java.util.List;

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

    public Game(int id, List<User> teamA, List<User> teamB, List<Hero> heroes) {
        this.id = id;
        this.winner = null;
        this.teamA = teamA;
        this.teamB = teamB;
        this.heroes = heroes; 
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
    
    //seleciona o heroi e consoante o sucesso diz 
    public boolean selectHero(String index, User user){
        int i = Integer.valueOf(index);
        boolean sucess = false;
        synchronized(heroes.get(i)){
            if(heroes.get(i).isUsed()==false){
                heroes.get(i).setUsed(true);
                user.setHero(heroes.get(i));
                sucess = true;
            }
        }
        return sucess;
    }
    
}
