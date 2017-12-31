/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.users;

import java.io.Serializable;
import java.util.Objects;

/** Classe dos herois 
 *
 * @author Celia
 */
public class Hero implements Serializable{
    public static final String HERO_NAME1 = "heroi1";
    public static final String HERO_NAME2 = "heroi2";
    public static final String HERO_NAME3 = "heroi3";
    public static final String HERO_NAME4 = "heroi4";
    public static final String HERO_NAME5 = "heroi5";
    public static final String HERO_NAME6 = "heroi6";
    public static final String HERO_NAME7 = "heroi7";
    public static final String HERO_NAME8 = "heroi8";
    public static final String HERO_NAME9 = "heroi9";
    public static final String HERO_NAME10 = "heroi10";
    public static final String HERO_NAME11 = "heroi11";
    public static final String HERO_NAME12 = "heroi12";
    public static final String HERO_NAME13 = "heroi13";
    public static final String HERO_NAME14 = "heroi14";
    public static final String HERO_NAME15 = "heroi15";
    public static final String HERO_NAME16 = "heroi16";
    public static final String HERO_NAME17 = "heroi17";
    public static final String HERO_NAME18 = "heroi18";
    public static final String HERO_NAME19 = "heroi19";
    public static final String HERO_NAME20 = "heroi20";
    public static final String HERO_NAME21 = "heroi21";
    public static final String HERO_NAME22 = "heroi22";
    public static final String HERO_NAME23 = "heroi23";
    public static final String HERO_NAME24 = "heroi24";
    public static final String HERO_NAME25 = "heroi25";
    public static final String HERO_NAME26 = "heroi26";
    public static final String HERO_NAME27 = "heroi27";
    public static final String HERO_NAME28 = "heroi28";
    public static final String HERO_NAME29 = "heroi29";
    public static final String HERO_NAME30 = "heroi30";

    private String name;
    private Boolean used; 

    public Hero() {
        this.name = null;
        this.used = null;
    }
    
    public Hero(String name, boolean used) {
        this.name = name;
        this.used = used;
    }
    
    public Hero(Hero h) {
        this.name = h.getName();
        this.used = h.isUsed();
    }

    public boolean isUsed() {
        return used;
    }
    
    public boolean getUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
    
    

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + (this.used ? 1 : 0);
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
        final Hero other = (Hero) obj;
        if (this.used != other.used) {
            return false;
        }
        return this.name.equals(other.name);
    }

    @Override
    public String toString() {
        return "Hero{" + "name=" + name + ", used=" + used + '}';
    }
    
    public String printHero(){
        StringBuilder string = new StringBuilder();
        string.append(name); 
        return string.toString(); 
    }

    @Override
    public Hero clone() {
        Hero h = new Hero(name, used);
        return h; 
    }   
}
