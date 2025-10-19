
package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
     private final String url = "jdbc:postgresql://localhost:5432/dbAgenda";
    private final String user = "postgres";
    private final String password = "1288";
    
    private Connection con = null;
    
    public Connection getConexao(){
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, password);
            //System.out.println("Conexão bem sucedida.");
        } catch (Exception e){
            System.out.println("Erro ao iniciar a conexão: " + e.getMessage());
        }
       
        return con;
        
    }
}
