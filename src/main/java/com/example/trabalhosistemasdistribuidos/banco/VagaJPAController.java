package com.example.trabalhosistemasdistribuidos.banco;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import com.example.trabalhosistemasdistribuidos.modelo.Vaga;

public class VagaJPAController implements Serializable{
    
    private EntityManagerFactory emf = null;

    public VagaJPAController(EntityManagerFactory emf){
        this.emf = emf;
    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public Vaga encontrarVaga(int idVaga){
        EntityManager em = getEntityManager();
        try{
            return em.find(Vaga.class, idVaga);
        }finally{
            em.close();
        }
    }

    public void criar(Vaga vaga) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vaga);
            em.getTransaction().commit();
        }catch (Exception ex){
            if(encontrarVaga(vaga.getIdVaga()) != null){
                throw new Exception("Vaga " + vaga + " já existente.",ex);
            }
            throw ex;
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    public void editar(Vaga vaga) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            vaga = em.merge(vaga);
            em.getTransaction().commit();
        }catch(Exception ex){
            String msg = ex.getLocalizedMessage();
            if(msg == null || msg.length() == 0){
                int matricula = vaga.getIdVaga();
                if(encontrarVaga(matricula) == null){
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

    public void deletar(int idVaga) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Vaga vaga;
            try{
                vaga = em.getReference(Vaga.class, idVaga);
                vaga.getIdVaga();
            }catch(EntityNotFoundException enfe){
                throw new Exception("A vaga com matricula " + idVaga + " não existe.",enfe);
            }
            em.remove(vaga);
            em.getTransaction().commit();
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<Vaga> encontrarEntidadesVaga(boolean tudo, int maximoResultados, int primeiroResultado){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vaga.class));
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

    public List<Vaga> encontrarEntidadesVaga(int maximoResultados, int primeiroResultado){
        return encontrarEntidadesVaga(false,maximoResultados,primeiroResultado);
    }

    public List<Vaga> encontrarEntidadesVaga(){
        return encontrarEntidadesVaga(true, -1, -1);
    }

    public Vaga encontrarVagaNome(Integer idEmpresa, String nome){
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT u FROM Vaga u WHERE u.idEmpresa = :idEmpresa AND u.nome = :nome", Vaga.class).setParameter("idEmpresa", idEmpresa).setParameter("nome", nome).getSingleResult();
        }catch(NoResultException NRE){
            System.out.println("Vaga não encontrada");
            return null;
        }finally{
            em.close();
        }
    }

    public List<Vaga> encontrarVagaEmail(Integer idEmpresa){
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT u FROM Vaga u WHERE u.idEmpresa = :idEmpresa", Vaga.class).setParameter("idEmpresa", idEmpresa).getResultList();
        }catch(NoResultException NRE){
            System.out.println("Vagas não encontradas");
            return null;
        }finally{
            em.close();
        }
    }
}
