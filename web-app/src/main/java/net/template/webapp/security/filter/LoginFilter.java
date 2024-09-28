package net.template.webapp.security.filter;


import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

public class LoginFilter extends GenericFilterBean {

    private final String appContextPath;

    public LoginFilter(String appContextPath) {
        this.appContextPath = appContextPath;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && ((HttpServletRequest) servletRequest).getRequestURI().equals(appContextPath+"/public/login")) {

            ((HttpServletResponse) servletResponse).sendRedirect(appContextPath);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
