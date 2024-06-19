package com.example.trabalhosistemasdistribuidos.banco;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import com.example.trabalhosistemasdistribuidos.modelo.CandidatoCompetencia;
import com.example.trabalhosistemasdistribuidos.modelo.VagaCompetencia;

public class CandidatoCompetenciaJPAController implements Serializable{
    
    private EntityManagerFactory emf = null;

    public CandidatoCompetenciaJPAController(EntityManagerFactory emf){
        this.emf = emf;
    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public CandidatoCompetencia encontrarCandidatoCompetencia(int idCandidatoCompetencia){
        EntityManager em = getEntityManager();
        try{
            return em.find(CandidatoCompetencia.class, idCandidatoCompetencia);
        }finally{
            em.close();
        }
    }

    public void criar(CandidatoCompetencia candidatoCompetencia) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(candidatoCompetencia);
            em.getTransaction().commit();
        }catch (Exception ex){
            if(encontrarCandidatoCompetencia(candidatoCompetencia.getIdCandidatoCompetencia()) != null){
                throw new Exception("CandidatoCompetencia " + candidatoCompetencia + " já existente.",ex);
            }
            throw ex;
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    public void editar(CandidatoCompetencia candidatoCompetencia) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            candidatoCompetencia = em.merge(candidatoCompetencia);
            em.getTransaction().commit();
        }catch(Exception ex){
            String msg = ex.getLocalizedMessage();
            if(msg == null || msg.length() == 0){
                int matricula = candidatoCompetencia.getIdCandidatoCompetencia();
                if(encontrarCandidatoCompetencia(matricula) == null){
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

    public void deletar(int idCandidatoCompetencia) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            CandidatoCompetencia candidatoCompetencia;
            try{
                candidatoCompetencia = em.getReference(CandidatoCompetencia.class, idCandidatoCompetencia);
                candidatoCompetencia.getIdCandidatoCompetencia();
            }catch(EntityNotFoundException enfe){
                throw new Exception("O usuario com matricula " + idCandidatoCompetencia + " não existe.",enfe);
            }
            em.remove(candidatoCompetencia);
            em.getTransaction().commit();
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<CandidatoCompetencia> encontrarEntidadesCandidatoCompetencia(boolean tudo, int maximoResultados, int primeiroResultado){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CandidatoCompetencia.class));
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

    public List<CandidatoCompetencia> encontrarEntidadesCandidatoCompetencia(int maximoResultados, int primeiroResultado){
        return encontrarEntidadesCandidatoCompetencia(false,maximoResultados,primeiroResultado);
    }

    public List<CandidatoCompetencia> encontrarEntidadesCandidatoCompetencia(){
        return encontrarEntidadesCandidatoCompetencia(true, -1, -1);
    }

    public List<CandidatoCompetencia> encontrarCandidatoCompetenciaCandidato(Integer idCandidato){
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT u FROM CandidatoCompetencia u WHERE u.idCandidato = :idCandidato", CandidatoCompetencia.class).setParameter("idCandidato", idCandidato).getResultList();
        }catch(NoResultException NRE){
            System.out.println("CandidatoCompetencia não encontrado");
            return null;
        }finally{
            em.close();
        }
    }

    public CandidatoCompetencia encontrarIdCandidatoCompetencia(Integer idCandidato, Integer idCompetencia){
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT u FROM CandidatoCompetencia u WHERE u.idCandidato = :idCandidato AND u.idCompetencia = :idCompetencia", CandidatoCompetencia.class).setParameter("idCandidato", idCandidato).setParameter("idCompetencia", idCompetencia).getSingleResult();
        }catch(NoResultException NRE){
            System.out.println("CandidatoCompetencia não encontrado");
            return null;
        }finally{
            em.close();
        }
    }

    public List<CandidatoCompetencia> encontrarCandidatoCompetenciaIdCompetencia(Integer idCompetencia){
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT u FROM CandidatoCompetencia u WHERE u.idCompetencia = :idCompetencia", CandidatoCompetencia.class).setParameter("idCompetencia", idCompetencia).getResultList();
        }catch(NoResultException NRE){
            System.out.println("Competencia não encontrado");
            return null;
        }finally{
            em.close();
        }
    }
}
