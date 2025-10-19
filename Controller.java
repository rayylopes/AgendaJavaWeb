package controller;

import DAO.ContatosDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import connection.Conexao;
import java.util.ArrayList;

@WebServlet(name = "Controller", urlPatterns = {"/Controller", "/main", "/insert", "/editar", "/excluir", "/update"})
public class Controller extends HttpServlet {
    Conexao con = new Conexao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getServletPath();
        System.out.println("Action: " + action);
        
        if (action.equals("/main")) {
            contatos(request, response);
        } else if (action.equals("/insert")) {
            response.sendRedirect("cadastroagenda.html");
        } else if (action.equals("/editar")) {
            mostrarFormularioEdicao(request, response);
        } else if (action.equals("/excluir")) {
            excluirContato(request, response);
        } else {
            processRequest(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getServletPath();
        
        if (action.equals("/insert")) {
            novoContato(request, response);
        } else if (action.equals("/update")) {
            atualizarContato(request, response);
        } else {
            processRequest(request, response);
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    response.setContentType("text/html;charset=UTF-8");
    
    try (PrintWriter out = response.getWriter()) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel=\"icon\" href=\"imagens/search.png\">");
        out.println("<title>Agenda de Contatos</title>");
        out.println("<meta charset='UTF-8'>");
        out.println("<link rel='stylesheet' href='style.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Sistema de Agenda de Contatos</h1>");
        
        out.println("<div>");
        out.println("<a href='insert' class='ButtonAlt'>Novo Contato</a>");
        out.println("<a href='Controller' class='ButtonAlt'>Listar Todos</a>");
        out.println("</div>");
        out.println("<p>Status da Conexão: " + (con.getConexao() != null ? 
                "✓ Conectado" : "✗ Desconectado") + "</p>");
        
        out.println("<div class='search-container'>");
        out.println("<form method='get' action='Controller'>");
        out.println("<input type='text' name='busca' placeholder='Buscar por nome ou telefone...' "
                + "class='search-input' value='" + (request.getParameter("busca") != null ? 
                        request.getParameter("busca") : "") + "'>");
        out.println("<input type='submit' value='🔍 Buscar' class='Button1'>");
        out.println("</form>");
        out.println("</div>");
        
        String busca = request.getParameter("busca");
        
        ContatosDAO dao = new ContatosDAO();
        ArrayList<String> contatos;
        
        if (busca != null && !busca.trim().isEmpty()) {
            out.println("<h2 class='search-result-title'>Resultados da busca: \"" + busca + "\"</h2>");
            contatos = dao.buscarContato(busca);
        } else {
            out.println("<h2>Lista de Contatos</h2>");
            contatos = dao.listarContatos();
        }
        
        if (contatos.isEmpty()) {
            if (busca != null && !busca.trim().isEmpty()) {
                out.println("<div class='no-results'>");
                out.println("<strong>⚠️ Nenhum contato encontrado</strong>");
                out.println("<p>Não foram encontrados contatos com o termo \"" + busca + "\".</p>");
                out.println("</div>");
            } else {
                out.println("<p>Nenhum contato cadastrado ainda.</p>");
            }
        } else {
            out.println("<table>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Nome</th>");
            out.println("<th>Idade</th>");
            out.println("<th>Telefone</th>");
            out.println("<th>Ações</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            
            for (String contato : contatos) {
                String[] partes = contato.split("\\|");
                String id = partes[0].replace("ID:", "").trim();
                String nome = partes[1].replace("Nome:", "").trim();
                String idade = partes[2].replace("Idade:", "").trim();
                String telefone = partes.length > 3 ? partes[3].replace("Telefone:", "").trim() : "-";
                
                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + nome + "</td>");
                out.println("<td>" + idade + "</td>");
                out.println("<td>" + telefone + "</td>");
                out.println("<td>");
                out.println("<a href='editar?id=" + id + "' class='ButtonAlt btn-small'>Editar</a> ");
                out.println("<a href='excluir?id=" + id + "' class='ButtonExc btn-small' onclick='return confirm(\"Deseja realmente excluir?\")'>Excluir</a>");
                out.println("</td>");
                out.println("</tr>");
            }
            
            out.println("</tbody>");
            out.println("</table>");
        }
        
        out.println("</body>");
        out.println("</html>");
    }
}
    
    protected void contatos(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("agenda.jsp");
    }
    
