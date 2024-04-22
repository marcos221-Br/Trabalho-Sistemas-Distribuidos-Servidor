package com.example.trabalhosistemasdistribuidos.banco;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import com.example.trabalhosistemasdistribuidos.modelo.Candidato;

public class CandidatoJPAController implements Serializable{
    
    private EntityManagerFactory emf = null;

    public CandidatoJPAController(EntityManagerFactory emf){
        this.emf = emf;
    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public Candidato encontrarCandidato(int idCandidato){
        EntityManager em = getEntityManager();
        try{
            return em.find(Candidato.class, idCandidato);
        }finally{
            em.close();
        }
    }

    public void criar(Candidato candidato) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(candidato);
            em.getTransaction().commit();
        }catch (Exception ex){
            if(encontrarCandidato(candidato.getIdCandidato()) != null){
                throw new Exception("Candidato " + candidato + " já existente.",ex);
            }
            throw ex;
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    public void editar(Candidato candidato) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            candidato = em.merge(candidato);
            em.getTransaction().commit();
        }catch(Exception ex){
            String msg = ex.getLocalizedMessage();
            if(msg == null || msg.length() == 0){
                int matricula = candidato.getIdCandidato();
                if(encontrarCandidato(matricula) == null){
                    throw new Exception("O produto com matrícula " + matricula + " não existe.");
                }
            }
            throw ex;
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    public void deletar(int idCandidato) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Candidato candidato;
            try{
                candidato = em.getReference(Candidato.class, idCandidato);
                candidato.getIdCandidato();
            }catch(EntityNotFoundException enfe){
                throw new Exception("O usuario com matricula " + idCandidato + " não existe.",enfe);
            }
            em.remove(candidato);
            em.getTransaction().commit();
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    private List<Candidato> encontrarEntidadesCandidato(boolean tudo, int maximoResultados, int primeiroResultado){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Candidato.class));
            Query q = em.createQuery(cq);
            if(!tudo){
                q.setMaxResults(maximoResultados);
                q.setFirstResult(primeiroResultado);
            }
            return q.getResultList();
        }finally{
            em.close();
        }
    }

    public List<Candidato> encontrarEntidadesCandidato(int maximoResultados, int primeiroResultado){
        return encontrarEntidadesCandidato(false,maximoResultados,primeiroResultado);
    }

    public List<Candidato> encontrarEntidadesCandidato(){
        return encontrarEntidadesCandidato(true, -1, -1);
    }

    public Candidato encontrarCandidatoLogin(String email){
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT u FROM Candidato u WHERE u.email = :email", Candidato.class).setParameter("email", email).getSingleResult();
        }catch(NoResultException NRE){
            System.out.println("Candidato não encontrado");
            return null;
        }finally{
            em.close();
        }
    }
}
