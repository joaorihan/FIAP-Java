/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author labsfiap
 */
public class Funcionalidades_DAO {
     // Preço por hora
    private static final double PRECO_POR_HORA = 15.00;


    public static double calcularValor(int estadia) {
        return estadia * PRECO_POR_HORA;
    }

    public static String obterHoraAtual() {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Corrigido o formato
        return agora.format(formato);
    }

    
}
