package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.domain.Artiste;
public class ArtisteDao extends AbstractJpaDao<Long, Artiste> {
    public ArtisteDao() {
        super();
        setClazz(Artiste.class);
    }
    public Artiste findByNom(String nom) {
        return entityManager.createQuery("SELECT a FROM Artiste a WHERE a.nom = :nom", Artiste.class)
                .setParameter("nom", nom)
                .getSingleResult();
    }

}
