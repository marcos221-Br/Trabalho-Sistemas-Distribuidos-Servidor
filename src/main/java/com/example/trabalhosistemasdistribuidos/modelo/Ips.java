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
@Table(name = "ips")
@NamedQueries({
    @NamedQuery(name = "Ips.findAll", query = "SELECT u FROM Ips u"),
    @NamedQuery(name = "Ips.findByIp", query = "SELECT u FROM Ips u WHERE u.ip = :ip")})
public class Ips implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ip")
    private String ip;

    public Ips(){

    }
    
    public Ips(String ip){
        this.ip = ip;
    }

    public String getIp(){
        return this.ip;
    }
}
