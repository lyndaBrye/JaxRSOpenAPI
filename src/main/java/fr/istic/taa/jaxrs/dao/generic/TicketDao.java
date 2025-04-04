package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;

public class TicketDao extends AbstractJpaDao<Long, Ticket> {

    public TicketDao() {
        super();
        setClazz(Ticket.class);
    }

    public List<Ticket> findAllWithConcertAndUser() {
        return entityManager.createQuery(
                "SELECT t FROM Ticket t LEFT JOIN FETCH t.concert LEFT JOIN FETCH t.user",
                Ticket.class
        ).getResultList();
    }
    public List<Ticket> findAvailableTicketsByConcertId(Long concertId) {
        return entityManager.createQuery(
                        "SELECT t FROM Ticket t WHERE t.concert.id = :concertId AND t.user.id IS null ", Ticket.class)
                .setParameter("concertId", concertId)
                .getResultList();
    }

}
