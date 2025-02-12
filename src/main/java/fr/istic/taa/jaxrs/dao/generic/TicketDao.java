package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class TicketDao extends AbstractJpaDao<Long, Ticket> {
    protected EntityManager entityManager;
    private Class<Ticket> clazz;
    public TicketDao() {
        this.entityManager= EntityManagerHelper.getEntityManager();
    }
    public void setClazz(Class<Ticket> clazzToSet) {

        this.clazz = clazzToSet;
    }
    public Ticket findOne(Long id) {
        return entityManager.find(clazz,id);
    }
    public List<Ticket> findAll(){
        return entityManager.createQuery("SELECT e FROM"+clazz.getName()+"as e",clazz).getResultList();
    }

    public void save(Ticket entity){
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
    }
    public Ticket update(Ticket entity){
        EntityTransaction transaction=entityManager.getTransaction();
        transaction.begin();
        Ticket resultat=entityManager.merge(entity);
        transaction.commit();
        return resultat;
    }

    public void delete(Ticket entity){
        EntityTransaction transaction=entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(entity);
        transaction.commit();
    }

    public void deleteById(Long id){
        Ticket reslt=findOne(id);
        delete(reslt);
    }
}
