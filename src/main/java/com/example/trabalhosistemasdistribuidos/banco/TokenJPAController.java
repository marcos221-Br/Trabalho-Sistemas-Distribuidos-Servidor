package com.example.trabalhosistemasdistribuidos.banco;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;

import com.example.trabalhosistemasdistribuidos.modelo.Tokens;

public class TokenJPAController implements Serializable {
    
    private EntityManagerFactory emf = null;

    public TokenJPAController(EntityManagerFactory emf){
        this.emf = emf;
    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public Tokens encontrarToken(String token){
        EntityManager em = getEntityManager();
        try{
            return em.find(Tokens.class, token);
        }finally{
            em.close();
        }
    }

    public void criar(Tokens token) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(token);
            em.getTransaction().commit();
        }catch (Exception ex){
            if(encontrarToken(token.getToken()) != null){
                throw new Exception("Token " + token + " já existente.",ex);
            }
            throw ex;
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    public void deletar(String tokenString) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Tokens token;
            try{
                token = em.getReference(Tokens.class, tokenString);
                token.getToken();
            }catch(EntityNotFoundException enfe){
                throw new Exception("O token " + tokenString + " não existe.",enfe);
            }
            em.remove(token);
            em.getTransaction().commit();
        }finally{
            if(em != null){
                em.close();
            }
        }
    }
}
