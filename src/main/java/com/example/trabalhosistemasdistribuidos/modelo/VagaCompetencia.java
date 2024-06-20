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
@Table(name = "vagacompetencia")
@NamedQueries({
    @NamedQuery(name = "VagaCompetencia.findAll", query = "SELECT u FROM VagaCompetencia u"),
    @NamedQuery(name = "VagaCompetencia.findByIdvaga", query = "SELECT u FROM VagaCompetencia u WHERE u.idVaga = :idVaga"),
    @NamedQuery(name = "VagaCompetencia.findByIdcompetencia", query = "SELECT u FROM VagaCompetencia u WHERE u.idCompetencia = :idCompetencia"),
    @NamedQuery(name = "VagaCompetencia.findByIdvagacompetencia", query = "SELECT u FROM VagaCompetencia u WHERE u.idVagaCompetencia = :idVagaCompetencia")})
public class VagaCompetencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVagaCompetencia")
    private Integer idVagaCompetencia;
    @Column(name = "idVaga")
    private Integer idVaga;
    @Column(name = "idCompetencia")
    private Integer idCompetencia;

    public VagaCompetencia(Integer idVaga, Integer idCompetencia) {
        this.idVaga = idVaga;
        this.idCompetencia = idCompetencia;
    }

    public VagaCompetencia(Integer idVaga){
        this.idVaga = idVaga;
    }

    public VagaCompetencia() {

    }

    public Integer getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(Integer idVaga) {
        this.idVaga = idVaga;
    }

    public Integer getIdCompetencia() {
        return idCompetencia;
    }

    public void setIdCompetencia(Integer idCompetencia) {
        this.idCompetencia = idCompetencia;
    }

    public Integer getIdVagaCompetencia() {
        return idVagaCompetencia;
    }

    public void setIdVagaCompetencia(Integer idVagaCompetencia) {
        this.idVagaCompetencia = idVagaCompetencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVagaCompetencia != null ? idVagaCompetencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Vaga)) {
            return false;
        }
        VagaCompetencia other = (VagaCompetencia) object;
        if ((this.idVagaCompetencia == null && other.idVagaCompetencia != null) || (this.idVagaCompetencia != null && !this.idVagaCompetencia.equals(other.idVagaCompetencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VagaCompetencia:\nIdVaga: " + idVagaCompetencia + "\nIdVaga: " + idVaga + "\nIdCompetencia: " + idCompetencia;
    }
    
}