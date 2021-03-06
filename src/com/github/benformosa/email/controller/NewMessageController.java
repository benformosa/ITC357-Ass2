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
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    getServletContext().getRequestDispatcher("/secure/newmessagepage").forward(
        request, response);
  }

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

    // convert recipient to array. recipients should be separated by ; with
    // optional whitespace around the ;
    String[] recipientArray = new String[0];
    if (recipient != null & !recipient.isEmpty()) {
      recipientArray = recipient.split("\\s*;\\s*");
    }

    status = messageDAO.sendMessage(sender, recipientArray, subject, body);

    if (status == MessageStatus.SUCCESS) {
      response.sendRedirect(request.getContextPath()
        + "/secure/inbox?status=sent");
    } else if (status == MessageStatus.FAILED) {
      request.setAttribute("messageSent", "false");
      getServletContext().getRequestDispatcher("/secure/newmessagepage")
          .forward(request, response);
    } else if (status == MessageStatus.NOSUCHUSER) {
      request.setAttribute("messageSent", "nosuchuser");
      getServletContext().getRequestDispatcher("/secure/newmessagepage")
          .forward(request, response);
    } else if (status == MessageStatus.RECIPIENTBLANK) {
      request.setAttribute("emptyAttribute", "recipient");
      getServletContext().getRequestDispatcher("/secure/newmessagepage")
          .forward(request, response);
    } else if (status == MessageStatus.SUBJECTBLANK) {
      request.setAttribute("emptyAttribute", "subject");
      getServletContext().getRequestDispatcher("/secure/newmessagepage")
          .forward(request, response);
    }
  }
}
