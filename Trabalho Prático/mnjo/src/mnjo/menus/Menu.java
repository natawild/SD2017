/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.menus;

/**
 *
 * @author Celia
 */

public class Menu {
    // variáveis de instância
    private String menu;
    private int op;

    public Menu(String menu, int op) {
        this.menu = menu;
        this.op = op;
    }
    
     public Menu(int op) {
        this.op = op;
    }

    /** Apresentar o menu */
    public void showMenu() {
        switch(op){
            case 0: System.out.println("************* MENU ****************\n"+
                                       "* 1 - Iniciar Sessao              *\n"+
                                       "* 2 - Registar                    *\n"+
                                       "* 0 - Sair                        *\n"+
                                       "***********************************\n");
                    break;
            case 1: System.out.println("******************** Menu Jogo *************\n"+
                                        "* 1 - Escolher Heroi                       *\n"+
                                        "* 2 - Visualizar Constituiçao da Equipa    *\n"+
                                       "*********************************************\n");
                    break;
            case 2: System.out.println("************* Equipas ************\n"+
                                       "* 1 - Entrar na Equipa             *\n"+
                                       "* 0 - Logout                       *\n"+
                                       "***********************************\n");
                break;
            case 3:
                System.out.println("************* Menu Jogo ************\n"
                        + "* 1 - Confirmar heroi e jogar              *\n"
                        + "* 2 - Escolher outro heroi                  *\n"
                        + "* 3 - Visualizar Equipa                  *\n"
                        + "***********************************\n");
                break;
        }
    }

    public int getOp(){
        return this.op;
    }

    public void setOp(int n){
        this.op=n;
    }

}

