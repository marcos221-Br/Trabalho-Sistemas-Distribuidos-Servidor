package com.example.trabalhosistemasdistribuidos.modelo;

import java.util.ArrayList;

public class Filtro {
    private ArrayList<String> competencias;
    private ArrayList<Integer> experiencias;
    private String tipo;

    public Filtro(String tipo){
        this.competencias = new ArrayList<>();
        this.experiencias = new ArrayList<>();
        this.tipo = tipo;
    }

    public void setCompetencias(String competencia){
        this.competencias.add(competencia);
    }

    public void setExperiencias(Integer experiencias){
        this.experiencias.add(experiencias);
    }

    public ArrayList<Integer> getExperiencias(){
        return this.experiencias;
    }

    public ArrayList<String> getCompetencias(){
        return this.competencias;
    }

    public String getTipo(){
        return this.tipo;
    }

    @Override
    public String toString(){
        return "Tipo: " + this.tipo + "\nCompetencias: " + this.competencias;
    }
}
