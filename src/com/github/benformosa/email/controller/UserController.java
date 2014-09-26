package com.github.benformosa.email.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.benformosa.email.model.User;
import com.github.benformosa.email.model.UserDAO;

public class UserController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    UserDAO userDAO = new UserDAO(this.getServletConfig().getServletContext()
        .getRealPath("/WEB-INF"));

    try {
      String username = (String) request.getParameter("user");

      if (username == null) {
        response.sendRedirect(request.getContextPath() + "/secure/users");
        return;
      }

      User user = userDAO.getUser(username);
      request.setAttribute("user", user);
    } catch (NumberFormatException | NullPointerException e) {
      response.sendRedirect(request.getContextPath() + "/secure/users");
    }
    getServletContext().getRequestDispatcher("/secure/userpage").forward(
        request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();

    String username = (String) session.getAttribute("username");
    String name = request.getParameter("name");

    UserDAO userDAO;
    userDAO = new UserDAO(this.getServletConfig().getServletContext()
        .getRealPath("/WEB-INF"));

    userDAO.updateUser(username, name);

    response.sendRedirect(request.getContextPath() + "/secure/user?user="
      + username + "&status=updated");
  }
}
