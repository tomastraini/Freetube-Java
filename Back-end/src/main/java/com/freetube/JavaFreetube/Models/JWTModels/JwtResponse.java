package com.freetube.JavaFreetube.Models.JWTModels;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String id_user;
	private final String roles;
	private final String expiresIn;
	private final String tokenGivenAt;
	private final String tokenInvalidAt;

	public JwtResponse(String jwttoken, String id_user, String roles, String expiresIn, String tokenGivenAt, String tokenInvalidAt) {
		this.jwttoken = jwttoken;
		this.id_user = id_user;
		this.roles = roles;
		this.expiresIn = expiresIn;
		this.tokenGivenAt = tokenGivenAt;
		this.tokenInvalidAt = tokenInvalidAt;
	}

	public String getToken() {
		return this.jwttoken;
	}
	public String getId_user() {
		return this.id_user;
	}
	public String getroles() {
		return this.roles;
	}
	public String getexpiresIn() {
		return this.expiresIn;
	}
	public String gettokenGivenAt() {
		return this.tokenGivenAt;
	}
	public String gettokenInvalidAt() {
		return this.tokenInvalidAt;
	}

}