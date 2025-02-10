package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Artiste;
import fr.istic.taa.jaxrs.domain.Organisateur;

public class OrganisateurDao extends  AbstractJpaDao<Long, Organisateur>{
    public OrganisateurDao() {
        super();
        setClazz(Organisateur.class);
    }
}
