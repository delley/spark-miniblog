package br.com.froli.miniblog.controller;

import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import br.com.froli.miniblog.dao.PostDAO;
//import br.com.froli.miniblog.dao.SessionDAO;
import spark.ModelAndView;
//import spark.Session;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

public class BlogController {

	public static final String BLOG_TEMPLATE = "blog_template.ftl";
	public static final String ENTRY_TEMPLATE = "entry_template.ftl";
	public static final String POST_TEMPLATE = "post_template.ftl";
	public static final String WELCOME_TEMPLATE = "welcome.ftl";

	public static void initRoutes() {
		
		TemplateEngine engine = new FreeMarkerEngine();
		//SessionDAO sessionDAO = new SessionDAO();
		PostDAO postDAO = new PostDAO();
		SessionController sessionController = new SessionController();

		get("/", (request, response) -> {
			//SimpleHash root = new SimpleHash();
			Map<String, Object> root = new HashMap<>();
			List<Document> posts = postDAO.findByDateDescending(10);
			
			root.put("myposts", posts);
			//Session session = request.session();
			//String username = sessionDAO.getUsernameBySessionId(session.id());
			String username = sessionController.getUser(request);
			if (username != null) {
				root.put("username", username);
			}
			return new ModelAndView(root, BLOG_TEMPLATE);
		}, engine);

		get("/welcome", (request, response) -> {
			String username = sessionController.getUser(request);
			if (username == null) {
				response.redirect("/users");
				return null;
			} else {
				//SimpleHash root = new SimpleHash();
				Map<String, Object> root = new HashMap<>();
				root.put("username", username);
				return new ModelAndView(root, WELCOME_TEMPLATE);
			}
		}, engine);

		get("/posts/:permalink", (request, response) -> {
			String permalink = request.params(":permalink");
			Document post = postDAO.findByPermalink(permalink);
			if (post == null) {
				halt(404, "Post não encontrado");
				return null;
			} else {
				//SimpleHash root = new SimpleHash();
				Map<String, Object> root = new HashMap<>();
				root.put("post", post);
				return new ModelAndView(root, ENTRY_TEMPLATE);
			}
		}, engine);

		get("/posts", (request, response) -> {
			String username = sessionController.getUser(request);
			if (username == null) {
				response.redirect("/login");
				return null;
			} else {
				//SimpleHash root = new SimpleHash();
				Map<String, Object> root = new HashMap<>();
				root.put("username", username);
				return new ModelAndView(root, POST_TEMPLATE);
			}
		}, engine);
		
		post("/posts", (request, response) -> {
			String title = request.queryParams("subject");
			String post = request.queryParams("body");
			String tags = request.queryParams("tags");
			String username = sessionController.getUser(request);

			if (username == null) {
				response.redirect("/login");
				return null;
			} else {
				if (title.equals("") || post.equals("")) {
					HashMap<String, String> root = new HashMap<String, String>();
					root.put("errors", "post must contain a title and blog entry.");
					root.put("subject", title);
					root.put("username", username);
					root.put("tags", tags);
					root.put("body", post);
					return new ModelAndView(root, POST_TEMPLATE);
				} else {
					ArrayList<String> tagsArray = extractTags(tags);
					post = post.replaceAll("\\r?\\n", "<p>");
					String permalink = postDAO.addPost(title, post, tagsArray, username);
					response.redirect("/posts/" + permalink);
					return null;
				}
			}
		}, engine);
	}

	private static ArrayList<String> extractTags(String tags) {
		tags = tags.replaceAll("\\s", "");
		String tagArray[] = tags.split(",");
		ArrayList<String> cleaned = new ArrayList<String>();

		for (String tag : tagArray) {
			if (!tag.equals("") && !cleaned.contains(tag)) {
				cleaned.add(tag);
			}
		}
		return cleaned;
	}
}
