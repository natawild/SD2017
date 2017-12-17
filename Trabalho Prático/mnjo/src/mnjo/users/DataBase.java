/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.users;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Celia
 */
public class DataBase {
    private Map<String, User> db;

    public DataBase() {
        this.db = new HashMap<>();
    }
    
    public DataBase(Map<String, User> db) {
        this.db = db;
    }

    public Map<String, User> getDb() {
        //Se fosse em POO tinhas que ter cuidado com isto que estas a fazer. Precisavas de devolver uma copiada da lista e nao a propria lista (um clone do Map)
        return db;
    }

    public void setDb(Map<String, User> db) {
        //Mesma coisa que no getDb. Em POO devias primeiro criar uma copia do MAP e só depois fazer this.db = copia_da_db_passada_em_paramtro
        this.db = db;
    }
    
    /**
     * Adicionar um utilizador a base de dados
     * @param user: utilizador que se pretende adiconar
     */
    public void addUser(User user){
        //TODO: CUidado com este codigo se tiveres varias thereds podes ter problemas. O que fazer aqui? :)
        if(db.get(user.getUsername()) == null){ // ainda nao esta registado
            db.put(user.getUsername(), user);
        }
    }
    
    public void deleteUser(User user){
        //TODO: CUidado com este codigo se tiveres varias thereds podes ter problemas. O que fazer aqui? :)
        if(db.get(user.getUsername()) != null){
            db.remove(user.getUsername());
        }
    }
     
}
