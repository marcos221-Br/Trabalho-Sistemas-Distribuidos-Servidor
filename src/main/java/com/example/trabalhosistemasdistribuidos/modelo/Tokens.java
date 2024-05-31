package com.example.trabalhosistemasdistribuidos.modelo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tokens")
@NamedQueries({
    @NamedQuery(name = "Tokens.findAll", query = "SELECT u FROM Tokens u"),
    @NamedQuery(name = "Tokens.findByToken", query = "SELECT u FROM Tokens u WHERE u.tokenusuario = :tokenusuario")})
public class Tokens implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "tokenusuario")
    private String tokenusuario;

    public Tokens(){

    }

    public Tokens(String token){
        this.tokenusuario = token;
    }

    public String getToken(){
        return this.tokenusuario;
    }

    public void setToken(String token){
        this.tokenusuario = token;
    }
}
