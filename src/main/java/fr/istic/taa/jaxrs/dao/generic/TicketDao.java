package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Organisateur;
import fr.istic.taa.jaxrs.domain.Ticket;

public class TicketDao extends AbstractJpaDao<Long, Ticket> {
    public TicketDao() {
        super();
        setClazz(Ticket.class);
    }

}
