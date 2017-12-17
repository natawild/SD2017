/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Celia
 */
public class Client {
    private int port;
    private String hostname;
    private Socket clientSocket;
    
    
    public Client(String hostname, int port){
        this.hostname=hostname;
        this.port=port; 
        this.clientSocket = null;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.port;
        hash = 47 * hash + Objects.hashCode(this.hostname);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        return this.port == other.port && this.hostname.equals(other.hostname);
    }
    
    public void initConnection() throws IOException{
        this.clientSocket = new Socket(hostname, port);
    }
    
}
