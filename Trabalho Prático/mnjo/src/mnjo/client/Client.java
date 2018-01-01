/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import mnjo.menus.Menu;
import mnjo.users.Game;
import mnjo.users.Hero;
import mnjo.users.User;
import mnjo.utils.Utils;

/**
 *
 * @author Celia
 */
public class Client extends Thread{
    private int port;
    private String hostname;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;
    private boolean logged;
    private User user; 
    private ObjectOutputStream oos ; 
    private ObjectInputStream ois; 
    

    
    public Client(String hostname, int port){
        this.hostname=hostname;
        this.port=port; 
        this.clientSocket = null; 
        this.scanner = null;
        this.logged = false;
    }
    
    public Client(String hostname, int port, String username, String password){
        this.hostname=hostname;
        this.port=port; 
        this.clientSocket = null; 
        this.scanner = null;
        this.logged = false;
        this.user= new User(username, password); 
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        oos = new ObjectOutputStream(clientSocket.getOutputStream());
        ois = new ObjectInputStream(clientSocket.getInputStream());
        
        //mensagem de boas vindas
        System.out.println(in.readLine());
        
        //Iniciar menus
        initMenus();
    }
    
    public void joinGameBoot() throws IOException{
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        oos = new ObjectOutputStream(clientSocket.getOutputStream());
        ois = new ObjectInputStream(clientSocket.getInputStream());
        
        //mensagem de boas vindas
        System.out.println(in.readLine());
        
        this.executeLoginBoot();
        initGameBoot(); 
        selectHeroBoot();
        seeMyTimeBoot();
        confirmeHeroAndStartGameBoot();
    }
    
