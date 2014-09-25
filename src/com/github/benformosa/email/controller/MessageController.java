package com.github.benformosa.email.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.benformosa.email.model.Message;
import com.github.benformosa.email.model.MessageDAO;

public class MessageController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();

    MessageDAO messageDAO = new MessageDAO(this.getServletConfig()
        .getServletContext().getRealPath("/WEB-INF"));

    try {
      int id = Integer.parseInt(request.getParameter("id"));
      Message message = messageDAO.getMessage(id);
      if (message.getRecipient().equals(session.getAttribute("username"))) {
        request.setAttribute("message", message);
      }
    } catch (NumberFormatException | NullPointerException e) {
    }
    getServletContext().getRequestDispatcher("/secure/messagepage").forward(
        request, response);
  }
}
