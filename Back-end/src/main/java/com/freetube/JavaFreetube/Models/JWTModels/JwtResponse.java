package com.freetube.JavaFreetube.Models.JWTModels;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String id_user;

	public JwtResponse(String jwttoken, String id_user) {
		this.jwttoken = jwttoken;
		this.id_user = id_user;
	}

	public String getToken() {
		return this.jwttoken;
	}
	public String getId_user() {
		return this.id_user;
	}

}