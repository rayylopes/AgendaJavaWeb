
package model;

public class Contatos {
    int id_contato;
    String nome;
    int idade;

    public Contatos(int id_contato, String nome, int idade) {
        this.id_contato = id_contato;
        this.nome = nome;
        this.idade = idade;
    }

    public int getId_contato() {
        return id_contato;
    }

    public void setId_contato(int id_contato) {
        this.id_contato = id_contato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    
}
