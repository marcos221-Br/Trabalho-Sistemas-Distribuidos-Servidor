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
@Table(name = "competencia")
@NamedQueries({
    @NamedQuery(name = "Competencia.findAll", query = "SELECT u FROM Competencia u"),
    @NamedQuery(name = "Competencia.findByIdcompetencia", query = "SELECT u FROM Competencia u WHERE u.idCompetencia = :idCompetencia"),
    @NamedQuery(name = "Competencia.findByCompetencia", query = "SELECT u FROM Competencia u WHERE u.competencia = :competencia")})
public class Competencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idCompetencia")
    private Integer idCompetencia;
    @Column(name = "competencia")
    private String competencia;

    public Competencia() {

    }

    public Competencia(String competencia) {
        this.competencia = competencia;
    }

    public Competencia(Integer idCompetencia, String competencia){
        this.idCompetencia = idCompetencia;
        this.competencia = competencia;
    }

    public Competencia(Integer idCompetencia){
        this.idCompetencia = idCompetencia;
    }

    public Integer getIdCompetencia() {
        return idCompetencia;
    }

    public void setIdCompetencia(Integer idCompetencia) {
        this.idCompetencia = idCompetencia;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompetencia != null ? idCompetencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Competencia)) {
            return false;
        }
        Competencia other = (Competencia) object;
        if ((this.idCompetencia == null && other.idCompetencia != null) || (this.idCompetencia != null && !this.idCompetencia.equals(other.idCompetencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Competencia:\nIdCompetencia: " + idCompetencia + "\nCompetencia: " + competencia;
    }
    
}