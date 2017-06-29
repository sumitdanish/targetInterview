package com.sumit.authenticationServiceImpl;

import com.sumit.entity.OauthClientDetails;
import com.sumit.entity.Users;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumit.authenticationService.AuthneicationService;

@Service
public class AuthneicationServiceImpl implements AuthneicationService{

	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	
	@Transactional
	public OauthClientDetails findClientDetails(String clientId) throws ClientRegistrationException {
		// TODO Auto-generated method stub
		OauthClientDetails oauthClientDetails = (OauthClientDetails)sessionFactory.getCurrentSession().get(OauthClientDetails.class, clientId);
		return oauthClientDetails;
	}

	@Transactional
	public Users findUserDetails(String userid) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users user = (Users)sessionFactory.getCurrentSession().get(Users.class, userid);
		return user;
	}

}
