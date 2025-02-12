package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Organisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.*;

import java.util.List;

public class OrganisateurDao extends AbstractJpaDao<Long, Organisateur> {

    private Class<Organisateur> clazz;
    protected EntityManager entityManager;

    public OrganisateurDao() {
        super();
        //je ne comprends pas son utilité
        this.entityManager=EntityManagerHelper.getEntityManager();
    }
    //je comprends pas son utilité

    public void setClazz(Class<Organisateur> clazz) {
        this.clazz = clazz;
    }


    public Organisateur findOne(Long id) {
        return entityManager.find(Organisateur.class, id);
    }


    public List<Organisateur> findAll() {
        return entityManager.createQuery("SELECT e FROM "+clazz.getName()+" as e", clazz).getResultList();
    }


    public void save(Organisateur entity) {
        EntityTransaction transaction=entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
    }


    public Organisateur update(Organisateur entity) {
        EntityTransaction transaction=entityManager.getTransaction();
        transaction.begin();
        Organisateur res=entityManager.merge(entity);
        transaction.commit();
        return res;
    }


    public void delete(Organisateur entity) {
        EntityTransaction transaction=entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(entity);
        transaction.commit();
    }


    public void deleteById(Long entityId) {
        Organisateur entity = findOne(entityId);
        /* a verifier si c'est dans la doc ou pas */
        if (entity != null) {
            delete(entity);
        }
    }

}
