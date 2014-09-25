package com.github.benformosa.email.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.benformosa.email.model.MessageDAO;
import com.github.benformosa.email.model.MessageDAO.MessageStatus;

public class NewMessageController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();

    String sender = (String) session.getAttribute("username");
    String recipient = request.getParameter("recipient");
    String subject = request.getParameter("subject");
    String body = request.getParameter("body");

    MessageDAO messageDAO = new MessageDAO(this.getServletConfig()
        .getServletContext().getRealPath("/WEB-INF"));

    MessageStatus status;

    status = messageDAO.sendMessage(sender, recipient, subject, body);

    if (status == MessageStatus.SUCCESS) {
      request.setAttribute("messagesent", "true");
      getServletContext().getRequestDispatcher("/secure/inbox").forward(
          request, response);
    } else if (status == MessageStatus.FAILED) {
      request.setAttribute("messagesent", "false");
      getServletContext().getRequestDispatcher("/secure/newmessage").forward(
          request, response);
    } else if (status == MessageStatus.SENDERBLANK) {
      request.setAttribute("emptyattribute", "sender");
      getServletContext().getRequestDispatcher("/secure/newmessage").forward(
          request, response);
    } else if (status == MessageStatus.RECIPIENTBLANK) {
      request.setAttribute("emptyattribute", "recipient");
      getServletContext().getRequestDispatcher("/secure/newmessage").forward(
          request, response);
    } else if (status == MessageStatus.SUBJECTBLANK) {
      request.setAttribute("emptyattribute", "subject");
      getServletContext().getRequestDispatcher("/secure/newmessage").forward(
          request, response);
    }

  }
}
