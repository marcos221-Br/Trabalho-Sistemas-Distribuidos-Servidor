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
@Table(name = "candidatocompetencia")
@NamedQueries({
    @NamedQuery(name = "CandidatoCompetencia.findAll", query = "SELECT u FROM CandidatoCompetencia u"),
    @NamedQuery(name = "CandidatoCompetencia.findByIdcandidatocompetencia", query = "SELECT u FROM CandidatoCompetencia u WHERE u.idCandidatoCompetencia = :idCandidatoCompetencia"),
    @NamedQuery(name = "CandidatoCompetencia.findByIdCandidato", query = "SELECT u FROM CandidatoCompetencia u WHERE u.idCandidato = :idCandidato"),
    @NamedQuery(name = "CandidatoCompetencia.findByIdCompetencia", query = "SELECT u FROM CandidatoCompetencia u WHERE u.idCompetencia = :idCompetencia"),
    @NamedQuery(name = "CandidatoCompetencia.findByTempo", query = "SELECT u FROM CandidatoCompetencia u WHERE u.tempo = :tempo")})
public class CandidatoCompetencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCandidatoCompetencia")
    private Integer idCandidatoCompetencia;
    @Column(name = "idCandidato")
    private Integer idCandidato;
    @Column(name = "idCompetencia")
    private Integer idCompetencia;
    @Column(name = "tempo")
    private Integer tempo;

    public CandidatoCompetencia() {

    }

    public CandidatoCompetencia(Integer idCandidatoCompetencia) {
        this.idCandidatoCompetencia = idCandidatoCompetencia;
    }

    public CandidatoCompetencia(Integer idCandidato, Integer idCompetencia, Integer tempo){
        this.idCandidato = idCandidato;
        this.idCompetencia = idCompetencia;
        this.tempo = tempo;
    }

    public CandidatoCompetencia(Integer idCandidato, Integer idCompetencia){
        this.idCandidato = idCandidato;
        this.idCompetencia = idCompetencia;
    }

    public Integer getIdCandidatoCompetencia() {
        return idCandidatoCompetencia;
    }

    public void setIdCandidatoCompetencia(Integer idCandidatoCompetencia) {
        this.idCandidatoCompetencia = idCandidatoCompetencia;
    }

    public Integer getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Integer idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Integer getIdCompetencia(){
        return this.idCompetencia;
    }

    public void setIdCompetencia(Integer idCompetencia){
        this.idCompetencia = idCompetencia;
    }

    public Integer getTempo(){
        return this.tempo;
    }

    public void setTempo(Integer tempo){
        this.tempo = tempo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCandidatoCompetencia != null ? idCandidatoCompetencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CandidatoCompetencia)) {
            return false;
        }
        CandidatoCompetencia other = (CandidatoCompetencia) object;
        if ((this.idCandidatoCompetencia == null && other.idCandidatoCompetencia != null) || (this.idCandidatoCompetencia != null && !this.idCandidatoCompetencia.equals(other.idCandidatoCompetencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CandidatoCompetencia:\nIdCandidatoCompetencia: " + idCandidatoCompetencia + "\nIdCandidato: " + idCandidato + "\nIdCompetencia: " + idCompetencia + "\nTempo: " + tempo;
    }
    
}