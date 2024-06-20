package com.example.trabalhosistemasdistribuidos.modelo;

import java.util.ArrayList;

import org.json.JSONObject;

public class FiltroCandidato {
    private String nome;
    private String email;
    private ArrayList<CompetenciaExperiencia> competenciasExperiencias;

    public FiltroCandidato(String nome, String email){
        this.nome = nome;
        this.email = email;
        this.competenciasExperiencias = new ArrayList<>();
    }

    public String getEmail(){
        return this.email;
    }

    public ArrayList<String> getCompetencias(){
        ArrayList<String> competencias = new ArrayList<>();
        for (CompetenciaExperiencia competenciaExperiencia : competenciasExperiencias) {
            competencias.add(competenciaExperiencia.getCompetencia());
        }
        return competencias;
    }

    public void setCompetenciasExperiencias(CompetenciaExperiencia competenciaExperiencia){
        this.competenciasExperiencias.add(competenciaExperiencia);
    }

    public JSONObject getJson(){
        JSONObject json = new JSONObject();
        json.put("nome",nome);
        json.put("email",email);
        json.put("competenciasExperiencias",competenciasExperiencias);
        return json;
    }

    public ArrayList<CompetenciaExperiencia> getCompetenciasExperiencias(){
        return this.competenciasExperiencias;
    }
}
