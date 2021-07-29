package org.generated.project.application;


import org.seedstack.seed.Bind;

@Bind
public class LoginCredential{

	
	private String id;
	
	private String password;
	
	

	public LoginCredential() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginCredential(String id) {
		super();
		this.id = id;
	}

	public LoginCredential(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
