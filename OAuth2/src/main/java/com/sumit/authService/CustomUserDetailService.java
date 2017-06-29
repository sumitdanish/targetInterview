package com.sumit.authService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sumit.entity.Authorities;
import com.sumit.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.sumit.authenticationService.AuthneicationService;

/**
 *
 */
@Component
public class CustomUserDetailService implements UserDetailsService{
	@Autowired
	AuthneicationService authenticationService;
	
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users user = authenticationService.findUserDetails(userid);
		return new UserDetailsRepo(user);
	}

	private final static class UserDetailsRepo extends Users implements UserDetails {
		private static final long serialVersionUID = 1L;
		Users users;

		public UserDetailsRepo(Users users) {
			
			this.users = users;


		}

		public Collection<? extends GrantedAuthority> getAuthorities() {
			return buildUserAuthority(users.getAuthoritieses());
		}

		public boolean isAccountNonExpired() {
			return users.isEnabled();
		}

		public boolean isAccountNonLocked() {
			return users.isEnabled();
		}

		public boolean isCredentialsNonExpired() {
			return users.isEnabled();
		}
		
		@Override
        public boolean isEnabled() {
            return users.isEnabled();
        }
		private List<GrantedAuthority> buildUserAuthority(Set<Authorities> userRoles) {

			Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
			for (Authorities auth : userRoles) {
				setAuths.add(new SimpleGrantedAuthority(auth.getId().getAuthority()));
			}
			List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

			return Result;
		}
	}

}
