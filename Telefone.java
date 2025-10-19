package model;

public class Telefone {
    int id_contato;
    int id_telefone;
    String numero;

    public Telefone(int id_contato, int id_telefone, String numero) {
        this.id_contato = id_contato;
        this.id_telefone = id_telefone;
        this.numero = numero;
    }

    public int getId_contato() {
        return id_contato;
    }

    public void setId_contato(int id_contato) {
        this.id_contato = id_contato;
    }

    public int getId_telefone() {
        return id_telefone;
    }

    public void setId_telefone(int id_telefone) {
        this.id_telefone = id_telefone;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    
}
