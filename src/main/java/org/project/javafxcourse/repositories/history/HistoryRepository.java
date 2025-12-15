package org.project.javafxcourse.repositories.history;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.project.javafxcourse.interfaces.HistoryManager;
import org.project.javafxcourse.models.entities.History;

import java.util.List;

public class HistoryRepository implements HistoryManager {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sqlitePU");

    public void save(String title, String showType) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            // Chercher l'entrée existante
            History history = em.createQuery(
                            "SELECT h FROM History h WHERE h.title = :title AND h.showType = :showType",
                            History.class
                    )
                    .setParameter("title", title)
                    .setParameter("showType", showType)
                    .getResultStream()
                    .findFirst()
                    .orElse(new History(title, showType));

            // Mettre à jour la date (même si c'est une nouvelle entrée)
            history.updateConsultedAt();

            // merge gère automatiquement insert ou update
            em.merge(history);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<History> getAll() {
        EntityManager em = emf.createEntityManager();
        List<History> list = em.createQuery("SELECT h FROM History h ORDER BY h.consultedAt DESC", History.class)
                .getResultList();
        em.close();
        return list;
    }

    public void deleteAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.createQuery("DELETE FROM History").executeUpdate();

        em.getTransaction().commit();
        em.close();
    }
}