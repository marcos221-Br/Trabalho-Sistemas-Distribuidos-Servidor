package com.example.trabalhosistemasdistribuidos.banco;

import java.util.List;

import com.example.trabalhosistemasdistribuidos.modelo.VagaCompetencia;

public class VagaCompetenciaDAO extends AbstrataDAO<VagaCompetenciaJPAController, VagaCompetencia>{
    
    public VagaCompetenciaDAO(){
        objetoJPA = new VagaCompetenciaJPAController(getEntityManagerFactory());
    }

    @Override
    public void cadastrar(VagaCompetencia objeto) throws Exception{
        objetoJPA.criar(objeto);
    }

    @Override
    public void editar(VagaCompetencia objeto) throws Exception{
        objetoJPA.editar(objeto);
    }

    @Override
    public void excluir(VagaCompetencia objeto) throws Exception{
        objetoJPA.deletar(objeto.getIdVagaCompetencia());
    }

    @Override
    public VagaCompetencia buscar(VagaCompetencia objeto){
        return objetoJPA.encontrarVagaCompetencia(objeto.getIdVaga());
    }

    @Override
    public List<VagaCompetencia> buscarTodos(){
        return objetoJPA.encontrarEntidadesVagaCompetencia();
    }

    public List<VagaCompetencia> buscarVagaCompetenciaIdVagas(VagaCompetencia object){
         return objetoJPA.encontrarVagaCompetenciaIdVaga(object.getIdVaga());
    }
}
