/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.client;

import java.util.logging.Level;
import java.util.logging.Logger;
import mnjo.users.Game;

/**
 *
 * @author Celia
 */
public class Timeout extends Thread {

    private int seconds;
    private Game game;

    public Timeout(int seconds, Game game) {
        this.seconds = seconds;
        this.game= game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    
    
    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public void run() {
        try {
            this.sleep(seconds * 1000);
            game.abortGame();
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao adormecer a thread ", ex);
        }
    }
}
