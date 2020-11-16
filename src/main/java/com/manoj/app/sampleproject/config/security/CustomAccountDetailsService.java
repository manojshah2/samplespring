package com.manoj.app.sampleproject.config.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manoj.app.sampleproject.model.ApplicationUser;
import com.manoj.app.sampleproject.repository.ApplicationUserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "userDetailsService")
@Primary
public class CustomAccountDetailsService implements UserDetailsService  {

	@Autowired
    private ApplicationUserRepository accountRepository;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		List<ApplicationUser> user = this.accountRepository.findByUsername(username);
		
		if(user.size()!=1 || user==null)
			throw new UsernameNotFoundException("User not Found");
        
        return CustomAccountDetail.create(user.get(0));
	}

}