    private void selectHeroBoot(){
        try {
            out.println("1");
            //ler heroies
            ois.readObject();
            //Enviar escolha do heroi
            out.println(Utils.generateRandom(0, 29));
            String message = in.readLine();
            while(message.equals("fail")){
                out.println(Utils.generateRandom(0, 29));
                message = in.readLine();
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao ler herois", ex);
        }
    }
    
    private void seeMyTimeBoot(){
        out.println("3");     
        try {
           String myTeam = in.readLine();
            List<User> myTeamList = createMyTeam(myTeam);
            printMyTeam("A sua equipa é constituida por: ", myTeamList);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao tentar apresentar a equipa", ex);
        } 
    }
    
    private void confirmeHeroAndStartGameBoot(){
        try {
            out.println("1");
            in.readLine();     
            out.println("1");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    public void executeLoginBoot(){
        out.println("1");
        loginBoot();
    }
    
    public void loginBoot(){
        try {
                in.readLine();
                out.println(user.getUsername());

                in.readLine();
                out.println(user.getPassword());
                
                in.readLine();
                logged= true;        
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao tentar efetuar login", ex);
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
                if(loginMessage.equals("success")){
                    logged= true;
                    user= new User(username, password);
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
    
    public void initGameBoot(){
        out.println("1");
        try {
            in.readLine();
            System.out.println("tem equipa #");
            Game game = (Game) ois.readObject(); 
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao tentar ler o socket", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao tentar ler o objeto Game", ex);
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
           case "1": initGame();
                    break;
           default: System.out.println("Opção invalida");
                    loggedMenus();
                    break;
        }   
    }
     
    public void logout (){
        this.logged = false;
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
    
    public void initGame(){
        try {
            String serverMessage = in.readLine();
            if(serverMessage.equals("success")){
                System.out.println("Já tem equipa");
                Game game = (Game) ois.readObject(); 
                printTeam(game);
                gameMenu();
            }
            else{
                loggedMenus();       
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao fazer cast do objecto Game", ex);
        }
    }
    
    public void gameMenu(){
        Menu menu = new Menu(1);
        menu.showMenu();
        //selecionar opção do menu
        String option = scanner.next();
        out.println(option);
        
        switch(option){
           case "1": chooseHero();
                    break;  
           case "2": seeTeam();
                    break;
           default: System.out.println("Opção invalida");
                    gameMenu();
                    break;
        }   
    }
    
    

    private void printTeam(Game game) {
        if(game.getTeamA().contains(this.user)){
            System.out.println(); 
            printUserTeam("A sua equipa é constituida por: ", game.getTeamA(), "A equipa adversária é constituida por: ", game.getTeamB());
        }
        else{
            printUserTeam("A sua equipa é constituida por: ", game.getTeamB(), "A equipa adversária é constituida por: ", game.getTeamA());
        }
    }

    private void printUserTeam(String message1, List<User> team1, String message2,  List<User> team2 ) {
        System.out.println(message1); 
        for(User u : team1){
            System.out.print(u.printUser());
            System.out.print(" | ");
        }
        System.out.println("\n" + message2); 
        for(User u : team2){
            System.out.print(u.printUser());
            System.out.print(" | ");
        }
        System.out.println();
    }
    
    private void printMyTeam(String message, List<User> team){
        System.out.println(message); 
        for(User u : team){
            System.out.print(u.printUser());
            System.out.print(" | ");
        }
        System.out.println("");
    }
    
    @Override
    public void run() {
        try {
            initConnection();
            joinGameBoot();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "erro ao tentar executar cliente teste", ex);
        }   
    } 

    private void chooseHero() {
        try {
            List<Hero> heroes = (List<Hero>) ois.readObject();
            int i=0;
            System.out.println("Escolha um dos seguintes herois:");
            for(Hero h: heroes){
                System.out.println(i+ " - "+h.getName());
                i++;
            }
            int option = Integer.valueOf(scanner.next());
            
            while(option<0 && option>=heroes.size()){
                System.out.println("Opção inválida. Insira novamente a opção");
                option = Integer.valueOf(scanner.next());
            }
            out.println(option);
            String messageSuccess = in.readLine();
            if(messageSuccess.equals("success")){
                heroMenu(); 
            }
            else{
                System.out.println("Heroi ja foi selecionado");
                //TODO: 
                // 1 apresentar a lista da equipa
                // voltar a pedir para escolher outro heroi
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao tentar ler a lista de herois", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao tentar ler a lista de heois", ex);
        }
    }
    
       public void heroMenu() {
        Menu menu = new Menu(3);
        menu.showMenu();
        //selecionar opção do menu
        String option = scanner.next();
        out.println(option);

        switch (option) {
            case "1":
                confirmHeroAndPlayGame();
                break;
            case "2":
                changeHero();
                break;
            case "3":
                seeMyTeam();
                break;
            default:
                System.out.println("Opção invalida");
                heroMenu();
                break;
        }
    }
    

    private void seeTeam() {
        
    }

    private void confirmHeroAndPlayGame() {
        try {
            String myTeam = in.readLine();
            List<User> myTeamList = createMyTeam(myTeam);
            printMyTeam("A sua equipa é constituida por: ", myTeamList);
            
            String otherTeam = in.readLine();
            List<User> otherTeamList = createMyTeam(myTeam);
            printMyTeam("A equipa adversaria é constituida por: ", otherTeamList);
            
            String message = in.readLine();
            if(message.equals("win")){
                System.out.println("Parabens a sua equipa venceu");
                gameMenu();
            }
            else {
                System.out.println("Jogo perdido");
                gameMenu();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao confirmar heroi", ex);
        }
    }

    private void changeHero() {
    }

    private void seeMyTeam() {
        try {
            String myTeam = in.readLine();
            List<User> myTeamList = createMyTeam(myTeam);
            printMyTeam("A sua equipa é constituida por: ", myTeamList);
            heroMenu();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao tentar ver a minha equipa", ex);
        }
    }

    private List<User> createMyTeam(String myTeam) {
        //imprimir minha equipa
        List<User> myTeamList = new ArrayList<>();
        String[] users = myTeam.split(";");
        for(String userString: users){
            String[] userSplitted = userString.split(":");
            User u = new User();
            u.setUsername(userSplitted[0]);
            if(userSplitted[1].equals("null")){
                u.setHero(null);
            }
            else {
                Hero h = new Hero();
                h.setName(userSplitted[1]);
                u.setHero(h);  
            }
            myTeamList.add(u);
        }
        return myTeamList;
    }
}
