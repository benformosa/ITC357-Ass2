package util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authenticator {
  private Connection connection;

  public Authenticator() throws SQLException, IOException, URISyntaxException {
    // connection = ConnectionFactory.getConnection();
    connection = ConnectionFactory.getHardCodedConnection();
  }

  public boolean authenticate(String username, String password) {
    String query = "select " + util.Email.userColumnUsername + ","
      + util.Email.userColumnHashedPassword + "," + util.Email.userColumnSalt
      + " from " + util.Email.userTable + " where "
      + util.Email.userColumnUsername + "= ?";

    System.out.println(query);

    boolean authenticate = false;
    byte[] dbHashedPassword = null;
    byte[] dbSalt = null;
    PreparedStatement s = null;
    ResultSet r = null;
    try {
      s = connection.prepareStatement(query);
      s.setString(1, username);
      r = s.executeQuery();

      while (r.next()) {
        dbHashedPassword = r.getBytes(util.Email.userColumnHashedPassword);
        dbSalt = r.getBytes(util.Email.userColumnSalt);
      }

      authenticate = Passwords.isExpectedPassword(password.toCharArray(),
          dbSalt, dbHashedPassword);

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return authenticate;
  }

  public boolean newUser(String username, String password) {
    String query = "select " + util.Email.userColumnUsername + " where "
      + util.Email.userColumnUsername + "= ?";

    PreparedStatement s = null;
    ResultSet r = null;
    try {
      s = connection.prepareStatement(query);
      s.setString(1, username);
      r = s.executeQuery();

      // if username is available
      if (!r.next()) {
        // insert new user
        s = connection.prepareStatement("insert into " + Email.userTable + " ("
          + Email.userColumnUsername + "," + Email.userColumnHashedPassword
          + "," + Email.userColumnSalt + ") " + "values (?, ?, ?)");

        byte[] salt = Passwords.getNextSalt();

        s.setString(1, username);
        s.setBytes(2, Passwords.hash(password.toCharArray(), salt));
        s.setBytes(3, salt);
        s.executeUpdate();

        return true;
      } else {
        return false;
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return false;
  }
}
