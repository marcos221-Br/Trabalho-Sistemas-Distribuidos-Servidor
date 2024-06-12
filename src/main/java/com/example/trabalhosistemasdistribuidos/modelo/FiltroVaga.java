package com.example.trabalhosistemasdistribuidos.modelo;

import java.util.ArrayList;

import org.json.JSONObject;

public class FiltroVaga {
    private Integer idVaga;
    private String email;
    private float faixaSalarial;
    private String descricao;
    private String estado;
    private String nome;
    private ArrayList<String> competencias;

    public FiltroVaga(Integer idVaga, String email, float faixaSalarial, String descricao, String estado, String nome){
        this.idVaga = idVaga;
        this.email = email;
        this.faixaSalarial = faixaSalarial;
        this.descricao = descricao;
        this.estado = estado;
        this.nome = nome;
        competencias = new ArrayList<>();
    }

    public void setCompetencias(String competencia){
        this.competencias.add(competencia);
    }

    public Integer getIdVaga(){
        return this.idVaga;
    }

    public JSONObject getJson(){
        JSONObject json = new JSONObject();
        json.put("idVaga",idVaga);
        json.put("email",email);
        json.put("faixaSalarial",faixaSalarial);
        json.put("descricao",descricao);
        json.put("estado",estado);
        json.put("nome",nome);
        json.put("competencias",competencias);
        return json;
    }

    public ArrayList<String> getCompetencias(){
        return this.competencias;
    }
}
