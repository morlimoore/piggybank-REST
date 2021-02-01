package com.morlimoore.piggybankapi.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthEntryPointFailureHandler implements AuthenticationEntryPoint {

	@Qualifier("handlerExceptionResolver")
	@Autowired
	private HandlerExceptionResolver resolver;

	private final Logger logger = LoggerFactory.getLogger(AuthEntryPointFailureHandler.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) {
		logger.error("Unauthorized error: {}", authException.getMessage());
		resolver.resolveException(request, response, null, authException);
	}
}
