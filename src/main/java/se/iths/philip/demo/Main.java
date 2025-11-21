package se.iths.philip.demo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import se.iths.philip.demo.dao.MovieDAO;
import se.iths.philip.demo.dao.MovieDAOMongo;
import se.iths.philip.demo.dao.MoviesMovieDAO;
import se.iths.philip.demo.model.Genre;
import se.iths.philip.demo.model.Movie;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String uri = "mongodb+srv://philipmitilinos_db_user:Wil91Ph!@cluster0.9jcty3n.mongodb.net/?appName=Cluster0";
        try (MongoClient client = MongoClients.create(uri)) {
            MongoDatabase db = client.getDatabase("moviesdb");
            MongoCollection<Document> collection = db.getCollection("movies");

            MovieDAO movieDAO = new MovieDAOMongo(collection);
            movieDAO.insert("Pulp Fiction", 1994);
            movieDAO.insert("Avatar", 2009);
            movieDAO.insert("Inception", 2010);
            movieDAO.insert("The Godfather", 1972);
//            movieDAO.findAll();
//            Document doc = movieDAO.findByTitle("Pulp Fiction");
//            System.out.println("Hittad film: " + doc);
            movieDAO.update("Pulp Fiction", "Pulp Fiction (Edited)");
            movieDAO.delete("Pulp Fiction (Edited)");
            movieDAO.delete("Avatar");
            movieDAO.delete("Inception");
            movieDAO.delete("The Godfather");
            MovieDAO<Movie> dao = new MoviesMovieDAO(collection);
            List<Genre> genres = List.of(
                    new Genre("Animation", "Animerad film"),
                    new Genre("Fantasy", "Magiska världar & äventyr")
            );
            Movie movie = new Movie(
                    "Spirited Away",
                    2001,
                    genres
            );
            dao.insert(movie);
            System.out.println("Klart! Film inlagd i databasen.");
            List<Genre> genres1 = List.of(
                    new Genre("Komedi", "Rolig film"),
                    new Genre("Action", "Späning & hög intensiv utmaning")
            );
            Movie movie1 = new Movie(
                    "Pulp Fiction",
                    1994,
                    genres1
            );
            dao.insert(movie1);
            System.out.println("Klart! Film inlagd i databasen.");
            List<Genre> genres2 = List.of(
                    new Genre("Science fiction", "Vetenskaps fantasy"),
                    new Genre("Fantasy", "Magiska världar & äventyr")
            );
            Movie movie2 = new Movie(
                    "Avatar",
                    2009,
                    genres2
            );
            dao.insert(movie2);
            System.out.println("Klart! Film inlagd i databasen.");
            List<Genre> genres3 = List.of(
                    new Genre("Science fiction", "Vetenskaps fantasy"),
                    new Genre("Action", "Späning & hög intensiv utmaning")
            );
            Movie movie3 = new Movie(
                    "Inception",
                    2010,
                    genres3
            );
            dao.insert(movie3);
            System.out.println("Klart! Film inlagd i databasen.");
            List<Genre> genres4 = List.of(
                    new Genre("Mafia", "Organised kriminell familj"),
                    new Genre("Classic", "Traditional stil")
            );
            Movie movie4 = new Movie("The Godfather",
                    1972,
                    genres4
            );
            dao.insert(movie4);
            System.out.println("Klart! Film inlagd i databasen.");

            List<Movie> movies = dao.findAll();
            System.out.println("----- ALLA FILMER I DATABASEN -----");
            movies.forEach(System.out::println);
            System.out.println("-----------------------------------");
            System.out.println();
            System.out.println("Sorterat efter title:");
            ((MoviesMovieDAO) dao).sortByTitle().forEach(System.out::println);
            System.out.println();
            System.out.println("Sorterat efter år:");
            ((MoviesMovieDAO) dao).sortByYear().forEach(System.out::println);

            dao.update("Spirited Away", "Spirited Away (Edited)");

            dao.delete("Spirited Away (Edited)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
