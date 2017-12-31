/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.client.tests;

import java.util.ArrayList;
import java.util.List;
import mnjo.client.Client;

/**
 *
 * @author Celia
 */
public class Test {
    public static void main(String[] args) {
        
        List<String> list = new ArrayList<>(); 
        list.add("celia");
        list.add("marcia");
        list.add("adriana");
        
        
        for(String a : list){
            Client c = new Client("localhost", 12345,a,a );
            c.start();
        }
    }
    
    
}
