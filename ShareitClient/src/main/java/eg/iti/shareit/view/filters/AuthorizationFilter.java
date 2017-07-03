/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.view.filters;

/**
 *
 * @author Dina Ashraf
 */
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {

    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession userSession = req.getSession(false);

            // supposdly add home page and viewItems page about us 
            //all pages that doesnt need login
            // or make filter on specfic pages instead easier
            String reqURI = req.getRequestURI();
            if (reqURI.contains("items.xhtml") || reqURI.contains("About_us.xhtml")
                    || reqURI.contains("Contact_us.xhtml") || reqURI.contains("fag.xhtml")
                    || reqURI.contains("advancedSearch.xhtml") || reqURI.contains("itemDetails.xhtml")) {
                chain.doFilter(request, response);
            } else if (reqURI.endsWith("shareit")
                    || reqURI.endsWith("shareit//")) {
                resp.sendRedirect(req.getContextPath() + "/faces/pages/items.xhtml");
            } else if (reqURI.contains("/register.xhtml") && userSession != null && userSession.getAttribute("userDto") != null) {
                resp.sendRedirect(req.getContextPath() + "/faces/pages/items.xhtml");
            } else if (userSession != null && userSession.getAttribute("userDto") != null) {
                chain.doFilter(request, response);
            } else if (!reqURI.contains("/register.xhtml") && (userSession == null || userSession.getAttribute("userDto") == null)) {
                resp.sendRedirect(req.getContextPath() + "/faces/pages/register.xhtml");
            } else {
                chain.doFilter(request, response);
            }
        } catch (IOException | ServletException e) {
        }
    }

    @Override
    public void destroy() {

    }
}
