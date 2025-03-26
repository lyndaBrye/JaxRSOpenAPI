package fr.istic.taa.jaxrs.dao.generic;


import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;


import java.util.List;

public class UserDao extends  AbstractJpaDao<Long, User> {
    public UserDao() {
        super();
        setClazz(User.class);

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
    public List<Ticket> getTicketsByUserId(Long userId) {
        User user = entityManager.find(User.class, userId);  // Récupérer l'objet User par son ID
        if (user == null) {
            return null;
        }
        return entityManager.createQuery(
                        "SELECT t FROM Ticket t WHERE t.user = :user", Ticket.class)
                .setParameter("user", user)  // Passer l'objet User
                .getResultList();
    }
}
