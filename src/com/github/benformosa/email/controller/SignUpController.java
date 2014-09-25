package com.github.benformosa.email.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.benformosa.email.model.UserDAO;
import com.github.benformosa.email.model.UserDAO.UserStatus;

@SuppressWarnings("serial")
public class SignUpController extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    UserDAO userDAO;
    userDAO = new UserDAO(this.getServletConfig().getServletContext()
        .getRealPath("/WEB-INF"));
    UserStatus status;

    status = userDAO.newUser(username, password);

    if (status == UserStatus.SUCCESS) {
      request.setAttribute("logincreated", "true");
      getServletContext().getRequestDispatcher("/login").forward(request,
          response);
    } else if (status == UserStatus.FAILED) {
      request.setAttribute("usernameexists", "true");
      getServletContext().getRequestDispatcher("/signup").forward(request,
          response);
    } else if (status == UserStatus.USERNAMEBLANK) {
      request.setAttribute("emptyattribute", "username");
      getServletContext().getRequestDispatcher("/signup").forward(request,
          response);
    } else if (status == UserStatus.PASSWORDBLANK) {
      request.setAttribute("emptyattribute", "password");
      getServletContext().getRequestDispatcher("/signup").forward(request,
          response);
    }
  }
}
