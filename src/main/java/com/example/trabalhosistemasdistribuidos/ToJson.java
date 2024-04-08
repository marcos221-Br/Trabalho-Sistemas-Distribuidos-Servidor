package com.example.trabalhosistemasdistribuidos;

public class ToJson {
    private String operacao;
    private String[] funcoes;
    private String[] valores;
    private String json;

    public ToJson(){
        this.operacao = "";
    }

    public ToJson(String operacao, String[] funcoes, String[] valores){
        this.operacao = operacao;
        this.funcoes = funcoes;
        this.valores = valores;
    }

    private void montarJson(){
        this.json = "{\"operacao\":\"" + operacao + "\"";
        for (int i = 0; i < funcoes.length; i++) {
            this.json += ",\"" + funcoes[i] + "\":\"" + valores[i] + "\"";
        }
        this.json += "}";
    }

    public String getJson(){
        montarJson();
        return this.json;
    }

    public void setJson(String json){
        String[] jsonSeparado;
        json = json.replace("{", "");
        json = json.replace("}", "");
        json = json.replace("\"", "");
        jsonSeparado = json.split(",");
        this.operacao = jsonSeparado[0].split(":")[1];
        this.funcoes = new String[jsonSeparado[1].length()];
        this.valores = new String[jsonSeparado[1].length()];
        for (int i = 1; i < jsonSeparado.length; i++) {
            this.funcoes[i-1] = jsonSeparado[i].split(":")[0];
            try{
                this.valores[i-1] = jsonSeparado[i].split(":")[1];
            }catch(ArrayIndexOutOfBoundsException AIOOBE){
                this.valores[i-1] = "";
            }
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
