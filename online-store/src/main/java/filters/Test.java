package filters;

import common.servlets.HttpFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 1 on 12.05.2016.
 */

//@WebFilter({"/registration.jsp", "/auth"})
public class Test implements HttpFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.getAttribute("test");
        chain.doFilter(request, response);
    }
}
