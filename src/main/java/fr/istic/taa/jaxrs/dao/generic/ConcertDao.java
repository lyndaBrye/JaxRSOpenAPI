package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Concert;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

    public class ConcertDao extends AbstractJpaDao<Long, Concert> {
        public ConcertDao() {
            super();
            setClazz(Concert.class);
        }
    }


