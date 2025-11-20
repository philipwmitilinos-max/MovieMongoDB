package se.iths.philip.demo.dao;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import se.iths.philip.demo.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesMovieDAO implements MovieDAO<Movie> {
    private final MongoCollection<Document> collection;

    public MoviesMovieDAO(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    @Override
    public void insert(String title, int year) {

    }

    @Override
    public List findAll() {
        List<Document> movies = collection.find().into(new ArrayList<>());
        return movies.stream()
                .map(document -> Movie.fromDocument(document))
                .toList();
    }

    @Override
    public Movie findByTitle(String title) {
        return null;
    }

    @Override
    public void update(String oldTitle, String newTitle) {

    }

    @Override
    public void delete(String title) {

    }
}
