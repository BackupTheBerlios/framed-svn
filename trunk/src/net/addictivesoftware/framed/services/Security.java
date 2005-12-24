package net.addictivesoftware.framed.services;

import net.addictivesoftware.framed.security.User;

public interface Security {
	public boolean isValid(User user);
}
