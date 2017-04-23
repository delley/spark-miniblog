package br.com.froli.miniblog.controller;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import br.com.froli.miniblog.dao.UserDAO;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class UserController {

	public static final String USER_TEMPLATE = "user_template.ftl";

	public static void initRoutes() {
		UserDAO userDAO = new UserDAO();

		UserController userController = new UserController();
		// SessionController

		get("/users", (request, response) -> {
			// SimpleHash root = new SimpleHash();
			Map<String, Object> root = new HashMap<>();

			root.put("username", "");
			root.put("password", "");
			root.put("password_error", "");
			root.put("username_error", "");
			root.put("verify_error", "");

			return new ModelAndView(root, USER_TEMPLATE);
		}, new FreeMarkerEngine());

		post("/users", (request, response) -> {

			// SimpleHash root = new SimpleHash();
			Map<String, Object> root = new HashMap<>();

			String username = request.queryParams("username");
			String password = request.queryParams("password");
			String verify = request.queryParams("verify");

			root.put("username", username);
			root.put("password", password);

			if (userController.validaFormUsuario(username, password, verify)) {
				System.out.println("Criando usuario: " + username + " " + password);
				boolean ok = userDAO.addUser(username, password);
				if (!ok) {

					root.put("username_error", "Username já está sendo usado");
					return new ModelAndView(root, USER_TEMPLATE);
				} else {
					//TODO: criar sessioncontroller
					//sessionController.iniciarSessao(username, request);
					response.redirect("/welcome");
					return null;
				}
			} else {
				halt(400, "Form com campos inválidos");
				return new ModelAndView(root, USER_TEMPLATE);
			}

		}, new FreeMarkerEngine());
	}

	private boolean validaFormUsuario(String username, String password, String verify) {
		if (preenchido(username) && preenchido(password) && preenchido(verify)) {
			if (password.equals(verify))
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
	
	public boolean preenchido(String s) {
		return s != null && !s.equals("");
	}
}
