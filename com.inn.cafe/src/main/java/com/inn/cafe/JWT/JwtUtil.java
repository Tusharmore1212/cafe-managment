package com.inn.cafe.JWT;

//import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	private String secret= "btechdays";
	
	public String extractUserName(String token) {
		return extractClaims(token,Claims::getSubject);
				
	}
	
	public Date extractExpiration(String token) {  
	    return extractClaims(token, Claims::getExpiration);
	}

	

	
	
	public <T> T extractClaims(String token, Function<Claims,T> claimResolver) {
		final Claims  claims = extractAllClaims(token);
		return claimResolver.apply(claims);
				
	}
	public  Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();			
	}
	
	
	
	
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date(0));		
	
	}
	
	public String generateToken(String username,String role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role",role);
		return createToken(claims, username);
	}
	
	
 	private String createToken(Map<String, Object>claims,String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10)).signWith(SignatureAlgorithm.HS256,secret).compact();
	}
	
	
	
	public boolean validateToken(String token,UserDetails userDetails) {
		final String username = extractUserName(token);
		return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
	}






}