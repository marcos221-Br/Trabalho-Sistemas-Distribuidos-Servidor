package com.example.trabalhosistemasdistribuidos.banco;

import java.util.List;

import com.example.trabalhosistemasdistribuidos.modelo.Ips;

public class IpDAO extends AbstrataDAO<IpJPAController, Ips>{
    
    public IpDAO() {
        objetoJPA = new IpJPAController(getEntityManagerFactory());
    }

    @Override
    public void cadastrar(Ips objeto) throws Exception{
        objetoJPA.criar(objeto);
    }

    @Override
    public void excluir(Ips objeto) throws Exception{
        objetoJPA.deletar(objeto.getIp());
    }

    @Override
    public Ips buscar(Ips objeto){
        return objetoJPA.encontrarIp(objeto.getIp());
    }

    @Override
    public void editar(Ips objeto) throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'editar'");
    }

    @Override
    public List<Ips> buscarTodos() {
        return objetoJPA.encontrarEntidadesIp();
    }
}
