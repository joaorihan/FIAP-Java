/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import view.MenuVeiculos_GUI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author labsfiap
 */
public class CRUD_DAO {
    

    public static String URL = "jdbc:mysql://localhost/estacionamento";
    public static String USERNAME = "root";
    public static String PASSWORD = "123";
    
    
    public static void cadastrar(String placa, String modelo, String marca, int estadia, String status){
        try {
            Connection con = null;

            // Estabelecendo conexão com o banco de dados
            try {
                con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException ex) {
                //Logger.getLogger(Agenda_FIAP.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!", "ERRO", JOptionPane.ERROR_MESSAGE);
                return; // Saindo caso a conexão falhe
            }

            // Recebendo os dados a serem inseridos na tabela
            String sql = "INSERT INTO veiculos(placa, marca, modelo, data_entrada, valor_pago, status) VALUES (?, ?, ?, ?, ?, ?);";

            // Tratamento de Erros para inserção
            try {
                // Criando variável que executará a inserção
                PreparedStatement inserir = con.prepareStatement(sql);
                inserir.setString(1, placa);
                inserir.setString(2, marca);
                inserir.setString(3, modelo);
                inserir.setString(4, Funcionalidades_DAO.obterHoraAtual());
                inserir.setString(5, String.valueOf(Funcionalidades_DAO.calcularValor(estadia)));
                inserir.setString(6, status);

                // Executando a inserção
                inserir.executeUpdate(); // Usando executeUpdate para inserções
                JOptionPane.showMessageDialog(null, "\nInserção realizada com sucesso!!!\n", "", JOptionPane.INFORMATION_MESSAGE);

                // Limpando campos de texto
                MenuVeiculos_GUI.placa.setText("");
                MenuVeiculos_GUI.modelo.setText("");
                MenuVeiculos_GUI.marca.setText("");
                MenuVeiculos_GUI.refresh();
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "\nErro na inserção!", "ERRO!", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException erro) {
            // Tratamento de erro caso o usuário não digite o telefone corretamente
            JOptionPane.showMessageDialog(null, "Digite os dados corretamente", "ERRO", JOptionPane.ERROR_MESSAGE);
            //tel_txt.setText("");
        }

        // Fim do código

    }
    
    
    public static void consultar() {
    // Variáveis
    String placa = JOptionPane.showInputDialog("Insira a placa a ser consultada");

    if (placa.isEmpty() || placa == "") {
        JOptionPane.showMessageDialog(null, "A placa não pode estar vazia.", "ERRO", JOptionPane.ERROR_MESSAGE);
        return;
    }

    Connection con = null;
    Statement stm = null;
    ResultSet rs = null;

    try {
        // Conectar ao banco de dados
        con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // Query SQL para buscar informações do veículo
        String sql = "SELECT Marca, Modelo, Placa, Status FROM Veiculos WHERE Placa = '" + placa + "'";
        stm = con.createStatement();
        rs = stm.executeQuery(sql);

        if (rs.next()) {
            // Atribuir os valores aos campos da interface
            String marca = rs.getString("Marca");
            String modelo = rs.getString("Modelo");
            String status = rs.getString("Status");

            MenuVeiculos_GUI.placa.setText(placa);
            MenuVeiculos_GUI.marca.setText(marca);
            MenuVeiculos_GUI.modelo.setText(modelo);
            MenuVeiculos_GUI.status.setSelectedItem(status);
            
            
            
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum veículo com essa placa foi registrado!", "Resultado", JOptionPane.WARNING_MESSAGE);
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    } finally {
        // Fechar ResultSet, Statement e Connection
        try {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}    
    
    public static void deletar() {
        String placa = JOptionPane.showInputDialog("Insira a placa do veículo que deseja excluir");

        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(null, "A placa não pode estar vazia.", "ERRO", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement("DELETE FROM Veiculos WHERE Placa = ?")) {
            
            stmt.setString(1, placa);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Veículo excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum veículo com essa placa foi encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir o veículo: " + ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void limparTodos() {
        int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar todos os registros?", "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 Statement stmt = con.createStatement()) {
                
                int rowsAffected = stmt.executeUpdate("DELETE FROM Veiculos");

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Todos os registros foram excluídos com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Não havia registros para excluir.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao limpar os registros: " + ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    public static void imprimir() {
        String placa = JOptionPane.showInputDialog("Insira a placa do veículo que deseja imprimir");

        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(null, "A placa não pode estar vazia.", "ERRO", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM Veiculos WHERE Placa = ?")) {

            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Pegando os dados do veículo
                String marca = rs.getString("Marca");
                String modelo = rs.getString("Modelo");
                String dataEntrada = rs.getString("data_entrada");
                String valorPago = rs.getString("valor_pago");
                String status = rs.getString("Status");

                // Simulação de impressão (exibindo em um JOptionPane)
                String mensagemImpressao = "----- Impressão -----\n" +
                        "Placa: " + placa + "\n" +
                        "Marca: " + marca + "\n" +
                        "Modelo: " + modelo + "\n" +
                        "Data de Entrada: " + dataEntrada + "\n" +
                        "Valor Pago: " + valorPago + "\n" +
                        "Status: " + status + "\n" +
                        "------------------------------";

                JOptionPane.showMessageDialog(null, mensagemImpressao, "Impressão", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum veículo com essa placa foi encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar os dados: " + ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
