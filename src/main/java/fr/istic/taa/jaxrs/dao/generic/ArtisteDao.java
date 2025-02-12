package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Artiste;
import jakarta.persistence.TypedQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

public class ArtisteDao extends AbstractJpaDao<Long, Artiste> {
    public ArtisteDao() {
        super();
        setClazz(Artiste.class);
    }

    @Operation(summary = "Trouver des artistes par nom", description = "Retourne une liste d'artistes ayant un nom spécifique")
    public List<Artiste> findByNom(String nom) {
        TypedQuery<Artiste> query = entityManager.createQuery("SELECT a FROM Artiste a WHERE a.nom = :nom", Artiste.class);
        query.setParameter("nom", nom);
        return query.getResultList();
    }

    @Operation(summary = "Lister tous les artistes triés par nom", description = "Retourne tous les artistes triés par ordre alphabétique")
    public List<Artiste> findAllOrderedByNom() {
        TypedQuery<Artiste> query = entityManager.createQuery("SELECT a FROM Artiste a ORDER BY a.nom", Artiste.class);
        return query.getResultList();
    }

    @Operation(summary = "Compter le nombre d'artistes", description = "Retourne le nombre total d'artistes enregistrés")

    public long count() {
        return entityManager.createQuery("SELECT COUNT(a) FROM Artiste a", Long.class)
                .getSingleResult();
    }
}
