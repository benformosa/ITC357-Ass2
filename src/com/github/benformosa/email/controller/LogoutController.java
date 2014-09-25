package com.github.benformosa.email.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();

    boolean wasValid = (session.getAttribute("username") != null);
    session.setAttribute("username", null);
    session.invalidate();

    String query = "";
    if (wasValid) {
      query = "?error=logout";
    }
    response.sendRedirect(request.getContextPath() + "/login" + query);
  }
}
