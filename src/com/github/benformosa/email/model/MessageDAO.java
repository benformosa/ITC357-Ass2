package com.github.benformosa.email.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.benformosa.email.util.ConnectionFactory;

public class MessageDAO {
  private Connection connection;

  public MessageDAO(String configPath) {
    try {
      connection = ConnectionFactory.getConnection(configPath);
    } catch (IOException | SQLException e) {
      e.printStackTrace();
    }
  }

  public Message getMessage(int id) {
    String query = "select " + Message.messageColumnId + ","
      + Message.messageColumnSender + "," + Message.messageColumnRecipient
      + "," + Message.messageColumnSubject + "," + Message.messageColumnBody
      + " from " + Message.messageTable + " where " + Message.messageColumnId
      + " = ?";

    Message message = null;
    PreparedStatement s = null;
    ResultSet r = null;
    try {
      s = connection.prepareStatement(query);
      s.setInt(1, id);
      r = s.executeQuery();

      while (r.next()) {
        String sender = r.getString(Message.messageColumnSender);
        String recipient = r.getString(Message.messageColumnRecipient);
        String subject = r.getString(Message.messageColumnSubject);
        String body = r.getString(Message.messageColumnBody);
        message = new Message(id, sender, recipient, subject, body);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return message;
  }

  public Message[] getMessages(String username) {
    List<Message> messages = new ArrayList<Message>();

    String query = "select " + Message.messageColumnId + ","
      + Message.messageColumnSender + "," + Message.messageColumnRecipient
      + "," + Message.messageColumnSubject + "," + Message.messageColumnBody
      + " from " + Message.messageTable + " where "
      + Message.messageColumnRecipient + " = ?";

    PreparedStatement s = null;
    ResultSet r = null;
    try {
      s = connection.prepareStatement(query);
      s.setString(1, username);
      r = s.executeQuery();

      while (r.next()) {
        int id = r.getInt(Message.messageColumnId);
        String sender = r.getString(Message.messageColumnSender);
        String recipient = r.getString(Message.messageColumnRecipient);
        String subject = r.getString(Message.messageColumnSubject);
        String body = r.getString(Message.messageColumnBody);
        messages.add(new Message(id, sender, recipient, subject, body));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return messages.toArray(new Message[messages.size()]);
  }
}
