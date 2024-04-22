package com.example.trabalhosistemasdistribuidos.banco;

import java.util.List;

import com.example.trabalhosistemasdistribuidos.modelo.Candidato;

public class CandidatoDAO extends AbstrataDAO<CandidatoJPAController, Candidato>{
    
    public CandidatoDAO(){
        objetoJPA = new CandidatoJPAController(getEntityManagerFactory());
    }

    @Override
    public void cadastrar(Candidato objeto) throws Exception{
        objetoJPA.criar(objeto);
    }

    @Override
    public void editar(Candidato objeto) throws Exception{
        objetoJPA.editar(objeto);
    }

    @Override
    public void excluir(Candidato objeto) throws Exception{
        objetoJPA.deletar(objeto.getIdCandidato());
    }

    @Override
    public Candidato buscar(Candidato objeto){
        return objetoJPA.encontrarCandidato(objeto.getIdCandidato());
    }

    @Override
    public List<Candidato> buscarTodos(){
        return objetoJPA.encontrarEntidadesCandidato();
    }

    public Candidato buscarIdCandidato(Candidato objeto){
        return objetoJPA.encontrarCandidatoLogin(objeto.getEmail());
    }
}
