package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;


import java.util.List;

public class UserDao extends  AbstractJpaDao<Long, User> {
    public UserDao() {
        super();

    }



}
