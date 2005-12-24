package net.addictivesoftware.framed.services;

import net.addictivesoftware.framed.security.User;

public class SecurityImpl implements Security {

	public boolean isValid(User user) {
		if (null != user &&	"gertjan@assies.info".equals(user.getEmail())) {
			return true;
		} else {
			return false;			
		}
	}

}
