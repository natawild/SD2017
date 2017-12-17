/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luisoliveira
 */
public class ClientServer {
    
    public static void main(String[] args) {
        Client c = new Client("localhost", 12345);
        try {
            c.initConnection();
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(c.getClientSocket().getInputStream()));
            String answer = input.readLine();
            System.out.println(answer);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
