package com.github.benformosa.email.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.benformosa.email.model.UserDAO;
import com.github.benformosa.email.model.UserDAO.UserStatus;

public class SignUpController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher("/signuppage").forward(request, response);
  }

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
      response.sendRedirect(request.getContextPath() + "/login?status=new");
    } else if (status == UserStatus.FAILED) {
      request.setAttribute("usernameExists", "true");
      getServletContext().getRequestDispatcher("/signuppage").forward(request,
          response);
    } else if (status == UserStatus.USERNAMEBLANK) {
      request.setAttribute("emptyAttribute", "username");
      getServletContext().getRequestDispatcher("/signuppage").forward(request,
          response);
    } else if (status == UserStatus.PASSWORDBLANK) {
      request.setAttribute("emptyAttribute", "password");
      getServletContext().getRequestDispatcher("/signuppage").forward(request,
          response);
    }
  }
}
