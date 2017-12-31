/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.users;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Celia
 */
public class User implements Serializable{

    private String username;
    private String password;
    private Integer rate;
    private Boolean waiting;
    private Integer gameId; 
    private Hero hero; 
    private int numberOfGames; 
    private int accumRate; 

    
    public User() {
        this.username = null;
        this.password = null;
        this.rate = 0;
        this.waiting = false;
        this.gameId=null; 
        this.hero= null;
        this.numberOfGames=0; 
        this.accumRate=0;
    }

    public User(User c) {
        this.username = c.getUsername();
        this.password = c.getPassword();
        this.rate = c.getRate();
        this.waiting = c.getWaiting();
        this.gameId=c.getGameId();
        this.hero=c.getHero();
        this.numberOfGames=c.getNumberOfGames(); 
        this.accumRate=c.getAccumRate();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.rate = 0;
        this.waiting=false; 
        this.gameId=null; 
        this.hero=null;
        this.numberOfGames=0;
        this.accumRate=0;
    }
    
    
    public User(String username, String password, Integer rate, boolean waiting, Integer gameId, Hero hero, int numberOfGames, int accumRate) {
        this.username = username;
        this.password = password;
        this.rate = rate;
        this.waiting = waiting;
        this.gameId = gameId;
        this.hero = hero;
        this.numberOfGames = numberOfGames;
        this.accumRate = accumRate;
    }

    public int getAccumRate() {
        return accumRate;
    }

    public void setAccumRate(int accumRate) {
        this.accumRate = accumRate;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean getWaiting() {
        return this.waiting;
    }

    public void setWaiting(boolean w) {
        this.waiting = w;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Username: ");
        string.append(username).append("\n");
        string.append("password: ");
        string.append(password).append("\n");
        return string.toString();
    }
    
    public String printUser(){
        StringBuilder string = new StringBuilder();
        string.append("Username: ");
        string.append(username); 
        if(hero!=null){
            string.append(", Heroi: "); 
            string.append(hero.printHero());  
        }
        else {
            string.append(", Heroi: Ainda nao esta selecionado"); 
        }
        return string.toString(); 
    }
    
    //Atualiza rating do jogador 
    public void upadteRate(int x){
        accumRate=accumRate+x;
        rate =(accumRate)/(numberOfGames++); 
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        User u = (User) obj;
        return u.getUsername().equals(this.username) && u.getPassword().equals(this.password);

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.username);
        hash = 41 * hash + Objects.hashCode(this.password);
        return hash;
    }

    public User clone() {
        return new User(this);
    }
}
