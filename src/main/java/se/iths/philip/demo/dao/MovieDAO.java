package se.iths.philip.demo.dao;

import org.bson.Document;

import java.util.List;

public interface MovieDAO {
    void insert(String title, int year);

    List<Document> findAll();

    Document findByTitle(String title);

    void update(String oldTitle, String newTitle); //Här behövdes två Strings för att kunna uppdatera.

    void delete(String title);
}
