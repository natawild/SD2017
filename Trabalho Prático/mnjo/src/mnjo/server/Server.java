/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Celia
 */
public class Server {
    private ServerSocket serverSocket; 
    private int port;
    
    public Server(int port){
        this.port=port; 
    }
    
    public void startServer() throws IOException{
        serverSocket = new ServerSocket(port);
    }
    
    public void closeServer() throws IOException {
        serverSocket.close();
    }
    
    public Socket accept() throws IOException{
        return serverSocket.accept();
    }
    
}
