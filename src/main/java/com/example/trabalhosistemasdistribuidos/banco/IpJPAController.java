package com.example.trabalhosistemasdistribuidos.banco;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import com.example.trabalhosistemasdistribuidos.modelo.Ips;

public class IpJPAController implements Serializable {
    
    private EntityManagerFactory emf = null;

    public IpJPAController(EntityManagerFactory emf){
        this.emf = emf;
    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public Ips encontrarIp(String ip){
        EntityManager em = getEntityManager();
        try{
            return em.find(Ips.class, ip);
        }finally{
            em.close();
        }
    }

    public void criar(Ips ip) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ip);
            em.getTransaction().commit();
        }catch (Exception ex){
            if(encontrarIp(ip.getIp()) != null){
                throw new Exception("Ip " + ip + " já existente.",ex);
            }
            throw ex;
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    public void deletar(String ipString) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Ips ip;
            try{
                ip = em.getReference(Ips.class, ipString);
                ip.getIp();
            }catch(EntityNotFoundException enfe){
                throw new Exception("O ip " + ipString + " não existe.",enfe);
            }
            em.remove(ip);
            em.getTransaction().commit();
        }finally{
            if(em != null){
                em.close();
            }
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<Ips> encontrarEntidadesIp(boolean tudo, int maximoResultados, int primeiroResultado){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ips.class));
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

    public List<Ips> encontrarEntidadesIp(){
        return encontrarEntidadesIp(true, -1, -1);
    }
}
