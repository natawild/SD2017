/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;



/**
 *
 * @author Celia
 */
public class Cliente {
    private final static int porta;
    private final static String hostname;
    private Socket clienteSocket;
    
    
    public Cliente(String hostname, int porta){
        this.hostname=hostname;
        this.porta=porta; 
    }
    
    
    public static void main(String[] args) {
        Cliente c = new Cliente("localhost", 11800);

    }
}
