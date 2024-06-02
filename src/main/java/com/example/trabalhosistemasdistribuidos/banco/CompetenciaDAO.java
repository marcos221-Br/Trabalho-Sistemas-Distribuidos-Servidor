package com.example.trabalhosistemasdistribuidos.banco;

import java.util.List;

import com.example.trabalhosistemasdistribuidos.modelo.Competencia;

public class CompetenciaDAO extends AbstrataDAO<CompetenciaJPAController, Competencia>{
    
    public CompetenciaDAO(){
        objetoJPA = new CompetenciaJPAController(getEntityManagerFactory());
    }

    @Override
    public void cadastrar(Competencia objeto) throws Exception{
        objetoJPA.criar(objeto);
    }

    @Override
    public void editar(Competencia objeto) throws Exception{
        objetoJPA.editar(objeto);
    }

    @Override
    public void excluir(Competencia objeto) throws Exception{
        objetoJPA.deletar(objeto.getIdCompetencia());
    }

    @Override
    public Competencia buscar(Competencia objeto){
        return objetoJPA.encontrarCompetencia(objeto.getIdCompetencia());
    }

    @Override
    public List<Competencia> buscarTodos(){
        return objetoJPA.encontrarEntidadesCompetencia();
    }

    public Competencia buscarIdCompetencia(Competencia object){
        return objetoJPA.encontrarCompetenciaNome(object.getCompetencia());
    }
}
