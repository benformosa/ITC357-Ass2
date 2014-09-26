package com.github.benformosa.email.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.benformosa.email.model.MessageDAO;

public class MessagesController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();

    MessageDAO messageDAO = new MessageDAO(this.getServletConfig()
        .getServletContext().getRealPath("/WEB-INF"));

    // get a list of messages and forward to inbox.jsp
    request.setAttribute("messages",
        messageDAO.getMessages((String) session.getAttribute("username")));

    getServletContext().getRequestDispatcher("/secure/messages").forward(
        request, response);
  }
}
