package io.javabrains;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private jwtUtil jwtUtil;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		final String authorizationHeader = request.getHeader("Authorization");
		String username =null;
		String jwt = null;
		System.out.println("authorizationHeader " + authorizationHeader) ;
		
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
			jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
			System.out.println(username) ;
		}
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			if (jwtUtil.ValidateToken(jwt)) {
								System.out.println("Token Validated") ;
				
							
				try {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(jwtUtil.extractUsername(jwt), null, new ArrayList<>());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}
			
			catch (Exception e) {
				System.out.println("error" + e);
			}
			}
			
			else 
			{
					System.out.println("Token Not Validated") ;
				}
			
		} 
		
		filterChain.doFilter(request, response);
		
	}

}	
