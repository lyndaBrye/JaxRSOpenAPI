package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;


import java.util.List;

public class UserDao extends  AbstractJpaDao<Long, User> {
    public UserDao() {
        super();

    }
    public User authenticate(String username, String password) {
        try {
            return entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.nom = :username AND u.password = :password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
