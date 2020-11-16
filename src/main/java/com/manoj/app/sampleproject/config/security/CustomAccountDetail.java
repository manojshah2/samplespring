package com.manoj.app.sampleproject.config.security;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.manoj.app.sampleproject.model.ApplicationUser;

import lombok.Getter;
import lombok.Setter;

public class CustomAccountDetail implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ApplicationUser user;
	
	@Getter @Setter
    private Map<String, Object> attributes;
	
	CustomAccountDetail(ApplicationUser user) {
        this.user = user;
    }
	
	public static CustomAccountDetail create(ApplicationUser user) {
		return new CustomAccountDetail(user);
	}
	
	public static CustomAccountDetail create(ApplicationUser user, Map<String, Object> attributes) {
        CustomAccountDetail userPrincipal = CustomAccountDetail.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<String> roles = user.getRoles();
        return roles.stream().map(x -> new SimpleGrantedAuthority(x)).collect(Collectors.toList());
	}
	
	public Collection<String> getRoles() {
        return user.getRoles();
    }

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public ApplicationUser getApplicationUser() {
		return user;
	}

}