    protected void novoContato(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nome = request.getParameter("nome");
        String fone = request.getParameter("fone");
        String idade = request.getParameter("idade");
        
        System.out.println("Nome: " + nome);
        System.out.println("Fone: " + fone);
        System.out.println("Idade: " + idade);
        
        if (nome == null || nome.trim().isEmpty() || nome.length() > 100) {
            response.sendRedirect("cadastroagenda.html?erro=nome");
            return;
        }
        
        if (fone == null || fone.trim().isEmpty() || fone.length() > 16) {
            response.sendRedirect("cadastroagenda.html?erro=telefone");
            return;
        }
        
        try {
            int idadeInt = Integer.parseInt(idade);
            if (idadeInt < 0 || idadeInt > 999) {
                response.sendRedirect("cadastroagenda.html?erro=idade");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("cadastroagenda.html?erro=idade");
            return;
        }
        
        try {
            ContatosDAO dao = new ContatosDAO();
            boolean sucesso = dao.inserirContatoComTelefone(nome, Integer.parseInt(idade), fone);
            
            if (sucesso) {
                System.out.println("Contato inserido com sucesso!");
            } else {
                System.out.println("Erro ao inserir contato.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
        
        response.sendRedirect("Controller");
    }
    
    protected void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id = request.getParameter("id");
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            ContatosDAO dao = new ContatosDAO();
            String contatoInfo = dao.buscarContatoPorId(Integer.parseInt(id));
            
            if (contatoInfo == null) {
                out.println("<h1>Contato nÃ£o encontrado!</h1>");
                out.println("<a href='Controller'>Voltar</a>");
                return;
            }
            
            String[] partes = contatoInfo.split("\\|");
            String nome = partes[1].replace("Nome:", "").trim();
            String idade = partes[2].replace("Idade:", "").trim();
            String telefone = partes.length > 3 ? partes[3].replace("Telefone:", "").trim() : "";
            
            out.println("<!DOCTYPE html>");
            out.println("<html lang='pt-br'>");
            out.println("<head>");
            out.println("<title>Editar Contato</title>");
            out.println("<meta charset='UTF-8'>");
            out.println("<link rel='stylesheet' href='style.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Editar Contato</h1>");
            out.println("<form name='frmContato' action='update' method='post'>");
            out.println("<input type='hidden' name='id' value='" + id + "'>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td><input type='text' name='nome' value='" + nome + 
                    "' placeholder='Nome' class='Caixa1'></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td><input type='text' name='fone' value='" + telefone +
                    "' placeholder='Telefone' class='Caixa2'></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td><input type='text' name='idade' value='" + idade +
                    "' placeholder='Idade' class='CaixaIdade'></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("<input type='submit' value='Salvar' class='Button1'>");
            out.println("<a href='Controller' class='Button1'>Cancelar</a>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
}
    
    protected void atualizarContato(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String fone = request.getParameter("fone");
        String idade = request.getParameter("idade");
        
        System.out.println("Atualizando contato ID: " + id);
        System.out.println("Nome: " + nome + ", Fone: " + fone + ", Idade: " + idade);
        
        try {
            ContatosDAO dao = new ContatosDAO();
            boolean sucesso = dao.atualizarContato(Integer.parseInt(id), nome, Integer.parseInt(idade));
            
            if (sucesso) {
                System.out.println("Contato atualizado com sucesso!");
            } else {
                System.out.println("Erro ao atualizar contato.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
        
        response.sendRedirect("Controller");
    }
    
    protected void excluirContato(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String id = request.getParameter("id");

    System.out.println("=== INÍCIO DA EXCLUSÃO ===");
    System.out.println("Excluindo contato ID: " + id);

    try {
        ContatosDAO dao = new ContatosDAO();


        String contatoInfo = dao.buscarContatoPorId(Integer.parseInt(id));

        boolean sucesso = dao.excluirContato(Integer.parseInt(id));
        System.out.println("Resultado da exclusão: " + sucesso);
    if (sucesso) {
            System.out.println("Contato excluído com sucesso!");

            if (contatoInfo != null) {
                System.out.println("Preparando log...");

                String[] partes = contatoInfo.split("\\|");
                String nome = partes[1].replace("Nome:", "").trim();
                String idade = partes[2].replace("Idade:", "").trim();
                String telefone = partes.length > 3 ? partes[3].replace("Telefone:", "").trim() : "Sem telefone";


                LogEx.logExclusaoContato(
                    Integer.parseInt(id), 
                    nome, 
                    Integer.parseInt(idade), 
                    telefone
                );

                System.out.println("6. Log gravado!");
            } else {
                System.out.println("ERRO: Contato não encontrado antes da exclusão!");
            }
        } else {
            System.out.println("Erro ao excluir contato.");
        }
    } catch (Exception e) {
        System.out.println("ERRO EXCEPTION: " + e.getMessage());
        e.printStackTrace();
    }

    System.out.println("=== FIM DA EXCLUSÃO ===");

    response.sendRedirect("Controller");
}
    @Override
    public String getServletInfo() {
        return "Servlet Controller para gerenciamento de contatos";
    }
}