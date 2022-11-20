package com.example.stockapplication.security;

import com.example.stockapplication.Constants.Constants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class JwtTokenFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    private final DomainUserDetailsService userDetailsService;

    public JwtTokenFilter(TokenProvider tokenProvider, DomainUserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String contextPath = request.getServletPath();
        if (doesAPI(contextPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getAccessToken(request);
        if (StringUtils.hasText(token) && tokenProvider.validateAccessToken(token)) {
            setAuthenticationContext(token, request);
            filterChain.doFilter(request, response);
        }
    }


    private String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.HEADER_BEARER))
            return bearerToken.substring(7);
        return null;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        DomainUserDetails userDetails = (DomainUserDetails) userDetailsService.loadUserByUsername(tokenProvider.getSubject(token));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public boolean doesAPI(String contextPath) {
        return Arrays.stream(WebSecurity.PUBLIC_ENDPOINTS).collect(Collectors.toList()).contains(contextPath);
    }
}
