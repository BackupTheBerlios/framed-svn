package net.addictivesoftware.framed.services;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.tapestry.annotations.InjectObject;

import net.addictivesoftware.framed.security.Role;
import net.addictivesoftware.framed.security.User;

public class UsersImpl implements Users {
	private ArrayList<User> users;

	public UsersImpl() {
		users = new ArrayList<User>();
		users.add(new User(1,"Gertjan Assies", "gertjan@assies.info", "test", true, new Role("all|registered")));
		users.add(new User(2,"Tester", "tester@assies.info", "test", false, new Role("all")));
	}
	
	/* (non-Javadoc)
	 * @see net.addictivesoftware.framed.services.IUsers#getUsers()
	 */
	public ArrayList<User> getUsers() {
		return users;
	}

	/* (non-Javadoc)
	 * @see net.addictivesoftware.framed.services.IUsers#setUsers(java.util.ArrayList)
	 */
	public void setUsers(ArrayList<User> _users) {
		users = _users;
	}
	
	/* (non-Javadoc)
	 * @see net.addictivesoftware.framed.services.IUsers#getUser(java.lang.String)
	 */
	public User getUser(String _email) {
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			if (_email.equals(user.getEmail())) {
				return user;
			}
		}
		return null;
	}
}
