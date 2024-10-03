/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


/**
 *
 * @author labsfiap
 */
public class Conecta_DB {
    
    public static void carregaDriver(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            System.out.println("Driver nao pode ser carregado!\n ou DB Inexistente");
        }
    }
    
}
