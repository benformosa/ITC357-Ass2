package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Authenticator;
import util.Authenticator.UserStatus;

@SuppressWarnings("serial")
public class LoginController extends HttpServlet {
  // when posted to, check username and password are OK and redirect
  // accordingly.

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    Authenticator authenticator;
    try {
      authenticator = new Authenticator(this.getServletConfig()
          .getServletContext().getRealPath("/WEB-INF"));
      UserStatus authenticated = authenticator.authenticate(username, password);
      if (authenticated == UserStatus.SUCCESS) {

        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        log("login success for " + session.getAttribute("username"));

        response.sendRedirect(request.getContextPath() + "/secure/main");
      } else {
        if (authenticated == UserStatus.FAILED) {
          log("Login failed for " + username);
          request.setAttribute("loginfailed", "true");
        } else if (authenticated == UserStatus.USERNAMEBLANK) {
          log("No username");
          request.setAttribute("emptyattribute", "username");
        } else if (authenticated == UserStatus.PASSWORDBLANK) {
          log("No password for " + username);
          request.setAttribute("emptyattribute", "password");
        }
        getServletContext().getRequestDispatcher("/login").forward(request,
            response);
      }
    } catch (SQLException | URISyntaxException e) {
      e.printStackTrace();
    }
  }
}
