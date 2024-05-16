package com.example.trabalhosistemasdistribuidos.modelo;

import com.example.trabalhosistemasdistribuidos.banco.CandidatoDAO;

public class LoginCandidato {
    private String login;
    private String senha;
    private Candidato candidato;
    private CandidatoDAO jpa;

    public boolean buscar(){ // Busca o usuário no banco para verificação
        candidato = new Candidato();
        jpa = new CandidatoDAO();
        candidato.setEmail(this.login);
        candidato.setSenha(this.senha);
        try{
            candidato = jpa.buscarIdCandidato(candidato);
            if(this.login.equals(candidato.getEmail()) && this.senha.equals(candidato.getSenha()+"")){
                return true;
            }
            return false;
        }catch(NullPointerException NPE){
            return false;
        }
    }

    public void setLogin(String login){
        this.login = login;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getLogin(){
        return this.login;
    }
}
