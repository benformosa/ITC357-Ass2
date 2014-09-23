package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Authenticator;

@SuppressWarnings("serial")
public class LoginController extends HttpServlet {
  private static Logger logger = Logger.getLogger("LoginController");

  // when posted to, check username and password are OK and redirect
  // accordingly.

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    Authenticator authenticator;
    try {
      authenticator = new Authenticator();
      if (authenticator.authenticate(username, password)) {
        getServletContext().getRequestDispatcher("/success").forward(request,
            response);
      } else {
        request.setAttribute("loginfailed", "true");
        getServletContext().getRequestDispatcher("/login").forward(request,
            response);
      }
    } catch (SQLException | URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
