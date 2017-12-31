/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mnjo.exceptions.DuplicatedUserException;
import mnjo.users.GameManager;
import mnjo.users.Hero;
import mnjo.users.User;

/**
 *
 * @author Celia
 */
public class MainServer {
    public static final int TEAM_SIZE = 4;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Server server = new Server(12345);
       int clientNumber = 0;
       GameManager gameManager = initGameManager();
        
       try {
            server.startServer();
          
            while (true) {
               ServerThread serverThread = new ServerThread(gameManager ,server.accept(), clientNumber++);
               serverThread.start();
            }
        }
        catch (IOException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, "Erro ao iniciar servidor", ex);
        }
        finally {
           try {
               server.closeServer();
           } catch (IOException ex) {
               Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, "Erro ao fechar servidor", ex);
           }
        }
    }
    
    private static GameManager initGameManager(){
        GameManager gameManager = new GameManager();
        
        //Dados de teste
        User user1 = new User ("luis", "luis");
        User user2 = new User ("celia", "celia");
        User user3 = new User ("adriana", "adriana");
        User user4 = new User ("marcia", "marcia");
        Hero h1 = new Hero(Hero.HERO_NAME1, false);
        Hero h2 = new Hero(Hero.HERO_NAME2, false);
        Hero h3 = new Hero(Hero.HERO_NAME3, false);
        Hero h4 = new Hero(Hero.HERO_NAME4, false);
        Hero h5 = new Hero(Hero.HERO_NAME5, false);
        Hero h6 = new Hero(Hero.HERO_NAME6, false);
        Hero h7 = new Hero(Hero.HERO_NAME7, false);
        Hero h8 = new Hero(Hero.HERO_NAME8, false);
        Hero h9 = new Hero(Hero.HERO_NAME9, false);
        Hero h10 = new Hero(Hero.HERO_NAME10, false);
        Hero h11 = new Hero(Hero.HERO_NAME11, false);
        Hero h12 = new Hero(Hero.HERO_NAME12, false);
        Hero h13 = new Hero(Hero.HERO_NAME13, false);
        Hero h14 = new Hero(Hero.HERO_NAME14, false);
        Hero h15 = new Hero(Hero.HERO_NAME15, false);
        Hero h16 = new Hero(Hero.HERO_NAME16, false);
        Hero h17 = new Hero(Hero.HERO_NAME17, false);
        Hero h18 = new Hero(Hero.HERO_NAME18, false);
        Hero h19 = new Hero(Hero.HERO_NAME19, false);
        Hero h20 = new Hero(Hero.HERO_NAME20, false);
        Hero h21 = new Hero(Hero.HERO_NAME21, false);
        Hero h22 = new Hero(Hero.HERO_NAME22, false);
        Hero h23 = new Hero(Hero.HERO_NAME23, false);
        Hero h24 = new Hero(Hero.HERO_NAME24, false);
        Hero h25 = new Hero(Hero.HERO_NAME25, false);
        Hero h26 = new Hero(Hero.HERO_NAME26, false);
        Hero h27 = new Hero(Hero.HERO_NAME27, false);
        Hero h28 = new Hero(Hero.HERO_NAME28, false);
        Hero h29 = new Hero(Hero.HERO_NAME29, false);
        Hero h30 = new Hero(Hero.HERO_NAME30, false);

        
        try {
            gameManager.registerUser(user1);
            gameManager.registerUser(user2);
            gameManager.registerUser(user3);
            gameManager.registerUser(user4);
        } catch (DuplicatedUserException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, "Erro ao adicionar cliente", ex);
        }
        gameManager.addHero(h1);
        gameManager.addHero(h2);
        gameManager.addHero(h3);
        gameManager.addHero(h4);
        gameManager.addHero(h5);
        gameManager.addHero(h6);
        gameManager.addHero(h7);
        gameManager.addHero(h8);
        gameManager.addHero(h9);
        gameManager.addHero(h10);
        gameManager.addHero(h11);
        gameManager.addHero(h12);
        gameManager.addHero(h13);
        gameManager.addHero(h14);
        gameManager.addHero(h15);
        gameManager.addHero(h16);
        gameManager.addHero(h17);
        gameManager.addHero(h18);
        gameManager.addHero(h19);
        gameManager.addHero(h20);
        gameManager.addHero(h21);
        gameManager.addHero(h22);
        gameManager.addHero(h23);
        gameManager.addHero(h24);
        gameManager.addHero(h25);
        gameManager.addHero(h26);
        gameManager.addHero(h27);
        gameManager.addHero(h28);
        gameManager.addHero(h29);
        gameManager.addHero(h30);
        return gameManager;
    }
}
