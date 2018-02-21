package br.com.froli.miniblog.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import br.com.froli.miniblog.dao.SessionDAO;
import br.com.froli.miniblog.dao.UserDAO;
import spark.ModelAndView;
import spark.Request;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

public class SessionController {
	
	public static final String LOGIN_TEMPLATE = "login.ftl";

	private UserDAO userDAO = new UserDAO();
	private SessionDAO sessionDAO = new SessionDAO();

	public static void initRoutes() {
		SessionController controller = new SessionController();

		get("/login", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("username", "");
			attributes.put("login_error", "");
			return new ModelAndView(attributes, LOGIN_TEMPLATE);
		}, new FreeMarkerEngine());

		post("/login", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();

			String username = request.queryParams("username");
			String password = request.queryParams("password");

			if (controller.autenticar(username, password)) {
				controller.iniciarSessao(username, request);
				response.redirect("/");
			} else {
				attributes.put("username", username);
				attributes.put("password", "");
				attributes.put("login_error", "Login inválido");
			}

			return new ModelAndView(attributes, LOGIN_TEMPLATE);
		}, new FreeMarkerEngine());

		get("/logout", (request, response) -> {
			Session session = request.session();
			if (session != null) {
				controller.sessionDAO.endSession(session.id());
				session.invalidate();
			}
			response.redirect("/login");
			return null;
		});

	}

	public String getUser(Request request) {
		Session session = request.session();
		if (session == null) {
			return null;
		} else {
			String username = sessionDAO.getUsernameBySessionId(session.id());
			return username;
		}
	}

	public boolean autenticar(String username, String password) {
		Document user = userDAO.authenticate(username, password);
		return user != null;
	}

	public void iniciarSessao(String username, Request request) {
		Session session = request.session(true);
		String sessionId = session.id();
		sessionDAO.startSession(username, sessionId);
	}
}
