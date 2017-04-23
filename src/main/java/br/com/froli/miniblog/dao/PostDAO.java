package br.com.froli.miniblog.dao;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoException;

public class PostDAO extends DAO {

	public PostDAO() {
		super("posts");
	}

	public Document findByPermalink(String permalink) {
		//BasicDBObject query = new BasicDBObject("permalink", permalink);
		//DBObject post = collection.findOne(query);
		Document post = collection.find(eq("permalink", permalink)).first();
		return post;
	}

	public List<Document> findByDateDescending(int limit) {
		//List<Document> posts = null;

		//DBCursor cursor = collection.find().sort(new BasicDBObject("date", -1)).limit(limit);
		
		//MongoCursor<Document> cursor 
		
		List<Document> posts = collection.find().sort(descending("date")).limit(limit).into(new ArrayList<>());

//		try {
//			posts = cursor.toArray();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			cursor.close();
//		}
		
		return posts;
	}

	public String addPost(String title, String body, List<?> tags, String username) {
		String permalink = title.replaceAll("\\s", "_");
		permalink = permalink.replaceAll("\\W", "");
		permalink = permalink.toLowerCase();

		Document post = new Document();
		post.append("title", title);
		post.append("author", username);
		post.append("body", body);
		post.append("permalink", permalink);
		post.append("tags", tags);
		post.append("date", new Date());

		try {
			collection.insertOne(post);
		} catch (MongoException e) {
			e.printStackTrace();
		}

		return permalink;
	}

}
