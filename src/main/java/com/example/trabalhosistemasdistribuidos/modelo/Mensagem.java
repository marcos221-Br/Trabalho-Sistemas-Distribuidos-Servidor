package com.example.trabalhosistemasdistribuidos.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "mensagem")
@NamedQueries({
    @NamedQuery(name = "Mensagem.findAll", query = "SELECT u FROM Mensagem u"),
    @NamedQuery(name = "Mensagem.findByIdcandidato", query = "SELECT u FROM Mensagem u WHERE u.idCandidato = :idCandidato"),
    @NamedQuery(name = "Mensagem.findByIdempresa", query = "SELECT u FROM Mensagem u WHERE u.idCandidato = :idCandidato"),
    @NamedQuery(name = "Mensagem.findByIdMensagem", query = "SELECT u FROM Mensagem u WHERE u.id = :id")})
public class Mensagem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMensagem")
    private Integer idMensagem;
    @Column(name = "idCandidato")
    private Integer idCandidato;
    @Column(name = "idEmpresa")
    private Integer idEmpresa;

    public Mensagem() {

    }

    public Mensagem(Integer idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Integer getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Integer idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Integer getIdEmpresa() {
        return this.idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdMensagem() {
        return this.idMensagem;
    }

    public void setIdMensagem(Integer idMensagem) {
        this.idMensagem = idMensagem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMensagem!= null ? idMensagem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Mensagem)) {
            return false;
        }
        Mensagem other = (Mensagem) object;
        if ((this.idMensagem == null && other.idMensagem != null) || (this.idMensagem != null && !this.idMensagem.equals(other.idMensagem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mensagem:\nidMensagem: " + idMensagem + "\nIdCandidato: " + idCandidato + "\nidEmpresa: " + idEmpresa;
    }
    
}