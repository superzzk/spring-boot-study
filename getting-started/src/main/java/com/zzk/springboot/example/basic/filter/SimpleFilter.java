package com.zzk.springboot.example.basic.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SimpleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Remote Host:" + servletRequest.getRemoteHost());
        System.out.println("Remote Address:" + servletRequest.getRemoteAddr());
        // add response header
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        response.setHeader("some_header","value");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
