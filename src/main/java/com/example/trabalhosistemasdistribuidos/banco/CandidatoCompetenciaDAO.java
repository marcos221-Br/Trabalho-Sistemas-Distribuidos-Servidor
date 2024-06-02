package com.example.trabalhosistemasdistribuidos.banco;

import java.util.List;

import com.example.trabalhosistemasdistribuidos.modelo.Candidato;
import com.example.trabalhosistemasdistribuidos.modelo.CandidatoCompetencia;

public class CandidatoCompetenciaDAO extends AbstrataDAO<CandidatoCompetenciaJPAController, CandidatoCompetencia>{
    
    public CandidatoCompetenciaDAO(){
        objetoJPA = new CandidatoCompetenciaJPAController(getEntityManagerFactory());
    }

    @Override
    public void cadastrar(CandidatoCompetencia objeto) throws Exception{
        objetoJPA.criar(objeto);
    }

    @Override
    public void editar(CandidatoCompetencia objeto) throws Exception{
        objetoJPA.editar(objeto);
    }

    @Override
    public void excluir(CandidatoCompetencia objeto) throws Exception{
        objetoJPA.deletar(objeto.getIdCandidatoCompetencia());
    }

    @Override
    public CandidatoCompetencia buscar(CandidatoCompetencia objeto){
        return objetoJPA.encontrarCandidatoCompetencia(objeto.getIdCandidatoCompetencia());
    }

    @Override
    public List<CandidatoCompetencia> buscarTodos(){
        return objetoJPA.encontrarEntidadesCandidatoCompetencia();
    }

    public List<CandidatoCompetencia> buscarCompetenciasCandidato(Candidato object){
        return objetoJPA.encontrarCandidatoCompetenciaCandidato(object.getIdCandidato());
    }

    public CandidatoCompetencia buscarIdCandidatoCompetencia(CandidatoCompetencia object){
        return objetoJPA.encontrarIdCandidatoCompetencia(object.getIdCandidato(), object.getIdCompetencia());
    }
}
