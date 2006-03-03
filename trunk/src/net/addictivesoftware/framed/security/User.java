package net.addictivesoftware.framed.security;

import java.io.Serializable;
import java.sql.Timestamp;

public class User implements Serializable {
	private static final long serialVersionUID = 3787420128870522366L;
	
	private Integer id;
	private String name;
	private String email;
	private String password;
	private Boolean admin;
	private Timestamp lastAccess;
	private Role roles;
	
	public User(Integer id, String name, String email, String password, Boolean admin, Role roles) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.admin = admin;
		this.roles = roles; 
	}
	public Boolean isAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Timestamp getLastAccess() {
		return lastAccess;
	}
	public void setLastAccess(Timestamp lastAccess) {
		this.lastAccess = lastAccess;
	}
	public Role getRoles() {
		return roles;
	}
	public void setRoles(Role roles) {
		this.roles = roles;
	}
	
}
