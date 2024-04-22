package com.example.trabalhosistemasdistribuidos;

import org.json.JSONObject;

public class ToJson {
    private String operacao;
    private String[] funcoes;
    private String[] valores;
    private JSONObject json;

    public ToJson(){
        json = new JSONObject();
        this.operacao = "";
    }

    public ToJson(String operacao, String[] funcoes, String[] valores){
        json = new JSONObject();
        this.operacao = operacao;
        this.funcoes = funcoes;
        this.valores = valores;
    }

    private void montarJson(){
        this.json.put("operacao",operacao);
        for (int i = 0; i < funcoes.length; i++) {
            this.json.put(funcoes[i],valores[i]);
        }
    }

    public String getJson(){
        montarJson();
        return this.json.toString();
    }

    public void setJson(JSONObject json){
        int i = 0;
        this.operacao = (String) json.get("operacao");
        json.remove("operacao");
        this.funcoes = new String[json.length()];
        this.valores = new String[json.length()];
        for (String string : json.keySet()) {
            this.funcoes[i] = string;
            this.valores[i] = json.getString(string);
            i++;
        }
    }

    public String getFuncao(String funcao){
        for (int i = 0; i < this.funcoes.length; i++) {
            if(this.funcoes[i].equals(funcao)){
                return this.valores[i];
            }
        }
        return null;
    }

    public String getOperacao(){
        return this.operacao;
    }
}
