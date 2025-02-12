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

        public List<Concert> findByLieu(String lieu) {
            TypedQuery<Concert> query = entityManager.createQuery("SELECT c FROM Concert c WHERE c.lieu = :lieu", Concert.class);
            query.setParameter("lieu", lieu);
            return query.getResultList();
        }

        public <LocalDate> List<Concert> findByDate(LocalDate date) {
            TypedQuery<Concert> query = entityManager.createQuery("SELECT c FROM Concert c WHERE c.date = :date", Concert.class);
            query.setParameter("date", date);
            return query.getResultList();
        }

        public List<Concert> findAllOrderedByDate() {
            TypedQuery<Concert> query = entityManager.createQuery("SELECT c FROM Concert c ORDER BY c.date", Concert.class);
            return query.getResultList();
        }
    }


