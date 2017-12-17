/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo;

import java.net.ServerSocket;

/**
 *
 * @author Celia
 */
public class Servidor {
    private ServerSocket serverSocket; 
    private int porta;
    
    public Servidor(int porta){
        this.porta=porta; 
    }
    
    
    public static void main(String[] args){
        Servidor s = new Servidor(118000);
    }
    
    
}
