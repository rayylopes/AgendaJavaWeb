
package controller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEx {

private static final String LOG_FILE_PATH = "logs/agenda_contatos.log"; 
private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    public static void logExclusaoContato(int idContato, String nomeContato, int idade, String telefone) {
        String dataHora = LocalDateTime.now().format(formatter);
        String mensagem = String.format("[%s] EXCLUSÃO - ID: %d | Nome: %s | Idade: %d | Telefone: %s%n",
                dataHora, idContato, nomeContato, idade, telefone);
        
        gravarLog(mensagem);
    }
    
    public static void log(String tipoOperacao, String mensagem) {
        String dataHora = LocalDateTime.now().format(formatter);
        String logCompleto = String.format("[%s] %s - %s%n", dataHora, tipoOperacao, mensagem);
        
        gravarLog(logCompleto);
    }
    
    private static void gravarLog(String mensagem) {
    try {
        File logDir = new File("logs");
        if (!logDir.exists()) {
            boolean criada = logDir.mkdirs();
            System.out.println("Pasta criada? " + criada);
        }

        File arquivoLog = new File(logDir, "agenda_contatos.log");

        System.out.println("testando aqui bb");
        System.out.println("caminho " + arquivoLog.getAbsolutePath());
        System.out.println("ta tendo " + logDir.exists());
        System.out.println("existe ss ou nn " + arquivoLog.exists());
        System.out.println("=================================");

        FileWriter fw = new FileWriter(arquivoLog, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(mensagem);
        bw.close();
        fw.close();

        System.out.println("againS " + arquivoLog.exists());
        System.out.println("Log gravado com sucesso!");

    } catch (IOException e) {
        System.err.println("Erro ao gravar log: " + e.getMessage());
        e.printStackTrace();
    }
}
    public static String getLogFilePath() {
        return LOG_FILE_PATH;
    }
}