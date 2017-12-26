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
import mnjo.users.User;

/**
 *
 * @author Celia
 */
public class MainServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Server server = new Server(12345);
       int clientNumber = 0;
       GameManager gameManager = initBD();
        
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
    
    private static GameManager initBD(){
        GameManager gameManager = new GameManager();
        
        //Dados de teste
        User user1 = new User ("luis", "luis");
        try {
            gameManager.registerUser(user1);
        } catch (DuplicatedUserException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, "Erro ao adicionar cliente", ex);
        }
       
        return gameManager;
    }
}
