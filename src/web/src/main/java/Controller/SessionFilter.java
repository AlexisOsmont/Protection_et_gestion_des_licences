package Controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        
        if (session != null && !session.isNew()) {
        	// allow request to reach destination
        	chain.doFilter(request, response);
        } else {
        	// redirect request to the same page but this allow the browser to update the cookie  
        	session = httpRequest.getSession(true);

//    	    StringBuffer requestURL = httpRequest.getRequestURL();
//        	String queryString = httpRequest.getQueryString();
//        	String url = null;
//        	
//    	    if (queryString == null) {
//    	    	url = requestURL.toString();
//    	    } else {
//        	    url = requestURL.append('?').append(queryString).toString();
//    	    }
        	
        	((HttpServletResponse)response).sendRedirect(httpRequest.getContextPath() + "/home");
        }
        
    }
    
    
 
}
