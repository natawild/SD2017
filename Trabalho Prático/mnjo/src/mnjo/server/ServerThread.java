/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import mnjo.client.Client;
import mnjo.exceptions.DuplicatedUserException;
import mnjo.exceptions.InvalidCredentialsException;
import mnjo.users.Game;
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
    private ObjectOutputStream oos ; 
    private ObjectInputStream ois; 
    

    public ServerThread(GameManager gameManager, Socket socket, int clientNumber) {
        this.gameManager = gameManager;
        this.socket = socket;
        this.clientNumber= clientNumber;
        this.user = null;
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
    
    
    
    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            // Send a welcome message to the client.
            out.println("Numero do cliente #" + clientNumber);
            
            int selectedMenu = Integer.valueOf(in.readLine());            
            while (true && selectedMenu > 0) {
               protocolOptions(selectedMenu);     
               selectedMenu = Integer.valueOf(in.readLine());
            }
        } catch (IOException e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro com o cliente " + clientNumber, e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro com o cliente " + clientNumber, e);
            }
            Logger.getLogger(Client.class.getName()).log(Level.INFO, "Cone\u00e7\u00e3o com o cliente {0} fechada", clientNumber);
        }
    }
    
    private void protocolOptions (int selectedMenu){
        if(user==null){
            switch(selectedMenu){
                case 1:  login();
                        break;
                case 2: register();
                        break;
                default: 
                        break;
            }
        }
        else {
            switch (selectedMenu) {
                case 1:
                    initGame();
                    break;
                case 0:
                    logout();
                    break;
                default:
                    break;
            }
        }
    }
    
    private void logout(){
        this.user = null;    
    }
    
    private void login(){
        int retryNumber = 0;
       
        try {
            while (user == null && retryNumber < 3){
                out.println("Username: ");
                String username = in.readLine();
                out.println("password");
                String password = in.readLine();

                try {
                    user = gameManager.login(username, password);
                } catch (InvalidCredentialsException ex) {
                   Logger.getLogger(Client.class.getName()).log(Level.INFO, "Erro nas cradenciais do cliente " + clientNumber, ex);
                }
                
                if(user != null){
                  out.println("sucess");
                }
                else {
                   out.println("fail"); 
                   retryNumber++;
                }
            }
        } catch (IOException ex) {
           Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao tentar ler username e password do cliente " + clientNumber , ex);
        }
        
    }
    
    public void register(){     
        User registedUser = null;
        int retryNumber = 0;
        while (registedUser == null && retryNumber < 3){
            try {
                out.println("Username: ");
                String username;
                username = in.readLine();
                out.println("password");
                String password = in.readLine();
                registedUser = gameManager.registerUser(username, password);
                Logger.getLogger(ServerThread.class.getName()).log(Level.INFO, "Utilizador " + username + " registado");
            } catch (DuplicatedUserException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "Erro ao tentar registar utilizador", ex);
            }
            
            if(registedUser != null){
                out.println("sucess");
            }
            else {
                out.println("fail"); 
                retryNumber++;
            }   
        }       
    }
    
    public void initGame(){
        gameManager.findTeam(user);
        //mandar mensagem para ciente a dzer que ja tem equipa
        out.println("success");
        Game game = gameManager.getGames().get(user.getGameId());
        try {
            //mandar jogo para o cliente
            oos.writeObject(game);
            gameMenu();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void gameMenu() throws IOException {
        //esperar pelo opcao do cliente
        String option = in.readLine();
        switch(option){
            case "1": chooseHero();
                break;
            case "2": seeTeam(); 
                break;
            default: gameMenu();
                break;
        }
        //consoante a opçcao do cliente executar o que pediu
    }
    
    private void chooseHero() {
        Game game = gameManager.getGames().get(user.getGameId()); 
        try { 
            oos.writeObject(game.getHeroes());
            String inHero = in.readLine(); 
            boolean success = game.selectHero(inHero, user); 
            if(success){
                out.println("success");
                heroMenu();
            }
            else {
                out.println("fail");   
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "Erro ao tentar enviar a lista de herois", ex);
        }
        
    }
    
    private void heroMenu() throws IOException {
        //esperar pelo opcao do cliente
        String option = in.readLine();
        switch(option){
           case "1": confirmHero();
                    break;  
           case "2": changeHero();
                    break;
           case "3": seeMyTeam();
                    break;
           default: heroMenu();
                    break;
        }   
        //consoante a opçcao do cliente executar o que pediu
    }
    
     private void seeTeam() {
        
    }

    private void confirmHero() {
    }

    private void changeHero() {
    }

    private void seeMyTeam() {
        Game game = gameManager.getGames().get(user.getGameId()); 
        try {
            oos.writeObject(game);
            heroMenu(); 
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "Erro ao tentar enviar jogo", ex);
        }
    }
    
}
