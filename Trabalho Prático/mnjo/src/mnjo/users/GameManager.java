/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Celia
 */

//esta classe servirá para armazenar e gerir todos os dados 
public class GameManager {
    
    /*Map com todos os utilizadores registados*/
    private Map<String, User> users;
    /*Lista com os herois*/
    private List<Hero> heroes; 
    private ReentrantLock usersLock;

    public GameManager() {
        this.users = new HashMap<>();
        this.usersLock = new ReentrantLock();
    }
    
    public GameManager(Map<String, User> users) {
        this.users = users;
    }

    public Map<String, User> getGameManager() {
        //Se fosse em POO tinhas que ter cuidado com isto que estas a fazer. Precisavas de devolver uma copia da lista e nao a propria lista (um clone do Map)
        return users;
    }

    public void setGameManager(Map<String, User> users) {
        //Mesma coisa que no getDb. Em POO devias primeiro criar uma copia do MAP e só depois fazer this.db = copia_da_db_passada_em_paramtro
        this.users = users;
    }
    
    /**
     * Adicionar um utilizador a base de dados
     * @param user: utilizador que se pretende adiconar
     */
    public void registerUser(User user){
        this.usersLock.lock();
        try {
            //TODO: CUidado com este codigo se tiveres varias thereds podes ter problemas. O que fazer aqui? :)
            if(users.get(user.getUsername()) == null){ // ainda nao esta registado
                users.put(user.getUsername(), user);
            }
        } finally {
            this.usersLock.unlock();
        }
    }

    
    
    public void deleteUser(User user){
        //TODO: CUidado com este codigo se tiveres varias thereds podes ter problemas. O que fazer aqui? :)
        if(users.get(user.getUsername()) != null){
            users.remove(user.getUsername());
        }
    }
    
    
    
    public List<Hero> initHeroes (){
        List listaHerois = new ArrayList();
        heroes.forEach(listaHerois::add);
        return listaHerois; 
    }
     
}
