package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;


import java.util.List;

public class UserDao extends  AbstractJpaDao<Long, User> {

    private Class<User> clazz;
    //pourquoi protected ?
    protected EntityManager entityManager;


    public UserDao() {
        super();
        this.entityManager= EntityManagerHelper.getEntityManager();
    }

    public void setClazz(Class<User> clazzToSet) {

        this.clazz = clazzToSet;
    }


    public User findOne(Long id){
        return entityManager.find(User.class, id);
    }


    public List<User> findAll() {
        return entityManager.createQuery("SELECT e FROM "+clazz.getName()+" as e", clazz).getResultList();
    }



    public void save(User entity){
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
    }


    public User update(User entity){
        EntityTransaction transaction= entityManager.getTransaction();
        transaction.begin();
        User resultat= entityManager.merge(entity);
        transaction.commit();
        return resultat;
    }


    public void delete(User entity){
        EntityTransaction transaction= entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(entity);
        transaction.commit();
    }


    public void deleteById(Long entityId){
        User entity = findOne(entityId);
        if (entity != null) {
            delete(entity);
        }
    }

}
