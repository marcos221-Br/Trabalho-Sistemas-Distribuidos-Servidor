package com.example.trabalhosistemasdistribuidos.modelo;

public class VagaId {
    private String nomeVaga;
    private int idVaga;

    public VagaId(String nomeVaga, int idVaga){
        this.nomeVaga = nomeVaga;
        this.idVaga = idVaga;
    }

    public String getNomeVaga(){
        return this.nomeVaga;
    }

    public int getIdVaga(){
        return this.idVaga;
    }
}
