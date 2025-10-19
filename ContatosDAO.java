package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import connection.Conexao;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContatosDAO {
    Connection con;
    
    public boolean inserirContatoComTelefone(String nome, int idade, String telefone) {
        con = new Conexao().getConexao();
        
        String sqlContato = "INSERT INTO contatos(nome, idade) VALUES (?, ?) RETURNING id_contato;";
        String sqlTelefone = "INSERT INTO telefone(id_contato, numero) VALUES (?, ?);";
        
        try {
            con.setAutoCommit(false);
            
            PreparedStatement pstmContato = con.prepareStatement(sqlContato);
            pstmContato.setString(1, nome);
            pstmContato.setInt(2, idade);
            
            ResultSet rs = pstmContato.executeQuery();
            int idContato = 0;
            
            if (rs.next()) {
                idContato = rs.getInt("id_contato");
            }
            
            rs.close();
            pstmContato.close();
            
            if (idContato > 0 && telefone != null && !telefone.isEmpty()) {
                PreparedStatement pstmTelefone = con.prepareStatement(sqlTelefone);
                pstmTelefone.setInt(1, idContato);
                pstmTelefone.setString(2, telefone);
                pstmTelefone.executeUpdate();
                pstmTelefone.close();
            }
            
            con.commit();
            con.close();
            
            System.out.println("Contato e telefone inseridos com sucesso. ID: " + idContato);
            return true;
            
        } catch (Exception e) {
            System.out.println("Erro ao inserir contato: " + e.getMessage());
            e.printStackTrace();
            
            try {
                if (con != null) {
                    con.rollback();
                    con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
    
    public boolean inserirContato(String nome, int idade) {
        con = new Conexao().getConexao();
        String sql = "INSERT INTO contatos(nome, idade) VALUES (?, ?);";
        
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, nome);
            pstm.setInt(2, idade);
            pstm.executeUpdate();
            pstm.close();
            con.close();
            System.out.println("Contato inserido com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir contato: " + e.getMessage());
            return false;
        }
    }
    
    public ArrayList<String> listarContatos() {
        ArrayList<String> lista = new ArrayList<>();
        con = new Conexao().getConexao();
        
        String sql = "SELECT c.id_contato, c.nome, c.idade, t.numero " +
                     "FROM contatos c " +
                     "LEFT JOIN telefone t ON c.id_contato = t.id_contato " +
                     "ORDER BY c.id_contato ASC";
        
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id_contato");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String numero = rs.getString("numero");
                
                String contato = "ID: " + id + " | Nome: " + nome + 
                               " | Idade: " + idade;
                
                if (numero != null && !numero.isEmpty()) {
                    contato += " | Telefone: " + numero;
                }
                
                lista.add(contato);
            }
            
            rs.close();
            pstm.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Erro ao listar contatos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
   
    public ArrayList<String> buscarContato(String buscando) {
    ArrayList<String> lista = new ArrayList<>();
    con = new Conexao().getConexao();

    String sql = "SELECT c.id_contato, c.nome, c.idade, t.numero " +
                 "FROM contatos c " +
                 "LEFT JOIN telefone t ON c.id_contato = t.id_contato " +
                 "WHERE LOWER(c.nome) LIKE LOWER(?) " +
                 "OR t.numero LIKE ? " +
                 "ORDER BY c.id_contato ASC";

    try {
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, "%" + buscando + "%");
        pstm.setString(2, "%" + buscando + "%");
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id_contato");
            String nome = rs.getString("nome");
            int idade = rs.getInt("idade");
            String numero = rs.getString("numero");

            String contato = "ID: " + id + " | Nome: " + nome + 
                           " | Idade: " + idade;

            if (numero != null && !numero.isEmpty()) {
                contato += " | Telefone: " + numero;
            }

            lista.add(contato);
        }
        rs.close();
        pstm.close();
        con.close();
    } catch(Exception e){
        System.out.println("Erro ao buscar contatos: " + e.getMessage());
        e.printStackTrace();
    }
    return lista;
    }
    
    public String buscarContatoPorId(int idContato) {
    con = new Conexao().getConexao();

    String sql = "SELECT c.id_contato, c.nome, c.idade, t.numero " +
                 "FROM contatos c " +
                 "LEFT JOIN telefone t ON c.id_contato = t.id_contato " +
                 "WHERE c.id_contato = ?";

    try {
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, idContato);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            String nome = rs.getString("nome");
            int idade = rs.getInt("idade");
            String numero = rs.getString("numero");

            String contato = "ID: " + idContato + " | Nome: " + nome + 
                           " | Idade: " + idade;

            if (numero != null && !numero.isEmpty()) {
                contato += " | Telefone: " + numero;
            }

            rs.close();
            pstm.close();
            con.close();
            return contato;
        }

        rs.close();
        pstm.close();
        con.close();
    } catch (Exception e) {
        System.out.println("Erro ao buscar contato: " + e.getMessage());
        e.printStackTrace();
    }

    return null;
    }
    
    public boolean atualizarContato(int idContato, String nome, int idade) {
        con = new Conexao().getConexao();
        String sql = "UPDATE contatos SET nome = ?, idade = ? WHERE id_contato = ?";
        
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, nome);
            pstm.setInt(2, idade);
            pstm.setInt(3, idContato);
            
            int linhasAfetadas = pstm.executeUpdate();
            pstm.close();
            con.close();
            
            return linhasAfetadas > 0;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar contato: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean excluirContato(int idContato) {
        con = new Conexao().getConexao();
        
        try {
            con.setAutoCommit(false);
            
            String sqlTelefone = "DELETE FROM telefone WHERE id_contato = ?";
            PreparedStatement pstmTelefone = con.prepareStatement(sqlTelefone);
            pstmTelefone.setInt(1, idContato);
            pstmTelefone.executeUpdate();
            pstmTelefone.close();
            
            String sqlContato = "DELETE FROM contatos WHERE id_contato = ?";
            PreparedStatement pstmContato = con.prepareStatement(sqlContato);
            pstmContato.setInt(1, idContato);
            int linhasAfetadas = pstmContato.executeUpdate();
            pstmContato.close();
            
            con.commit();
            con.close();
            
            return linhasAfetadas > 0;
        } catch (Exception e) {
            System.out.println("Erro ao excluir contato: " + e.getMessage());
            e.printStackTrace();
            
            try {
                if (con != null) {
                    con.rollback();
                    con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
}