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
@Table(name = "candidato")
@NamedQueries({
    @NamedQuery(name = "Candidato.findAll", query = "SELECT u FROM Candidato u"),
    @NamedQuery(name = "Candidato.findByIdcandidato", query = "SELECT u FROM Candidato u WHERE u.idCandidato = :idCandidato"),
    @NamedQuery(name = "Candidato.findByEmail", query = "SELECT u FROM Candidato u WHERE u.email = :email"),
    @NamedQuery(name = "Candidato.findBySenha", query = "SELECT u FROM Candidato u WHERE u.senha = :senha"),
    @NamedQuery(name = "Candidato.findByNome", query = "SELECT u FROM Candidato u WHERE u.nome = :nome")})
public class Candidato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idCandidato")
    private Integer idCandidato;
    @Column(name = "email")
    private String email;
    @Column(name = "senha")
    private String senha;
    @Column(name = "nome")
    private String nome;

    public Candidato() {

    }

    public Candidato(Integer idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Candidato(String email, String senha){
        this.email = email;
        this.senha = senha;
    }

    public Candidato(String email){
        this.email = email;
    }

    public Integer getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Integer idCandidato) {
        this.idCandidato = idCandidato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCandidato != null ? idCandidato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Candidato)) {
            return false;
        }
        Candidato other = (Candidato) object;
        if ((this.idCandidato == null && other.idCandidato != null) || (this.idCandidato != null && !this.idCandidato.equals(other.idCandidato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Candidato:\nIdCandidato: " + idCandidato + "\nEmail: " + email + "\nSenha: " + senha;
    }
    
}