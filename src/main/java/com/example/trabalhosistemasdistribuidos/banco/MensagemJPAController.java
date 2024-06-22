package com.example.trabalhosistemasdistribuidos.banco;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import com.example.trabalhosistemasdistribuidos.modelo.Mensagem;

public class MensagemJPAController implements Serializable{
    
    private EntityManagerFactory emf = null;

    public MensagemJPAController(EntityManagerFactory emf){
        this.emf = emf;
    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public Mensagem encontrarMensagem(int idMensagem){
        EntityManager em = getEntityManager();
        try{
            return em.find(Mensagem.class, idMensagem);
        }finally{
            em.close();
        }
    }

    public void criar(Mensagem mensagem) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(mensagem);
            em.getTransaction().commit();
        }catch (Exception ex){
            if(encontrarMensagem(mensagem.getIdMensagem()) != null){
                throw new Exception("Mensagem " + mensagem + " já existente.",ex);
            }
            throw ex;
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    public void editar(Mensagem mensagem) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            mensagem = em.merge(mensagem);
            em.getTransaction().commit();
        }catch(Exception ex){
            String msg = ex.getLocalizedMessage();
            if(msg == null || msg.length() == 0){
                int matricula = mensagem.getIdMensagem();
                if(encontrarMensagem(matricula) == null){
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

    public void deletar(int idMensagem) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Mensagem mensagem;
            try{
                mensagem = em.getReference(Mensagem.class, idMensagem);
                mensagem.getIdMensagem();
            }catch(EntityNotFoundException enfe){
                throw new Exception("O usuario com matricula " + idMensagem + " não existe.",enfe);
            }
            em.remove(mensagem);
            em.getTransaction().commit();
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<Mensagem> encontrarEntidadesMensagem(boolean tudo, int maximoResultados, int primeiroResultado){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mensagem.class));
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

    public List<Mensagem> encontrarEntidadesMensagem(int maximoResultados, int primeiroResultado){
        return encontrarEntidadesMensagem(false,maximoResultados,primeiroResultado);
    }

    public List<Mensagem> encontrarEntidadesMensagem(){
        return encontrarEntidadesMensagem(true, -1, -1);
    }

    public List<Mensagem> encontrarMensagensCandidato(Integer idCandidato){
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT u FROM Mensagem u WHERE u.idCandidato = :idCandidato", Mensagem.class).setParameter("idCandidato", idCandidato).getResultList();
        }catch(NoResultException NRE){
            System.out.println("Mensagem não encontrada");
            return null;
        }finally{
            em.close();
        }
    }
}
