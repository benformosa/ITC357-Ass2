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
import util.Authenticator.authstatus;

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
			authenticator = new Authenticator();
			authstatus authenticated = authenticator.authenticate(username, password);
			if (authenticated == authstatus.AUTHSUCCESS) {

				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				System.out.println("login success for "
				    + session.getAttribute("username"));

				getServletContext().getRequestDispatcher("/main").forward(request,
				    response);
			} else {
				if (authenticated == authstatus.AUTHFAILED) {
					System.out.println("Login failed for " + username);
					request.setAttribute("loginfailed", "true");
				} else if (authenticated == authstatus.USERNAMEBLANK) {
					System.out.println("No username");
					request.setAttribute("emptyattribute", "username");
				} else if (authenticated == authstatus.PASSWORDBLANK) {
					System.out.println("No password for " + username);
					request.setAttribute("emptyattribute", "password");
				}
				getServletContext().getRequestDispatcher("/login").forward(request,
				    response);
			}
		} catch (SQLException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
