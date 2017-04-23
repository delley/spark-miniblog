package br.com.froli.miniblog.dao;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
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
        return collection.find(new BasicDBObject("_id", new ObjectId("560ea3f205240f065a3e9d19"))).first();
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
