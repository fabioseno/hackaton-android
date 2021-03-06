package com.renner.reposicao.models;

/**
 * Created by Dico
 */
public class User {

    private String chapa;
    private String senha;
    private String nome;
    private String lojaID;

    public User() {}

    public User(String chapa, String senha, String nome, String lojaID) {
        this.chapa = chapa;
        this.senha = senha;
        this.nome = nome;
        this.lojaID = lojaID;
    }

    public String getChapa() {
        return chapa;
    }

    public void setChapa(String chapa) {
        this.chapa = chapa;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLojaID() {
        return lojaID;
    }

    public void setLojaID(String lojaID) {
        this.lojaID = lojaID;
    }

    @Override
    public String toString() {
        return "Chapa: " + this.getChapa() +
                " Nome: " + this.getNome() +
                " Loja: " + this.getLojaID();
    }
}
