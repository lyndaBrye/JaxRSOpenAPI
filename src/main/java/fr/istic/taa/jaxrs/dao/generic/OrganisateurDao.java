package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Organisateur;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.NoResultException;

import java.util.List;

public class OrganisateurDao extends AbstractJpaDao<Long, Organisateur> {

    public OrganisateurDao() {
        super();

    }

    public void deleteWithEvents(Long id) {
        Organisateur organisateur = findOne(id);
        if (organisateur != null) {
            entityManager.createQuery("DELETE FROM Concert e WHERE e.organisateur.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            delete(organisateur);
        }
    }

}
