package com.example.trabalhosistemasdistribuidos.modelo;

public class CompetenciaExperiencia{
    private String competencia;
    private int experiencia;

    public CompetenciaExperiencia(String competencia, int experiencia){
        this.competencia = competencia;
        this.experiencia = experiencia;
    }

    public String getCompetencia(){
        return this.competencia;
    }

    public int getExperiencia(){
        return this.experiencia;
    }

    public void setCompetencia(String competencia){
        this.competencia = competencia;
    }

    public void setExperiencia(int experiencia){
        this.experiencia = experiencia;
    }
}
