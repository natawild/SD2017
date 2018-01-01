/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luisoliveira
 */

public class MainClient {
    public static final int TIMEOUT= 30;
    
    public static void main(String[] args) {
        Client c = new Client("localhost", 12345);
        try {
            c.initConnection();
            c.startProtocol();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao iniciar cliente: ", ex);
        }

    }
    
}
