package br.com.froli.miniblog;

import static spark.Spark.*;

import br.com.froli.miniblog.controller.BlogController;
import br.com.froli.miniblog.controller.SessionController;
import br.com.froli.miniblog.controller.UserController;

public class Main {
	
	public static void main(String[] args) {
		// O Spark vai rodar na porta 9090
		port(9090);

		// Inicializa as rotas
		SessionController.initRoutes();
		BlogController.initRoutes();
		UserController.initRoutes();
	}
	
}
