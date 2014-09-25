package com.github.benformosa.email.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.benformosa.email.model.UserDAO;

@SuppressWarnings("serial")
public class UserController extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    UserDAO userDAO = new UserDAO(this.getServletConfig().getServletContext()
        .getRealPath("/WEB-INF"));

    request.setAttribute("users", userDAO.getUsers());

    getServletContext().getRequestDispatcher("/secure/userspage").forward(
        request, response);
  }
}
