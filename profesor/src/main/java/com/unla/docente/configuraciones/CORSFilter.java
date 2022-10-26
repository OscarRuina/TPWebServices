package com.unla.docente.configuraciones;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

    static Logger logger = LoggerFactory.getLogger(CORSFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        System.out.println("CORS Filter HTTP Request: " + request.getMethod());

        //disable chain
        response.addHeader("Access-Control-Max-Age", "-1");
        //Autorize (allow) all domains to consume the content
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
        response.setHeader("Access-Control-Allow-Headers",
                "Access-Control-Allow-Headers, Origin, Accept, X-Request-With, Content-Type, "
                        + "Authorization, Access-Control-Request-Method");

        //For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
        logger.info(request.getRemoteAddr());
        System.out.println("CORS Filter HTTP Request: " + request.getMethod());
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

