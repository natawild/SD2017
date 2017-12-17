/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import mnjo.server.Server;

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
        
        try {
            server.startServer();
            
            while (true) {
                Socket socket = server.accept();
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("teste");
                } finally {
                    socket.close();
                }
            }
        }
        finally {
            server.closeServer();
        }
    }
}
