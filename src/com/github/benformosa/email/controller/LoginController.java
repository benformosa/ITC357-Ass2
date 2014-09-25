package com.github.benformosa.email.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.benformosa.email.model.UserDAO;
import com.github.benformosa.email.model.UserDAO.UserStatus;

public class LoginController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher("/loginpage").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    UserDAO userDAO;
    userDAO = new UserDAO(this.getServletConfig().getServletContext()
        .getRealPath("/WEB-INF"));
    UserStatus authenticated = userDAO.authenticate(username, password);
    if (authenticated == UserStatus.SUCCESS) {

      HttpSession session = request.getSession();
      session.setAttribute("username", username);
      log("login success for " + session.getAttribute("username"));

      response.sendRedirect(request.getContextPath() + "/secure/inbox");
    } else {
      if (authenticated == UserStatus.FAILED) {
        log("Login failed for " + username);
        request.setAttribute("loginFailed", "true");
      } else if (authenticated == UserStatus.USERNAMEBLANK) {
        log("No username");
        request.setAttribute("emptyAttribute", "username");
      } else if (authenticated == UserStatus.PASSWORDBLANK) {
        log("No password for " + username);
        request.setAttribute("emptyAttribute", "password");
      }
      getServletContext().getRequestDispatcher("/loginpage").forward(request,
          response);
    }
  }
}
