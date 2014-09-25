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

  public static enum MessageStatus {
    SENDERBLANK, RECIPIENTBLANK, SUBJECTBLANK, FAILED, SUCCESS
  }

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
      try {
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
      } finally {
        r.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        s.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
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
      try {
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
      } finally {
        r.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        s.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return messages.toArray(new Message[messages.size()]);
  }

  public MessageStatus sendMessage(String sender, String recipient,
      String subject, String body) {
    if (sender.isEmpty() | sender == null) {
      return MessageStatus.SENDERBLANK;
    }
    if (recipient.isEmpty() | recipient == null) {
      return MessageStatus.RECIPIENTBLANK;
    }
    if (subject.isEmpty() | subject == null) {
      return MessageStatus.SUBJECTBLANK;
    }

    PreparedStatement s = null;
    try {
      s = connection.prepareStatement("insert into " + Message.messageTable
        + " (" + Message.messageColumnSender + ","
        + Message.messageColumnRecipient + "," + Message.messageColumnSubject
        + "," + Message.messageColumnBody + ") " + "values (?, ?, ?, ?)");

      s.setString(1, sender);
      s.setString(2, recipient);
      s.setString(3, subject);
      s.setString(4, body);
      s.executeUpdate();

      return MessageStatus.SUCCESS;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        s.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return MessageStatus.FAILED;
  }
}
