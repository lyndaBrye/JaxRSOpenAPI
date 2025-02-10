package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.domain.Concert;

public class ConcertDao extends  AbstractJpaDao<Long, Concert>{
    public ConcertDao() {
        super();
        setClazz(Concert.class);
    }
}
