package net.addictivesoftware.framed.services;

import net.addictivesoftware.framed.security.User;

public class SecurityImpl implements Security {

	public boolean isValid(User _user) {
		// extra checks here please
		if (null != _user) {
			return true;
		} else {
			return false;			
		}
	}
	
	public String getRoles(User _user) {
		return "";
	}

}
