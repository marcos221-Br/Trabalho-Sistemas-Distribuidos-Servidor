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
@Table(name = "empresa")
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT u FROM Empresa u"),
    @NamedQuery(name = "Empresa.findByIdEmpresa", query = "SELECT u FROM Empresa u WHERE u.idEmpresa = :idEmpresa"),
    @NamedQuery(name = "Empresa.findByEmail", query = "SELECT u FROM Empresa u WHERE u.email = :email"),
    @NamedQuery(name = "Empresa.findBySenha", query = "SELECT u FROM Empresa u WHERE u.senha = :senha"),
    @NamedQuery(name = "Empresa.findByCnpj", query = "SELECT u FROM Empresa u WHERE u.cnpj = :cnpj")})
public class Empresa implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEmpresa")
    private Integer idEmpresa;
    @Column(name = "email")
    private String email;
    @Column(name = "senha")
    private String senha;
    @Column(name = "cnpj")
    private String cnpj;
    @Column(name = "razaoSocial")
    private String razaoSocial;
    @Column(name = "ramo")
    private String ramo;
    @Column(name = "descricao")
    private String descricao;

    public Empresa() {

    }

    public Empresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Empresa(String email, String senha){
        this.email = email;
        this.senha = senha;
    }

    public Empresa(String email){
        this.email = email;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
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

    public String getCNPJ() {
        return cnpj;
    }

    public void setCNPJ(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.idEmpresa == null && other.idEmpresa != null) || (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Empresa:\nIdEmpresa: " + idEmpresa + "\nEmail: " + email + "\nSenha: " + senha;
    }
}
