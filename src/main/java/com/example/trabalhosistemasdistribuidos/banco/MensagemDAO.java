package com.example.trabalhosistemasdistribuidos.banco;

import java.util.List;

import com.example.trabalhosistemasdistribuidos.modelo.Mensagem;

public class MensagemDAO extends AbstrataDAO<MensagemJPAController, Mensagem>{
    
    public MensagemDAO() {
        objetoJPA = new MensagemJPAController(getEntityManagerFactory());
    }

    @Override
    public void cadastrar(Mensagem objeto) throws Exception{
        objetoJPA.criar(objeto);
    }

    @Override
    public void editar(Mensagem objeto) throws Exception{
        objetoJPA.editar(objeto);
    }

    @Override
    public void excluir(Mensagem objeto) throws Exception{
        objetoJPA.deletar(objeto.getIdEmpresa());
    }

    @Override
    public Mensagem buscar(Mensagem objeto){
        return objetoJPA.encontrarMensagem(objeto.getIdMensagem());
    }

    @Override
    public List<Mensagem> buscarTodos(){
        return objetoJPA.encontrarEntidadesMensagem();
    }

    public List<Mensagem> buscarMensagensCandidato(Mensagem objeto){
        return objetoJPA.encontrarMensagensCandidato(objeto.getIdCandidato());
    }
}
