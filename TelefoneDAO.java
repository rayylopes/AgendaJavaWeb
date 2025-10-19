package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import connection.Conexao;
import java.util.ArrayList;

public class TelefoneDAO {
    Connection con;
    
    public boolean inserirTelefone(int idContato, String numero) {
        con = new Conexao().getConexao();
        String sql = "INSERT INTO telefone(id_contato, numero) VALUES (?, ?);";
        
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idContato);
            pstm.setString(2, numero);
            pstm.executeUpdate();
            pstm.close();
            con.close();
            System.out.println("Telefone inserido com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir telefone: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<String> listarTelefonesPorContato(int idContato) {
        ArrayList<String> lista = new ArrayList<>();
        con = new Conexao().getConexao();
        
        String sql = "SELECT id_telefone, numero FROM telefone WHERE id_contato = ?";
        
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idContato);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                int idTelefone = rs.getInt("id_telefone");
                String numero = rs.getString("numero");
                lista.add("ID Tel: " + idTelefone + " | Número: " + numero);
            }
            
            rs.close();
            pstm.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Erro ao listar telefones: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public boolean atualizarTelefone(int idTelefone, String numero) {
        con = new Conexao().getConexao();
        String sql = "UPDATE telefone SET numero = ? WHERE id_telefone = ?";
        
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, numero);
            pstm.setInt(2, idTelefone);
            
            int linhasAfetadas = pstm.executeUpdate();
            pstm.close();
            con.close();
            
            return linhasAfetadas > 0;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar telefone: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean excluirTelefone(int idTelefone) {
        con = new Conexao().getConexao();
        String sql = "DELETE FROM telefone WHERE id_telefone = ?";
        
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idTelefone);
            
            int linhasAfetadas = pstm.executeUpdate();
            pstm.close();
            con.close();
            
            return linhasAfetadas > 0;
        } catch (Exception e) {
            System.out.println("Erro ao excluir telefone: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean excluirTelefonesPorContato(int idContato) {
        con = new Conexao().getConexao();
        String sql = "DELETE FROM telefone WHERE id_contato = ?";
        
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idContato);
            
            pstm.executeUpdate();
            pstm.close();
            con.close();
            
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao excluir telefones: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}