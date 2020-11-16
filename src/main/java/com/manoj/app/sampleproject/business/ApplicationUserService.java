package com.manoj.app.sampleproject.business;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manoj.app.sampleproject.model.ApplicationUser;
import com.manoj.app.sampleproject.repository.ApplicationUserRepository;
import com.manoj.app.sampleproject.web.request.UserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationUserService {
	
	private final ApplicationUserRepository repository;
	private final PasswordEncoder encoder;
	
	@Transactional
	public void create(UserRequest request) {
		ApplicationUser user=ApplicationUser.builder()
				.username(request.getUsername())
				.password(encoder.encode(request.getPassword()))
				.roles(Arrays.asList("USER"))
				.emailId(request.getEmailId())
				.enabled(true)
				.build();		
		repository.save(user);
	}
	
	
	@Transactional(readOnly=true)
	public ApplicationUser findByUser(String username) {
		List<ApplicationUser> user= repository.findByUsername(username);
		return user.size()>0?user.get(0):null;			
	}

}
