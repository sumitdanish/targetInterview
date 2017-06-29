package com.sumit.authService;

import java.util.ArrayList;
import java.util.Collection;

import com.sumit.entity.Authorities;
import com.sumit.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.sumit.authenticationService.AuthneicationService;

@Component
public class CustomAuthenticationManager implements AuthenticationManager{

	
	@Autowired
	AuthneicationService authenticationService;
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String clientName = authentication.getName();
		String password = authentication.getCredentials().toString();
		System.out.println("client Nmae : "+clientName+" : "+password);
		try{
			Users oauthClient = authenticationService.findUserDetails(clientName);
			if (oauthClient == null || !oauthClient.getUsername().equalsIgnoreCase(clientName)) {
				throw new BadCredentialsException("Client not found.");
			}
			if (!password.equals(oauthClient.getPassword())) {
				throw new BadCredentialsException("Wrong Client Secret Key.");
			}
			Collection<? extends GrantedAuthority> authorities = getAuthorities(oauthClient);
			return new UsernamePasswordAuthenticationToken(oauthClient.getUsername(), password, authorities);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Users oauthClient) {

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		if (oauthClient != null) {
			for (Authorities role : oauthClient.getAuthoritieses()) {
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getId().getAuthority());
				authorities.add(authority);
			}
		}
		return authorities;
	}
	

}
