package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Message;
import util.ConnectionFactory;

@SuppressWarnings("serial")
public class MessageController extends HttpServlet {
	private Connection connection;

	@Override
	public void init() throws ServletException {
		try {
			connection = ConnectionFactory.getConnection(this.getServletConfig()
			    .getServletContext().getRealPath("/WEB-INF"));
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
		HttpSession session = request.getSession();

		// get a list of messages
		// forward to messages.jsp
		request.setAttribute("messages",
		    getMessages((String) session.getAttribute("username")));

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

	public Message[] getMessages(String username) {
		List<Message> messages = new ArrayList<Message>();

		String query = "select " + util.Email.messageColumnSender + ","
		    + util.Email.messageColumnRecipient + ","
		    + util.Email.messageColumnSubject + "," + util.Email.messageColumnBody
		    + " from " + util.Email.messageTable + " where "
		    + util.Email.messageColumnRecipient + " = ?";

		log(query);

		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = connection.prepareStatement(query);
			s.setString(1, username);
			r = s.executeQuery();

			while (r.next()) {
				String sender = r.getString(util.Email.messageColumnSender);
				String recipient = r.getString(util.Email.messageColumnRecipient);
				String subject = r.getString(util.Email.messageColumnSubject);
				String body = r.getString(util.Email.messageColumnBody);
				messages.add(new Message(sender, recipient, subject, body));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages.toArray(new Message[messages.size()]);
	}
}
