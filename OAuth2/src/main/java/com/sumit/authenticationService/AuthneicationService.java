package com.sumit.authenticationService;

import com.sumit.entity.OauthClientDetails;
import com.sumit.entity.Users;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

public interface AuthneicationService {
	public OauthClientDetails findClientDetails(String clientId) throws ClientRegistrationException;
	public Users findUserDetails(String userid) throws UsernameNotFoundException;
}
