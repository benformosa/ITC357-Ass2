package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Authenticator;

@SuppressWarnings("serial")
public class SignUpController extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Authenticator authenticator;
		try {
			authenticator = new Authenticator();
			boolean created = authenticator.newUser(username, password);

			if (created) {
				getServletContext().getRequestDispatcher("/login").forward(request,
				    response);

			} else {
				request.setAttribute("usernameexists", "true");
				getServletContext().getRequestDispatcher("/signup").forward(request,
				    response);
			}
		} catch (SQLException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
