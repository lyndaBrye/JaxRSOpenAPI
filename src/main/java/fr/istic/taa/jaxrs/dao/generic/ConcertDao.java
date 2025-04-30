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
        public List<Concert> findByDate(LocalDate date) {
            TypedQuery<Concert> query = entityManager.createQuery("SELECT c FROM Concert c WHERE c.date = :date", Concert.class);
            query.setParameter("date", date);
            return query.getResultList();
        }
        public List<Concert> findByArtisteId(Long artisteId) {
            TypedQuery<Concert> query = entityManager.createQuery("SELECT c FROM Concert c WHERE c.artiste.id = :artisteId", Concert.class);
            query.setParameter("artisteId", artisteId);
            return query.getResultList();
        }

        public List<Concert> findByLieu(String lieu) {
            TypedQuery<Concert> query = entityManager.createQuery("SELECT c FROM Concert c WHERE c.lieu like %:lieu%", Concert.class);
            query.setParameter("lieu", lieu);
            return query.getResultList();
        }

        public List<Concert> findByOrganisateurId(Long organisateurId) {
            TypedQuery<Concert> query = entityManager.createQuery("SELECT c FROM Concert c WHERE c.organisateur.id = :organisateurId", Concert.class);
            query.setParameter("organisateurId", organisateurId);
            return query.getResultList();
        }
    }


