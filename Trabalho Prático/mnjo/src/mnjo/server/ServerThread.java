/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import mnjo.exceptions.InvalidCredentialsException;
import mnjo.users.GameManager;
import mnjo.users.User;

/**
 *
 * @author celia
 */
public class ServerThread extends Thread{
    private Socket socket ;
    private int clientNumber;
    private User user;
    
    private BufferedReader in;
    private PrintWriter out;
    
    private GameManager gameManager;
    

    public ServerThread(GameManager gameManager, Socket socket, int clientNumber) {
        this.gameManager = gameManager;
        this.socket = socket;
        this.clientNumber= clientNumber;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    
    
    
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Send a welcome message to the client.
            out.println("Numero do cliente" + clientNumber);
            out.println("Selecione una opção ou . para sair");
            out.println(0);
            
            while (true) {
               int selectedMenu = Integer.valueOf(in.readLine());
               if(selectedMenu == 1){
                   login();
               }
            }
        } catch (IOException e) {
            System.out.print("Erro com o cliente " + clientNumber + ": " + e + '\n');
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.print("Nao é possivel fazer o socket: " + e + '\n');
            }
            System.out.print("Coneção com o cliente " + clientNumber + " fechada");
        }
    }
    
    private void login(){
        try {
            out.println("Username: ");
            String username = in.readLine();
            out.println("password");
            String password = in.readLine();
            
            try {
                user = gameManager.login(username, password);
            } catch (InvalidCredentialsException ex) {
               System.out.println("Erro nas cradenciais do cliente " + clientNumber + ": " + ex + '\n');
            }
        } catch (IOException ex) {
           System.out.println("Erro ao tentar ler username e password do cliente " + clientNumber + ": " + ex + '\n');
        }
        
    }
    
}
