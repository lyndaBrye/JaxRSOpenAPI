package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Organisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.*;

import java.util.List;

public class OrganisateurDao extends AbstractJpaDao<Long, Organisateur> {

    public OrganisateurDao() {
        super();

    }

}
