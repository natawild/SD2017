/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import mnjo.menus.Menu;

/**
 *
 * @author Celia
 */
public class Client {
    private int port;
    private String hostname;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;
    private boolean logged;

    
    public Client(String hostname, int port){
        this.hostname=hostname;
        this.port=port; 
        this.clientSocket = null; 
        this.scanner = null;
        this.logged = false;
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

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
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
    
    public void startProtocol() throws IOException{
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        scanner = new Scanner(System.in);
        
        //mensagem de boas vindas
        System.out.println(in.readLine());
        
        //Iniciar menus
        initMenus();
    }
    
    public void initMenus(){
        Menu menu = new Menu(0);
        menu.showMenu();
        
        //selecionar opção do menu
        String option = scanner.next();
        out.println(option);
        
        switch(option){
           case "0":  close();
                    break;  
           case "1": login();
                    break;
           case "2": register(option);
                    break;
           default: System.out.println("Opção invalida");
                    initMenus();
                    break;
        }
    }
    
    public void login(){
        String loginMessage = "fail";
        int retryNumber = 0;
        while (loginMessage.equals("fail") && retryNumber < 3){
            try {
                System.out.println(in.readLine());
                String username = scanner.next();
                out.println(username);

                System.out.println(in.readLine());
                String password = scanner.next();
                out.println(password);
                
                loginMessage = in.readLine();
                if(loginMessage.equals("sucess")){
                    logged= true;
                }
                else {
                    System.out.println("Cradencias erradas ou utilizador nao registado");
                    retryNumber++;
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao tentar efetuar login", ex);
            }
        }
        
        if(logged){
            loggedMenus();
        }
        else {
            initMenus();
        }
    }
    
     public void loggedMenus(){
        Menu menu = new Menu(2);
        menu.showMenu();
        
        //selecionar opção do menu
        String option = scanner.next();
        out.println(option);
        
        switch(option){
           case "0":  logout();
                    break;  
//           case "1": login();
//                    break;
//           case "2": register(option);
//                    break;
           default: System.out.println("Opção invalida");
                    loggedMenus();
                    break;
        }   
    }
     
    public void logout (){
        this.logged = false;
        out.println(4);
        initMenus();
    }
    
    public void register(String option){
        String registerMessage = "fail";
        int retryNumber = 0;
        while (registerMessage.equals("fail") && retryNumber < 3){
            try {
                System.out.println(in.readLine());
                String username = scanner.next();
                out.println(username);

                System.out.println(in.readLine());
                String password = scanner.next();
                out.println(password);
                
                registerMessage = in.readLine();
                if(registerMessage.equals("fail")){
                    System.out.println("Problema ao tentar registar. Verifique se estáa usar as cradenciais correctas");
                    retryNumber++;
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao tentar efetuar login", ex);
            }
        }
        
        initMenus();
    }
    
    public void close(){
        out.println("0");
        
        this.scanner.close(); 
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao fechar cliente", ex);
        }
      
    }
    
}
