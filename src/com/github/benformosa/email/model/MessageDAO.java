package com.github.benformosa.email.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.github.benformosa.email.util.ConnectionFactory;

public class MessageDAO {
  private Connection connection;

  public static enum MessageStatus {
    SENDERBLANK, RECIPIENTBLANK, SUBJECTBLANK, NOSUCHUSER, FAILED, SUCCESS
  }

  public MessageDAO(Connection connection) {
    this.connection = connection;
  }

  public MessageDAO(String configPath) {
    try {
      connection = ConnectionFactory.getConnection(configPath);
    } catch (IOException | SQLException e) {
      e.printStackTrace();
    }
  }

  /*
   * get a message with the given ID for the given user. returns a Message or
   * null if that ID doesn't exist or the user wasn't a recipient
   */
  public Message getMessage(int id, String recipient) {
    String query = "select messages.id, messages.sender, recipients.recipient, messages.subject, messages.body, recipients.trash "
      + "from messages "
      + "inner join recipients on messages.id = recipients.messageid "
      + "where messages.id = ? and recipients.recipient = ?";

    Message message = null;
    PreparedStatement s = null;
    ResultSet r = null;
    try {
      s = connection.prepareStatement(query);
      s.setInt(1, id);
      s.setString(2, recipient);
      try {
        r = s.executeQuery();

        while (r.next()) {
          String sender = r.getString(Message.messageColumnSender);
          String subject = r.getString(Message.messageColumnSubject);
          String body = r.getString(Message.messageColumnBody);
          boolean trash = r.getBoolean(Message.messageColumnTrash);
          message = new Message(id, sender, recipient, subject, body, trash);
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

  public void trashMessage(int id, String recipient) {
    setTrash(id, recipient, true);
  }

  public void unTrashMessage(int id, String recipient) {
    setTrash(id, recipient, false);
  }

  /*
   * update trash column for recipient of message with given id
   */
  public void setTrash(int id, String recipient, boolean trash) {

    String query = "update recipients set trash = ? where messageid = ? and recipient = ?";
    PreparedStatement s = null;
    try {
      s = connection.prepareStatement(query);

      s.setBoolean(1, trash);
      s.setInt(2, id);
      s.setString(3, recipient);
      s.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        s.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /*
   * get all recipients for the message with the given id
   */
  public String[] getRecipients(int id) {
    List<String> recipients = new ArrayList<String>();

    String query = "select recipients.recipient " + "from messages "
      + "inner join recipients on messages.id = recipients.messageid "
      + "where messages.id = ?";

    PreparedStatement s = null;
    ResultSet r = null;
    try {
      s = connection.prepareStatement(query);
      s.setInt(1, id);
      try {
        r = s.executeQuery();

        while (r.next()) {
          String recipient = r.getString(Message.messageColumnRecipient);
          recipients.add(recipient);
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
    return recipients.toArray(new String[recipients.size()]);
  }

  /*
   * get all non-trash messages with the given username as a recipient
   */
  public Message[] getInbox(String username) {
    return getMessages(username, false);
  }

  /*
   * get all messages with the given username as a recipient
   */
  public Message[] getMessages(String username, boolean trash) {
    List<Message> messages = new ArrayList<Message>();

    String query = "select messages.id, messages.sender, recipients.recipient, messages.subject, messages.body, recipients.recipient "
      + "from messages "
      + "inner join recipients on messages.id = recipients.messageid "
      + "where recipients.recipient = ? and recipients.trash = ?";

    PreparedStatement s = null;
    ResultSet r = null;
    try {
      s = connection.prepareStatement(query);
      s.setString(1, username);
      s.setBoolean(2, trash);
      try {
        r = s.executeQuery();

        while (r.next()) {
          int id = r.getInt(Message.messageColumnId);
          String sender = r.getString(Message.messageColumnSender);
          String recipient = r.getString(Message.messageColumnRecipient);
          String subject = r.getString(Message.messageColumnSubject);
          String body = r.getString(Message.messageColumnBody);
          messages
              .add(new Message(id, sender, recipient, subject, body, trash));
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

  // insert a message and return its id
  private Integer insertMessage(String sender, String subject, String body) {
    String query = "insert into " + Message.messageTable + " ("
      + Message.messageColumnSender + "," + Message.messageColumnSubject + ","
      + Message.messageColumnBody + ") " + "values (?, ?, ?)";

    Integer messageid = null;
    PreparedStatement s = null;
    ResultSet r = null;
    try {
      s = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      s.setString(1, sender);
      s.setString(2, subject);
      s.setString(3, body);

      try {
        s.executeUpdate();

        // get the id of the new message
        r = s.getGeneratedKeys();
        if (r.next()) {
          messageid = r.getInt(1);
        } else {
          // if we can't get the id, don't continue.
          // we should probably delete the message at this point also
          throw new SQLException();
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
    return messageid;
  }

  private void insertRecipient(int messageid, String recipient) {
    String query = "insert into recipients (messageid, recipient) values (?, ?)";

    PreparedStatement s = null;
    try {

      s = connection.prepareStatement(query);
      s.setInt(1, messageid);
      s.setString(2, recipient);
      s.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        s.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /*
   * send a message Checks that sender, recipient and subject are not empty or
   * null, checks the given recipient exists, then create a new message.
   */
  public MessageStatus sendMessage(String sender, String[] recipients,
      String subject, String body) {
    if (sender.isEmpty() | sender == null) {
      return MessageStatus.SENDERBLANK;
    }
    if (recipients.length == 0 | recipients == null) {
      return MessageStatus.RECIPIENTBLANK;
    }
    if (subject.isEmpty() | subject == null) {
      return MessageStatus.SUBJECTBLANK;
    }

    // create a list of users recipients that exist.
    // TODO: tell the user which users received the message
    ArrayList<String> recipientsExist = new ArrayList<String>();
    UserDAO userDAO = new UserDAO(connection);

    for (String recipient : recipients) {
      if (userDAO.getUser(recipient) != null) {
        recipientsExist.add(recipient);
      }
    }

    // check that at least one recipient exists
    if (!recipientsExist.isEmpty()) {
      Integer id = insertMessage(sender, subject, body);

      if (id != null) {
        // for each recipient that exists
        for (String recipient : recipientsExist) {
          insertRecipient(id, recipient);
        }
        return MessageStatus.SUCCESS;
      } else {
        return MessageStatus.FAILED;
      }
    } else {
      return MessageStatus.NOSUCHUSER;
    }
  }

  /*
   * given a message id and recipient, delete the message for that user
   */
  public void deleteMessage(int id, String recipient) {
    deleteRecipient(id, recipient);
    deleteMessage(id);
  }

  /*
   * delete the message with the given id only if it has no recipients
   */
  private void deleteMessage(int id) {
    String[] recipients = getRecipients(id);

    // if the message has no recipients
    if (recipients.length == 0) {
      String query = "delete from messages where id = ?";
      PreparedStatement s = null;
      try {
        s = connection.prepareStatement(query);
        s.setInt(1, id);
        s.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        try {
          s.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /*
   * delete the recipient for the message with the given id
   */
  private void deleteRecipient(int id, String recipient) {
    String query = "delete from recipients where messageid = ? and recipient = ?";

    PreparedStatement s = null;
    try {
      s = connection.prepareStatement(query);
      s.setInt(1, id);
      s.setString(2, recipient);
      s.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        s.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /*
   * get all trash messages with the given username as a recipient
   */
  public Message[] getTrash(String username) {
    return getMessages(username, true);
  }
}
