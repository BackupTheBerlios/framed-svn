package net.addictivesoftware.framed.services;

import java.util.ArrayList;

import net.addictivesoftware.framed.security.User;

public interface Users {

	public abstract ArrayList<User> getUsers();

	public abstract void setUsers(ArrayList<User> users);

	public abstract User getUser(String email);

}