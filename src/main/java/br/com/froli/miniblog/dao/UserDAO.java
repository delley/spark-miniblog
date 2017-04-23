package br.com.froli.miniblog.dao;

import org.bson.Document;

public class UserDAO extends DAO {

	public UserDAO() {
		super("users");
	}

	public boolean addUser(String username, String password) {
        Document user = new Document();
        user.append("_id", username).append("password", password);
        return super.create(user);
    }
 
    public Document authenticate(String username, String password) {
        Document user = super.findById(username);
 
        if (user == null) {
            System.out.println("Usuario não existe");
            return null;
        }
 
        if (!password.equals(user.get("password").toString())) {
            System.out.println("Senha inválida");
            return null;
        }
 
        return user;
    }
}
