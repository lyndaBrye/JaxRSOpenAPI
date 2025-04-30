package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Ticket;
import jakarta.persistence.*;
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

    public int countAvailableTicketsByConcertId(Long concertId) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(t) FROM Ticket t WHERE t.concert.id = :concertId AND t.user IS NULL",
                Long.class
        );
        query.setParameter("concertId", concertId);
        return query.getSingleResult().intValue();
    }


}
