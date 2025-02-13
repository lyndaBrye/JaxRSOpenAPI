package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Artiste;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ArtisteDao extends AbstractJpaDao<Long, Artiste> {
    public ArtisteDao() {
        super();
        setClazz(Artiste.class);
    }


    public List<Artiste> findAllOrderedByNom() {
        TypedQuery<Artiste> query = entityManager.createQuery("SELECT a FROM Artiste a ORDER BY a.nom", Artiste.class);
        return query.getResultList();
    }


    public long count() {
        return entityManager.createQuery("SELECT COUNT(a) FROM Artiste a", Long.class)
                .getSingleResult();
    }
}
