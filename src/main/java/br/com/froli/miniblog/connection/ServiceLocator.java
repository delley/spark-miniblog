package br.com.froli.miniblog.connection;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class ServiceLocator {

	private static final String HOST = "192.168.56.101";
	private static final String PORT = "27017";
	private static final String DB_NAME = "blog";
	private static final String URI = String.format("mongodb://%s:%s", HOST, PORT);

	private static ServiceLocator instance;

	private MongoDatabase database;

	private ServiceLocator() {
	}

	public static ServiceLocator getInstance() {
		if (instance == null) {
			instance = new ServiceLocator();
		}
		return instance;
	}

	public MongoDatabase getDatabase() {
		MongoClient mongoClient = null;
		
		if (database == null) {
			//try (MongoClient mongoClient = new MongoClient(new MongoClientURI(URI))) {
			try {
				mongoClient = new MongoClient(new MongoClientURI(URI));
				database = mongoClient.getDatabase(DB_NAME);
			} catch (Exception e) {
				e.printStackTrace();
			} //finally {
			//	mongoClient.close();
			//}
		}

		return database;
	}

}
