package net.addictivesoftware.framed.security;

import java.io.Serializable;

public class Role  implements Serializable {
	private static final long serialVersionUID = -2271766234141336917L;
	private String name;
	
	public Role(String _roles) {
		this.name = _roles;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
