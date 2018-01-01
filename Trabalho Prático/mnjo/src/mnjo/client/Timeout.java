/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.client;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Celia
 */
public class Timeout extends Thread {

    private int seconds;
    private Client client;

    public Timeout(int seconds, Client client) {
        this.seconds = seconds;
        this.client= client;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
            Timeout.sleep(seconds * 1000);
            client.interrupt();
           // thrown new InterruptedException()
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao adormecer a thread ", ex);
        }
    }
}
