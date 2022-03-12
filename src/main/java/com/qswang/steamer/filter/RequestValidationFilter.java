package com.qswang.steamer.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

public class RequestValidationFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestValidationFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest  = (HttpServletRequest) servletRequest;
        String URI = httpServletRequest.getRequestURI();
        logger.info(URI);
        try {
            validate(URI);
            Enumeration<String> names = httpServletRequest.getParameterNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement().toString();
                String[] values = httpServletRequest.getParameterValues(name);
                for (String v : values) {
                    validate(v);
                }
            }
        } catch (IllegalArgumentException ex){
            logger.error("invalid request:");
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    private void validate(String url){
        String u = url.toLowerCase();
        String regex = "select|update|or|delete|insert|truncate|into|drop|execute|like|from|grant|use|group_concat|column_name|information_schema.columns|table_schema|union|where|order|by|'\\*|\\;|\\-|\\--|\\+|\\,|\\//|\\/|\\%|\\#";
        if(u.matches(regex))
            throw new IllegalArgumentException();
    }
}