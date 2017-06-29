package com.sumit.authService;

import java.util.HashSet;

import com.sumit.entity.OauthClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sumit.authenticationService.AuthneicationService;

@Component
public class CustomClientDetailsService implements ClientDetailsService {

	@Autowired
	AuthneicationService authenticationService;

	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		OauthClientDetails oauthClientDetails = authenticationService.findClientDetails(clientId);
		boolean flag = Boolean.parseBoolean(oauthClientDetails.getAutoapprove());
		BaseClientDetails basicClientDetails = new BaseClientDetails();
		basicClientDetails.setAccessTokenValiditySeconds(oauthClientDetails.getAccessTokenValidity());
		basicClientDetails.setClientId(clientId);
		basicClientDetails.setRefreshTokenValiditySeconds(oauthClientDetails.getRefreshTokenValidity());
		basicClientDetails.setClientSecret(oauthClientDetails.getClientSecret());
		basicClientDetails.setAuthorities(AuthorityUtils.createAuthorityList(StringUtils
				.commaDelimitedListToStringArray(oauthClientDetails.getAuthorities())));
		basicClientDetails
				.setScope(StringUtils.commaDelimitedListToSet(oauthClientDetails.getScope()));
		basicClientDetails.setAuthorizedGrantTypes(StringUtils
				.commaDelimitedListToSet(oauthClientDetails.getAuthorizedGrantTypes()));
		if (flag) {
			basicClientDetails.setAutoApproveScopes(
					StringUtils.commaDelimitedListToSet(oauthClientDetails.getAutoapprove()));
		} else {
			basicClientDetails.setAutoApproveScopes(new HashSet<String>());
		}
		basicClientDetails.setRegisteredRedirectUri(
				StringUtils.commaDelimitedListToSet(oauthClientDetails.getWebServerRedirectUri()));
		basicClientDetails.setResourceIds(StringUtils.commaDelimitedListToSet(oauthClientDetails.getResourceIds()));
		return basicClientDetails;
	}
}
