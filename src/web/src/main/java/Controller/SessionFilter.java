package Controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Utils.UserSession;

public class SessionFilter implements Filter {
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
 
    @Override
    public void destroy() {
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    	
    	// this class is here just to unsure that user have a session
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        UserSession appSession = session == null ? null : (UserSession) session.getAttribute("user");
        
        String url = httpRequest.getRequestURL().toString();
        
        boolean isLoggedIn = (session != null && appSession != null);
        boolean isAllowed = appSession == null ? false : appSession.isAllowedFor(url);
        boolean isAuthRequired = UserSession.isLoginRequired(url);
        
        if ((isLoggedIn && (isAllowed || !isAuthRequired)) || (!isLoggedIn && !isAuthRequired)) {
        	// user is logged and is allowed to reach destination
        	chain.doFilter(request, response);
        	
        } else if (isLoggedIn && !isAllowed) {
        	// redirect user to home but doesn't destroy his session 
        	((HttpServletResponse)response).sendRedirect(httpRequest.getContextPath() + "/home");
        	
        } else {
        	// either user isn't allowed to go to this page, redirect him to the home page
        	session = httpRequest.getSession(true);
        	((HttpServletResponse)response).sendRedirect(httpRequest.getContextPath() + "/home");
        }
        
    }
}
