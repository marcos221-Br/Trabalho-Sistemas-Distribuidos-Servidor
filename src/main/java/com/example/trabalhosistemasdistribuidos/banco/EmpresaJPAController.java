package com.example.trabalhosistemasdistribuidos.banco;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import com.example.trabalhosistemasdistribuidos.modelo.Empresa;

public class EmpresaJPAController implements Serializable {
    
    private EntityManagerFactory emf = null;

    public EmpresaJPAController(EntityManagerFactory emf){
        this.emf = emf;
    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public Empresa encontrarEmpresa(int idEmpresa){
        EntityManager em = getEntityManager();
        try{
            return em.find(Empresa.class, idEmpresa);
        }finally{
            em.close();
        }
    }

    public void criar(Empresa Empresa) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(Empresa);
            em.getTransaction().commit();
        }catch (Exception ex){
            if(encontrarEmpresa(Empresa.getIdEmpresa()) != null){
                throw new Exception("Empresa " + Empresa + " já existente.",ex);
            }
            throw ex;
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    public void editar(Empresa Empresa) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa = em.merge(Empresa);
            em.getTransaction().commit();
        }catch(Exception ex){
            String msg = ex.getLocalizedMessage();
            if(msg == null || msg.length() == 0){
                int matricula = Empresa.getIdEmpresa();
                if(encontrarEmpresa(matricula) == null){
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

    public void deletar(int idEmpresa) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa Empresa;
            try{
                Empresa = em.getReference(Empresa.class, idEmpresa);
                Empresa.getIdEmpresa();
            }catch(EntityNotFoundException enfe){
                throw new Exception("O usuario com matricula " + idEmpresa + " não existe.",enfe);
            }
            em.remove(Empresa);
            em.getTransaction().commit();
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    private List<Empresa> encontrarEntidadesEmpresa(boolean tudo, int maximoResultados, int primeiroResultado){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresa.class));
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

    public List<Empresa> encontrarEntidadesEmpresa(int maximoResultados, int primeiroResultado){
        return encontrarEntidadesEmpresa(false,maximoResultados,primeiroResultado);
    }

    public List<Empresa> encontrarEntidadesEmpresa(){
        return encontrarEntidadesEmpresa(true, -1, -1);
    }

    public Empresa encontrarEmpresaLogin(String email){
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT u FROM Empresa u WHERE u.email = :email", Empresa.class).setParameter("email", email).getSingleResult();
        }catch(NoResultException NRE){
            System.out.println("Empresa não encontrado");
            return null;
        }finally{
            em.close();
        }
    }

    public Empresa encontrarEmpresaCNPJ(String cnpj){
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT u FROM Empresa u WHERE u.cnpj = :cnpj", Empresa.class).setParameter("cnpj", cnpj).getSingleResult();
        }catch(NoResultException NRE){
            System.out.println("Empresa não encontrado");
            return null;
        }finally{
            em.close();
        }
    }
}
