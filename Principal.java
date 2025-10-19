/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DAO.ContatosDAO;
import DAO.TelefoneDAO;
public class Principal {


    public static void main(String[] args) {
    ContatosDAO user = new ContatosDAO();
    
    user.inserirContato("Rayane", 20);
    //user.inserirUsuario("Eloísa", 25);
   
    //user.alterarUsuarioPorId("Luisa", 32, 1);

    //user.removerUsuarioPorId(2);
    

    }

    
}


