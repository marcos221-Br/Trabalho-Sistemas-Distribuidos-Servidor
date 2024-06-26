package com.example.trabalhosistemasdistribuidos.modelo;

import java.util.ArrayList;

import org.json.JSONObject;

public class FiltroCandidato {
    private Integer idCandidato;
    private String nome;
    private String email;
    private ArrayList<CompetenciaExperiencia> competenciasExperiencias;

    public FiltroCandidato(Integer idCandidato, String nome, String email){
        this.idCandidato = idCandidato;
        this.nome = nome;
        this.email = email;
        this.competenciasExperiencias = new ArrayList<>();
    }

    public String getEmail(){
        return this.email;
    }

    public Integer getIdCandidato(){
        return this.idCandidato;
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
        json.put("idCandidato",idCandidato);
        json.put("nome",nome);
        json.put("email",email);
        json.put("competenciaExperiencia",competenciasExperiencias);
        return json;
    }

    public ArrayList<CompetenciaExperiencia> getCompetenciasExperiencias(){
        return this.competenciasExperiencias;
    }
}
