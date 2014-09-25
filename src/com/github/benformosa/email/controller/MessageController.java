package com.github.benformosa.email.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.benformosa.email.model.MessageDAO;

@SuppressWarnings("serial")
public class MessageController extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();

    MessageDAO mDAO = new MessageDAO(this.getServletConfig()
        .getServletContext().getRealPath("/WEB-INF"));

    // get a list of messages
    // forward to messages.jsp
    request.setAttribute("messages",
        mDAO.getMessages((String) session.getAttribute("username")));

    getServletContext().getRequestDispatcher("/secure/messages").forward(
        request, response);
    // select * from messages where username =
    // session.getAttribute("username")

  }

  // GET
  // / - list messages addressed to current logged in user
  // /[id] - get message with id [id]

  // PUT
  // /new - form for sending message
  // /create -

}
