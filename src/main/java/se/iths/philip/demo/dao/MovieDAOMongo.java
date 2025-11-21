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

import java.util.ArrayList;
import java.util.List;

public class MovieDAOMongo implements MovieDAO<Document> {

    private final MongoCollection<Document> collection;

    public MovieDAOMongo(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    @Override
    public void insert(String title, int year) {
        // De här kollar om det finns redan ett object i databasen med samma title och namn.
        Document existing = collection.find(
                Filters.and(
                        Filters.eq("title", title),
                        Filters.eq("year", year)
                )
        ).first();

        if (existing != null) {
            System.out.println("Filmen finns redan i databasen: " + existing.toJson());
            return;
        }

        Document doc = new Document()
                .append("title", title)
                .append("year", year);
        //Tog bort det hård kodade doc för kunna använda Main.

        InsertOneResult result = collection.insertOne(doc);

        if (result.wasAcknowledged()) {
            BsonValue insertedId = result.getInsertedId();
            System.out.println("Insatt dokument med ID: " + insertedId);
        } else {
            System.out.println("Insättning misslyckades!");
        }
    }

    @Override
    public void insert(Document movie) {

    }

    @Override
    public List<Document> findAll() {
        List<Document> documents = collection.find().into(new ArrayList<>());
        for (Document doc : documents) {
            System.out.println(doc.toJson());
        }
        return documents;
    }

    @Override
    public Document findByTitle(String title) {
        Bson filter = Filters.eq("title", title);
//        Document document = collection.find(filter).first();
//        return document;
        return collection.find(filter).first();
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
