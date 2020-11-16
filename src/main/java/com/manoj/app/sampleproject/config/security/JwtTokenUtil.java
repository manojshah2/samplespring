package com.manoj.app.sampleproject.config.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.manoj.app.sampleproject.web.response.JwtResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 24;
	
	private String secret="mysecret";
	
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token,Claims::getSubject);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAuthorities(String token){		
		return (List<String>)getAllClaimsFromToken(token).get("authorities");
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token,Claims::getExpiration);
	}
	
	public <T>T getClaimFromToken(String token,Function<Claims,T> claimsResolver){
		final Claims claims=getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		final Date expiration=getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public String generateToken(JwtResponse response) {
		Map<String, Object> claims = new HashMap<>();		
		final String token=doGenerateToken(claims, response.getUsername());		
		return token;
	}
	
	public JwtResponse getJwtResponse(String username) {
		JwtResponse response=new JwtResponse();
		response.setUsername(username);
		final String token=generateToken(response);
		response.setJwtToken(token);
		return response;
	}
	
	private String doGenerateToken(Map<String, Object> claims, String subject) {		
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))

				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))

				.signWith(SignatureAlgorithm.HS512, secret).compact();
		
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);			
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));		
	}
	
}
