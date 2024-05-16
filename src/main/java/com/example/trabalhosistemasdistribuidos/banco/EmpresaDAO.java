package com.example.trabalhosistemasdistribuidos.banco;

import java.util.List;

import com.example.trabalhosistemasdistribuidos.modelo.Empresa;

public class EmpresaDAO extends AbstrataDAO<EmpresaJPAController, Empresa>{
    
    public EmpresaDAO() {
        objetoJPA = new EmpresaJPAController(getEntityManagerFactory());
    }

    @Override
    public void cadastrar(Empresa objeto) throws Exception{
        objetoJPA.criar(objeto);
    }

    @Override
    public void editar(Empresa objeto) throws Exception{
        objetoJPA.editar(objeto);
    }

    @Override
    public void excluir(Empresa objeto) throws Exception{
        objetoJPA.deletar(objeto.getIdEmpresa());
    }

    @Override
    public Empresa buscar(Empresa objeto){
        return objetoJPA.encontrarEmpresa(objeto.getIdEmpresa());
    }

    @Override
    public List<Empresa> buscarTodos(){
        return objetoJPA.encontrarEntidadesEmpresa();
    }

    public Empresa buscarIdEmpresa(Empresa objeto){
        return objetoJPA.encontrarEmpresaLogin(objeto.getEmail());
    }

    public Empresa buscarCNPJEmpresa(Empresa objeto){
        return objetoJPA.encontrarEmpresaCNPJ(objeto.getCNPJ());
    }
}
