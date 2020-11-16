package com.manoj.app.sampleproject.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.manoj.app.sampleproject.config.security.CustomAccountDetailsService;
import com.manoj.app.sampleproject.config.security.JwtTokenUtil;
import com.manoj.app.sampleproject.web.request.JwtRequest;
import com.manoj.app.sampleproject.web.response.JwtResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtAuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final CustomAccountDetailsService userService;
	
	@PostMapping(value="/authenticate")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public JwtResponse createAuthenticationToken(@RequestBody @Valid final JwtRequest authenticationRequest,BindingResult results) throws Exception {
		
		log.info("Authentication Started");
		Authentication auth=authenticate(authenticationRequest.getUsername(),authenticationRequest.getPassword());
		
		if(auth==null)
			throw new RequestRejectedException("Invalid Credentails");
		
		UserDetails user=userService.loadUserByUsername(authenticationRequest.getUsername());		
		return jwtTokenUtil.getJwtResponse(user.getUsername());
	}
	
	private Authentication authenticate(String username,String password) throws Exception {
		Authentication auth=null;
		try {
			auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}catch(DisabledException e) {
			throw new Exception("USER_DISABLED",e);
		}catch(BadCredentialsException  e) {
			throw new Exception("INVALID_CREDENTIALS",e);
		}catch(Exception ee) {
			ee.printStackTrace();
		}
		return auth;
	}
}
