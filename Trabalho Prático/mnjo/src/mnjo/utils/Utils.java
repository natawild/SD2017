/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.utils;

import java.util.Random;

/**
 *
 * @author luisoliveira
 */
public class Utils {
    
    public static int generateRandom(int min, int max){
        //return (int) (Math.random() * ( max - min ));
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
        
    }
    
}
