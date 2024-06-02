package com.example.trabalhosistemasdistribuidos.banco;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import com.example.trabalhosistemasdistribuidos.modelo.Competencia;

public class CompetenciaJPAController implements Serializable{
    
    private EntityManagerFactory emf = null;

    public CompetenciaJPAController(EntityManagerFactory emf){
        this.emf = emf;
    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public Competencia encontrarCompetencia(int idCompetencia){
        EntityManager em = getEntityManager();
        try{
            return em.find(Competencia.class, idCompetencia);
        }finally{
            em.close();
        }
    }

    public void criar(Competencia competencia) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(competencia);
            em.getTransaction().commit();
        }catch (Exception ex){
            if(encontrarCompetencia(competencia.getIdCompetencia()) != null){
                throw new Exception("Competencia " + competencia + " já existente.",ex);
            }
            throw ex;
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    public void editar(Competencia competencia) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            competencia = em.merge(competencia);
            em.getTransaction().commit();
        }catch(Exception ex){
            String msg = ex.getLocalizedMessage();
            if(msg == null || msg.length() == 0){
                int matricula = competencia.getIdCompetencia();
                if(encontrarCompetencia(matricula) == null){
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

    public void deletar(int idCompetencia) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Competencia competencia;
            try{
                competencia = em.getReference(Competencia.class, idCompetencia);
                competencia.getIdCompetencia();
            }catch(EntityNotFoundException enfe){
                throw new Exception("O usuario com matricula " + idCompetencia + " não existe.",enfe);
            }
            em.remove(competencia);
            em.getTransaction().commit();
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<Competencia> encontrarEntidadesCompetencia(boolean tudo, int maximoResultados, int primeiroResultado){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Competencia.class));
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

    public List<Competencia> encontrarEntidadesCompetencia(int maximoResultados, int primeiroResultado){
        return encontrarEntidadesCompetencia(false,maximoResultados,primeiroResultado);
    }

    public List<Competencia> encontrarEntidadesCompetencia(){
        return encontrarEntidadesCompetencia(true, -1, -1);
    }

    public Competencia encontrarCompetenciaNome(String competencia){
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT u FROM Competencia u WHERE u.competencia = :competencia", Competencia.class).setParameter("competencia", competencia).getSingleResult();
        }catch(NoResultException NRE){
            System.out.println("Competencia não encontrado");
            return null;
        }finally{
            em.close();
        }
    }
}
