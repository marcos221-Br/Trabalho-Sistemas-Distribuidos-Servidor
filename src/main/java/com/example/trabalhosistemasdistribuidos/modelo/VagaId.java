package com.example.trabalhosistemasdistribuidos.modelo;

public class VagaId {
    private String nome;
    private int idVaga;

    public VagaId(String nome, int idVaga){
        this.nome = nome;
        this.idVaga = idVaga;
    }

    public String getNome(){
        return this.nome;
    }

    public int getIdVaga(){
        return this.idVaga;
    }
}
