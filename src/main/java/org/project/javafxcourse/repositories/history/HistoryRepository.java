package org.project.javafxcourse.repositories.history;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.project.javafxcourse.models.entities.History;

import java.util.List;

public class HistoryRepository {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sqlitePU");

    public void addHistory(String title, String showType) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        History history = new History(title, showType);
        em.persist(history);

        em.getTransaction().commit();
        em.close();
    }

    public List<History> getAllHistory() {
        EntityManager em = emf.createEntityManager();
        List<History> list = em.createQuery("SELECT h FROM History h ORDER BY h.createdAt DESC", History.class)
                .getResultList();
        em.close();
        return list;
    }

    public void close() {
        if (emf != null) emf.close();
    }
}