package filters;

import common.servlets.HttpFilter;
import lombok.extern.log4j.Log4j;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Oleg Grigorjev 
 */
 
@Log4j
@WebFilter(value = {"/*"},
        initParams = {@WebInitParam(name = "characterEncoding", value = "utf-8")})
public class ContentTypeFilter implements HttpFilter {

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("characterEncoding");
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setContentType("text/html;charset=" + encoding);
        log.info("the content type set to " + "text/html;charset=" + encoding);
        request.setCharacterEncoding(encoding);
        log.info("the encoding set to " + encoding);

        chain.doFilter(request, response);
    }
}
