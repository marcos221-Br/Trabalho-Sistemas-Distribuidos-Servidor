package com.example.trabalhosistemasdistribuidos.banco;

import java.util.List;

import com.example.trabalhosistemasdistribuidos.modelo.Vaga;

public class VagaDAO extends AbstrataDAO<VagaJPAController, Vaga>{
    
    public VagaDAO(){
        objetoJPA = new VagaJPAController(getEntityManagerFactory());
    }

    @Override
    public void cadastrar(Vaga objeto) throws Exception{
        objetoJPA.criar(objeto);
    }

    @Override
    public void editar(Vaga objeto) throws Exception{
        objetoJPA.editar(objeto);
    }

    @Override
    public void excluir(Vaga objeto) throws Exception{
        objetoJPA.deletar(objeto.getIdVaga());
    }

    @Override
    public Vaga buscar(Vaga objeto){
        return objetoJPA.encontrarVaga(objeto.getIdVaga());
    }

    @Override
    public List<Vaga> buscarTodos(){
        return objetoJPA.encontrarEntidadesVaga();
    }

    public Vaga buscarIdVaga(Vaga object){
        return objetoJPA.encontrarVagaNome(object.getIdEmpresa(), object.getNome());
    }

    public List<Vaga> buscarVagaEmail(Vaga object){
        return objetoJPA.encontrarVagaEmail(object.getIdEmpresa());
    }
}
