package com.github.benformosa.email.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.benformosa.email.util.ConnectionFactory;
import com.github.benformosa.email.util.Passwords;

public class UserDAO {
  private Connection connection;

  public static enum UserStatus {
    USERNAMEBLANK, PASSWORDBLANK, FAILED, SUCCESS
  }

  public UserDAO(String configFilePath) {
    try {
      connection = ConnectionFactory.getConnection(configFilePath);
    } catch (IOException | SQLException e) {
      e.printStackTrace();
    }
  }

  public User[] getUsers() {
    List<User> users = new ArrayList<User>();

    String query = "select " + User.userColumnUsername + " from "
      + User.userTable;

    PreparedStatement s = null;
    ResultSet r = null;
    try {
      s = connection.prepareStatement(query);
      try {
        r = s.executeQuery();

        while (r.next()) {
          String username = r.getString(User.userColumnUsername);
          users.add(new User(username));
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
    return users.toArray(new User[users.size()]);
  }

  public UserStatus authenticate(String username, String password) {
    if (username.isEmpty() | username == null) {
      return UserStatus.USERNAMEBLANK;
    }
    if (password.isEmpty() | password == null) {
      return UserStatus.PASSWORDBLANK;
    }

    String query = "select " + User.userColumnUsername + ","
      + User.userColumnHashedPassword + "," + User.userColumnSalt + " from "
      + User.userTable + " where " + User.userColumnUsername + " = ?";

    UserStatus authenticated = UserStatus.FAILED;
    byte[] dbHashedPassword = null;
    byte[] dbSalt = null;
    PreparedStatement s = null;
    ResultSet r = null;
    try {
      s = connection.prepareStatement(query);
      s.setString(1, username);
      try {
        r = s.executeQuery();

        while (r.next()) {
          dbHashedPassword = r.getBytes(User.userColumnHashedPassword);
          dbSalt = r.getBytes(User.userColumnSalt);
        }

        if (dbHashedPassword == null | dbSalt == null) {
          return UserStatus.FAILED;
        }

        boolean correctPassword = Passwords.isExpectedPassword(
            password.toCharArray(), dbSalt, dbHashedPassword);
        if (correctPassword) {
          authenticated = UserStatus.SUCCESS;
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
    return authenticated;
  }

  public UserStatus newUser(String username, String password) {
    if (username.isEmpty() | username == null) {
      return UserStatus.USERNAMEBLANK;
    }
    if (password.isEmpty() | password == null) {
      return UserStatus.PASSWORDBLANK;
    }

    String query = "select " + User.userColumnUsername + " from "
      + User.userTable + " where " + User.userColumnUsername + " = ?";

    PreparedStatement s = null;
    ResultSet r = null;
    try {
      s = connection.prepareStatement(query);
      s.setString(1, username);
      try {
        r = s.executeQuery();

        // if username is available
        if (!r.next()) {
          // insert new user
          s = connection.prepareStatement("insert into " + User.userTable
            + " (" + User.userColumnUsername + ","
            + User.userColumnHashedPassword + "," + User.userColumnSalt + ") "
            + "values (?, ?, ?)");

          byte[] salt = Passwords.getNextSalt();

          s.setString(1, username);
          s.setBytes(2, Passwords.hash(password.toCharArray(), salt));
          s.setBytes(3, salt);
          s.executeUpdate();

          return UserStatus.SUCCESS;
        } else {
          return UserStatus.FAILED;
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      } finally {
        r.close();
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    } finally {
      try {
        s.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return UserStatus.FAILED;
  }
}
