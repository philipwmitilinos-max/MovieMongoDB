package se.iths.philip.demo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import se.iths.philip.demo.dao.MoviesMovieDAO;

public class Main {
    public static void main(String[] args) {
        String uri = "mongodb+srv://philipmitilinos_db_user:Wil91Ph!@cluster0.9jcty3n.mongodb.net/?appName=Cluster0";
        try (MongoClient client = MongoClients.create(uri)) {
            MongoDatabase db = client.getDatabase("moviesdb");
            MongoCollection<Document> collection = db.getCollection("movies");

//            MovieDAO movieDAO = new MovieDAOMongo(collection);

//            movieDAO.insert("Pulp Fiction", 1994);
//            movieDAO.insert("Avatar", 2009);
//            movieDAO.insert("Inception", 2010);
//            movieDAO.insert("The Godfather", 1972);
//            movieDAO.findAll();
//            Document doc = movieDAO.findByTitle("Pulp Fiction");
//            System.out.println("Hittad film: " + doc);
//
//            movieDAO.update("Pulp Fiction", "Pulp Fiction (Edited)");
//            movieDAO.delete("Pulp Fiction (Edited)");

            MoviesMovieDAO dao = new MoviesMovieDAO(collection);
            dao.insert("Spirited Away", 2001);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
