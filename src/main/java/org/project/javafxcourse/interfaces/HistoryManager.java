package org.project.javafxcourse.interfaces;

import org.project.javafxcourse.models.entities.History;

import java.util.List;

public interface HistoryManager {
    public void save(String title, String showType);
    public List<History> getAll();

    public void deleteAll();
}
