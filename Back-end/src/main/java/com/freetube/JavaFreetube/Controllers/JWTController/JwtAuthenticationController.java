package com.freetube.JavaFreetube.Controllers.JWTController;

import java.util.Date;
import java.util.Objects;

import com.freetube.JavaFreetube.Configurations.JwtTokenUtil;

import com.freetube.JavaFreetube.Models.JWTModels.JwtRequest;
import com.freetube.JavaFreetube.Models.JWTModels.JwtResponse;
import com.freetube.JavaFreetube.Models.Usuarios;
import com.freetube.JavaFreetube.Repositories.UsuariosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.freetube.JavaFreetube.Configurations.JwtTokenUtil.JWT_TOKEN_VALIDITY;


@RestController
@CrossOrigin()
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	public UsuariosRepo repo;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		final String roles = String.valueOf(userDetails.getAuthorities());
		
		final int id_usuario = getID(userDetails.getUsername());

		String expires = JWT_TOKEN_VALIDITY / 60 + " minutes";

		String tokenGivenAt = new Date().toString();
		String tokenExpiresAt = jwtTokenUtil.getExpirationDateFromToken(token).toString();
		tokenGivenAt = tokenGivenAt.substring(11, 19);
		tokenExpiresAt = tokenExpiresAt.substring(11, 19);

		return ResponseEntity.ok(new JwtResponse(token, String.valueOf(id_usuario),
				roles, expires, tokenGivenAt, tokenExpiresAt));
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	public int getID(String username)
	{
		Usuarios us =  repo.findAll().stream().filter(x -> x.usuario.contains(username)).findFirst().get();
		return us.id_usuario;
	}
}
