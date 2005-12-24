package net.addictivesoftware.framed.services;

import java.util.ArrayList;

import net.addictivesoftware.framed.security.User;

public class UsersImpl implements Users {
	private ArrayList<User> Users;

	public UsersImpl() {
		Users = new ArrayList<User>();
		Users.add(new User(1,"Gertjan Assies", "gertjan@assies.info", "test", true));
		Users.add(new User(2,"Tester", "tester@assies.info", "test", false));
	}
	
	/* (non-Javadoc)
	 * @see net.addictivesoftware.framed.services.IUsers#getUsers()
	 */
	public ArrayList<User> getUsers() {
		return Users;
	}

	/* (non-Javadoc)
	 * @see net.addictivesoftware.framed.services.IUsers#setUsers(java.util.ArrayList)
	 */
	public void setUsers(ArrayList<User> users) {
		Users = users;
	}
	
	/* (non-Javadoc)
	 * @see net.addictivesoftware.framed.services.IUsers#getUser(java.lang.String)
	 */
	public User getUser(String email) {
		for (int i = 0; i < Users.size(); i++) {
			User user = Users.get(i);
			if (email.equals(user.getEmail())) {
				return user;
			}
		}
		return null;
	}
}
