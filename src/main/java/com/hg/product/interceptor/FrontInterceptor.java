package com.hg.product.interceptor;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FrontInterceptor extends  OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException 
	{
			String correlationId = "X-Correlation-Id";
			MDC.put(correlationId, UUID.randomUUID().toString());
		try {
			filterChain.doFilter(request, response);
		} finally {
			MDC.remove(correlationId);
		}
		
	}
	
}
