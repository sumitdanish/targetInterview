package com.sumit.authService;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;


public class CustomUserApprovalHandler extends ApprovalStoreUserApprovalHandler {
	boolean useApprovalStore = true;

	@Autowired
	private ClientDetailsService clientDetailsService;

	
	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
		super.setClientDetailsService(this.clientDetailsService);
		
	}

	public void setUseApprovalStore(boolean useApprovalStore) {
		this.useApprovalStore = useApprovalStore;
	}

	@Override
	public AuthorizationRequest checkForPreApproval(AuthorizationRequest authorizationRequest,
			Authentication userAuthentication) {

		boolean approved = false;
		if (useApprovalStore) {
			authorizationRequest = super.checkForPreApproval(authorizationRequest, userAuthentication);
			approved = authorizationRequest.isApproved();
		} else {
			if (clientDetailsService != null) {
				Collection<String> requestedScopes = authorizationRequest.getScope();
				try {
					ClientDetails client = clientDetailsService
							.loadClientByClientId(authorizationRequest.getClientId());
					for (String scope : requestedScopes) {
						if (client.isAutoApprove(scope)) {
							approved = true;
							break;
						}
					}
				} catch (ClientRegistrationException e) {
				}
			}
		}
		authorizationRequest.setApproved(approved);

		return authorizationRequest;

	}
}
