/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.users;

import java.util.Objects;

/** Classe dos herois 
 *
 * @author Celia
 */
public class Hero {
    private String name; 
    
    public Hero(){
        this.name="";
    }
    
    public Hero(String name){
        this.name=name; 
    }
    
     public Hero(Hero h) {
        this.name = h.getName();
     }
     
     public String getName(){
         return this.name;    
     }
     
     public void setName(String name){
         this.name=name; 
     }
     
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Hero h = (Hero) obj;
        return h.getName().equals(this.name);

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
    
}
