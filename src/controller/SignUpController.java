package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Authenticator;
import util.Authenticator.UserStatus;

@SuppressWarnings("serial")
public class SignUpController extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    Authenticator authenticator;
    try {
      authenticator = new Authenticator();
      UserStatus createStatus;

      createStatus = authenticator.newUser(username, password);

      if (createStatus == UserStatus.SUCCESS) {
        request.setAttribute("logincreated", "true");
        getServletContext().getRequestDispatcher("/login").forward(request,
            response);
      } else if (createStatus == UserStatus.FAILED) {
        request.setAttribute("usernameexists", "true");
        getServletContext().getRequestDispatcher("/signup").forward(request,
            response);
      } else if (createStatus == UserStatus.USERNAMEBLANK) {
        request.setAttribute("emptyattribute", "username");
        getServletContext().getRequestDispatcher("/signup").forward(request,
            response);
      } else if (createStatus == UserStatus.PASSWORDBLANK) {
        request.setAttribute("emptyattribute", "password");
        getServletContext().getRequestDispatcher("/signup").forward(request,
            response);
      }
    } catch (SQLException | URISyntaxException e) {
      e.printStackTrace();
    }
  }
}
