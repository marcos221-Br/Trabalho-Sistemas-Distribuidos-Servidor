package com.example.trabalhosistemasdistribuidos.banco;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import com.example.trabalhosistemasdistribuidos.modelo.VagaCompetencia;

public class VagaCompetenciaJPAController implements Serializable{
    
    private EntityManagerFactory emf = null;

    public VagaCompetenciaJPAController(EntityManagerFactory emf){
        this.emf = emf;
    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public VagaCompetencia encontrarVagaCompetencia(int idVagaCompetencia){
        EntityManager em = getEntityManager();
        try{
            return em.find(VagaCompetencia.class, idVagaCompetencia);
        }finally{
            em.close();
        }
    }

    public void criar(VagaCompetencia vagaCompetencia) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vagaCompetencia);
            em.getTransaction().commit();
        }catch (Exception ex){
            if(encontrarVagaCompetencia(vagaCompetencia.getIdVagaCompetencia()) != null){
                throw new Exception("VagaCompetencia " + vagaCompetencia + " já existente.",ex);
            }
            throw ex;
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    public void editar(VagaCompetencia vagaCompetencia) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            vagaCompetencia = em.merge(vagaCompetencia);
            em.getTransaction().commit();
        }catch(Exception ex){
            String msg = ex.getLocalizedMessage();
            if(msg == null || msg.length() == 0){
                int matricula = vagaCompetencia.getIdVagaCompetencia();
                if(encontrarVagaCompetencia(matricula) == null){
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

    public void deletar(int idVagaCompetencia) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            VagaCompetencia vagaCompetencia;
            try{
                vagaCompetencia = em.getReference(VagaCompetencia.class, idVagaCompetencia);
                vagaCompetencia.getIdVagaCompetencia();
            }catch(EntityNotFoundException enfe){
                throw new Exception("A vagaCompetencia com matricula " + idVagaCompetencia + " não existe.",enfe);
            }
            em.remove(vagaCompetencia);
            em.getTransaction().commit();
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<VagaCompetencia> encontrarEntidadesVagaCompetencia(boolean tudo, int maximoResultados, int primeiroResultado){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VagaCompetencia.class));
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

    public List<VagaCompetencia> encontrarEntidadesVagaCompetencia(int maximoResultados, int primeiroResultado){
        return encontrarEntidadesVagaCompetencia(false,maximoResultados,primeiroResultado);
    }

    public List<VagaCompetencia> encontrarEntidadesVagaCompetencia(){
        return encontrarEntidadesVagaCompetencia(true, -1, -1);
    }

    public List<VagaCompetencia> encontrarVagaCompetenciaIdVaga(Integer idVaga){
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT u FROM VagaCompetencia u WHERE u.idVaga = :idVaga", VagaCompetencia.class).setParameter("idVaga", idVaga).getResultList();
        }catch(NoResultException NRE){
            System.out.println("Competencia não encontrado");
            return null;
        }finally{
            em.close();
        }
    }

    public List<VagaCompetencia> encontrarVagaCompetenciaIdCompetencia(Integer idCompetencia){
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT u FROM VagaCompetencia u WHERE u.idCompetencia = :idCompetencia", VagaCompetencia.class).setParameter("idCompetencia", idCompetencia).getResultList();
        }catch(NoResultException NRE){
            System.out.println("Competencia não encontrado");
            return null;
        }finally{
            em.close();
        }
    }
}
