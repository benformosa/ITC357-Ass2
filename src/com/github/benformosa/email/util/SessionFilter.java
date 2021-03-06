package com.github.benformosa.email.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionFilter implements Filter {
  private String contextPath;

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain fc) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    String username = (String) req.getSession().getAttribute("username");

    boolean authenticated = true;

    if (username == null) {
      authenticated = false;
    } else if (username.isEmpty()) {
      authenticated = false;
    }

    // redirect to login.jsp if username is not set in the session
    if (authenticated) {
    } else {
      res.sendRedirect(contextPath + "/login?error=login");
      return;
    }
    fc.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig fc) throws ServletException {
    contextPath = fc.getServletContext().getContextPath();
  }
}
