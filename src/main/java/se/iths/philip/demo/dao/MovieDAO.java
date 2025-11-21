package se.iths.philip.demo.dao;

import java.util.List;

public interface MovieDAO<T> {
    void insert(String title, int year);

    // Byter ut String title, int year till T movie.
    void insert(T movie);

    List<T> findAll();

    T findByTitle(String title);

    void update(String oldTitle, String newTitle); //Här behövdes två Strings för att kunna uppdatera.

    void delete(String title);
}
