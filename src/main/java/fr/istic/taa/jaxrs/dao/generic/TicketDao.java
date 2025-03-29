package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.User;

import java.util.List;

public class TicketDao extends AbstractJpaDao<Long, Ticket> {

    public TicketDao() {
        super();
        setClazz(Ticket.class);

    }
    public List<Ticket> findAllWithConcertAndUser() {
        return entityManager.createQuery("select t from Ticket t join fetch t.concert join fetch t.user", Ticket.class)
                .getResultList();
    }
}
