package com.example.trabalhosistemasdistribuidos.banco;

import java.util.List;

import com.example.trabalhosistemasdistribuidos.modelo.Tokens;

public class TokenDAO extends AbstrataDAO<TokenJPAController, Tokens>{
    
    public TokenDAO() {
        objetoJPA = new TokenJPAController(getEntityManagerFactory());
    }

    @Override
    public void cadastrar(Tokens objeto) throws Exception{
        objetoJPA.criar(objeto);
    }

    @Override
    public void excluir(Tokens objeto) throws Exception{
        objetoJPA.deletar(objeto.getToken());
    }

    @Override
    public Tokens buscar(Tokens objeto){
        return objetoJPA.encontrarToken(objeto.getToken());
    }

    @Override
    public void editar(Tokens objeto) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editar'");
    }

    @Override
    public List<Tokens> buscarTodos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarTodos'");
    }
}
