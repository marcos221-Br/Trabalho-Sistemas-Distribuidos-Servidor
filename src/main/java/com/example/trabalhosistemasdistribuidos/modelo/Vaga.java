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
@Table(name = "vaga")
@NamedQueries({
    @NamedQuery(name = "Vaga.findAll", query = "SELECT u FROM Vaga u"),
    @NamedQuery(name = "Vaga.findByIdvaga", query = "SELECT u FROM Vaga u WHERE u.idVaga = :idVaga"),
    @NamedQuery(name = "Vaga.findByIdempresa", query = "SELECT u FROM Vaga u WHERE u.idEmpresa = :idEmpresa"),
    @NamedQuery(name = "Vaga.findByFaixasalarial", query = "SELECT u FROM Vaga u WHERE u.faixaSalarial = :faixaSalarial"),
    @NamedQuery(name = "Vaga.findByDescricao", query = "SELECT u FROM Vaga u WHERE u.descricao = :descricao"),
    @NamedQuery(name = "Vaga.findByEstado", query = "SELECT u FROM Vaga u WHERE u.estado = :estado"),
    @NamedQuery(name = "Vaga.findByNome", query = "SELECT u FROM Vaga u WHERE u.nome = :nome")})
public class Vaga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idVaga")
    private Integer idVaga;
    @Column(name = "idEmpresa")
    private Integer idEmpresa;
    @Column(name = "faixaSalarial")
    private float faixaSalarial;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "estado")
    private String estado;
    @Column(name = "nome")
    private String nome;

    public Vaga(Integer idVaga, Integer idEmpresa, float faixaSalarial, String descricao, String estado, String nome) {
        this.idVaga = idVaga;
        this.idEmpresa = idEmpresa;
        this.faixaSalarial = faixaSalarial;
        this.descricao = descricao;
        this.estado = estado;
        this.nome = nome;
    }

    public Vaga() {

    }

    public Vaga(Integer idVaga, Integer idEmpresa){
        this.idVaga = idVaga;
        this.idEmpresa = idEmpresa;
    }

    public Vaga(Integer idEmpresa){
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(Integer idVaga) {
        this.idVaga = idVaga;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public float getFaixaSalarial() {
        return faixaSalarial;
    }

    public void setFaixaSalarial(float faixaSalarial) {
        this.faixaSalarial = faixaSalarial;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        hash += (idVaga != null ? idVaga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Vaga)) {
            return false;
        }
        Vaga other = (Vaga) object;
        if ((this.idVaga == null && other.idVaga != null) || (this.idVaga != null && !this.idVaga.equals(other.idVaga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Vaga:\nIdVaga: " + idVaga + "\nNome: " + nome;
    }
    
}