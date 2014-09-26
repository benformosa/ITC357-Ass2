package com.github.benformosa.email.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.benformosa.email.model.UserDAO;

public class UsersController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    UserDAO userDAO = new UserDAO(this.getServletConfig().getServletContext()
        .getRealPath("/WEB-INF"));
    HttpSession session = request.getSession();

    request.setAttribute("users", userDAO.getUsers());
    request.setAttribute("contacted",
        userDAO.allContacted((String) session.getAttribute("username")));
    request.setAttribute("contacters",
        userDAO.contactedBy((String) session.getAttribute("username")));

    getServletContext().getRequestDispatcher("/secure/userspage").forward(
        request, response);
  }
}
