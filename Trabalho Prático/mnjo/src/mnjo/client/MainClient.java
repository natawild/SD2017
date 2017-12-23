/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.client;

import java.io.IOException;

/**
 *
 * @author luisoliveira
 */

public class MainClient {
    
    public static void main(String[] args) {
        Client c = new Client("localhost", 12345);
        try {
            c.initConnection();
            c.startProtocol();
        } catch (IOException ex) {
            System.out.println("Erro: " + ex);
        }

    }
    
}
