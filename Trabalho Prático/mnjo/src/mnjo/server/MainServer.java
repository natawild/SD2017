/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.server;

import java.io.IOException;
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
    public static void main(String[] args) throws IOException{
       Server server = new Server(12345);
       int clientNumber = 0;
       GameManager gameManager = new GameManager();
        
       //Dados de teste
       User user1 = new User ("luis", "luis");
       gameManager.registerUser(user1);
        
       try {
           server.startServer();
            
            while (true) {
                new ServerThread(gameManager ,server.accept(), clientNumber++).start();
            }
        }
        finally {
            server.closeServer();
        }
    }
}
