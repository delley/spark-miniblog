package br.com.froli.miniblog.dao;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import br.com.froli.miniblog.connection.ServiceLocator;

public abstract class DAO {

	protected final MongoCollection<Document> collection;
	
	public DAO(String collectionName) {
		this.collection = ServiceLocator
				.getInstance()
				.getDatabase()
				.getCollection(collectionName);
	}
	
	public Document findById(String id) {
		// collection.deleteOne(eq("_id", new ObjectId(sessionID)));
        //return collection.find(new BasicDBObject("_id", new ObjectId(id))).first();
        return collection.find(eq("_id", id)).first();
    }
	
	public boolean create(Document doc) {
        try {
            collection.insertOne(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
