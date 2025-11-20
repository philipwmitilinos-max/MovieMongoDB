package se.iths.philip.demo.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
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
        Document movie = new Document()
                .append("title", title)
                .append("year", year);

        InsertOneResult result = collection.insertOne(movie);

        if (result.wasAcknowledged()) {
            BsonValue insertedId = result.getInsertedId();
            System.out.println("Insatt dokument med ID: " + insertedId);
        } else {
            System.out.println("Insättning misslyckades!");
        }
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
        Bson filter = Filters.eq("title", title);
        Document document = collection.find(filter).first();
        return Movie.fromDocument(document);
    }

    @Override
    public void update(String oldTitle, String newTitle) {
        Bson filter = Filters.eq("title", oldTitle);
        Bson update = Updates.set("title", newTitle);
        UpdateResult result = collection.updateOne(filter, update);
        if (result.wasAcknowledged()) {
            System.out.println("Antal dokument uppdaterade: " + result.getModifiedCount());
        } else {
            System.out.println("Uppdateringen bekräftades inte av servern.");
        }
    }

    @Override
    public void delete(String title) {
        Bson filter = Filters.eq("title", title);
        DeleteResult result = collection.deleteOne(filter);
        if (result.wasAcknowledged()) {
            System.out.println("Antal dokument raderade: " + result.getDeletedCount());
        } else {
            System.out.println("Raderingen bekräftades inte av servern.");
        }
    }
}
