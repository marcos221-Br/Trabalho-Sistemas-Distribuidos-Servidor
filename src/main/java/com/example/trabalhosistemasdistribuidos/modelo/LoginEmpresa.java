package com.example.trabalhosistemasdistribuidos.modelo;

import com.example.trabalhosistemasdistribuidos.banco.EmpresaDAO;

public class LoginEmpresa {
    private String login;
    private String senha;
    private Empresa Empresa;
    private EmpresaDAO jpa;

    public boolean buscar(){ // Busca o usuário no banco para verificação
        Empresa = new Empresa();
        jpa = new EmpresaDAO();
        Empresa.setEmail(this.login);
        Empresa.setSenha(this.senha);
        try{
            Empresa = jpa.buscarIdEmpresa(Empresa);
            if(this.login.equals(Empresa.getEmail()) && this.senha.equals(Empresa.getSenha()+"")){
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
