package br.com.froli.miniblog.dao;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

public class SessionDAO extends DAO {

	public SessionDAO() {
		super("sessions");
	}

	public void startSession(String username, String sessionID) {
		Document session = new Document();
		session.append("_id", sessionID);
		session.append("username", username);

		collection.insertOne(session);
	}

	public void endSession(String sessionID) {
		collection.deleteOne(eq("_id", sessionID));
		//collection.deleteOne(new BasicDBObject("_id", new ObjectId("560ea3f205240f065a3e9d19")));
	}

	public String getUsernameBySessionId(String sessionID) {
		Document session = super.findById(sessionID);
		if (session == null) {
			return null;
		} else {
			return session.get("username").toString();
		}
	}
}
