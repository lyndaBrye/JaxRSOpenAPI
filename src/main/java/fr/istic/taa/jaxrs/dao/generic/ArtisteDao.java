package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Artiste;

public class ArtisteDao extends AbstractJpaDao<Long, Artiste> {
    public ArtisteDao() {
        super();
        setClazz(Artiste.class);
    }

}
