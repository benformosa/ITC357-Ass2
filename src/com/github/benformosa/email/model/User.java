package com.github.benformosa.email.model;

import java.io.Serializable;

public class User implements Serializable {
  private static final long serialVersionUID = 1L;
  public static final String userColumnHashedPassword = "hashedpassword";
  public static final String userColumnSalt = "salt";
  public static final String userColumnUsername = "username";
  public static final String userColumnName = "name";
  public static final String userTable = "users";

  // public Date dob;
  public String name;
  public String username;

  public User(/* Date dob, */String username, String name) {
    // this.dob = dob;
    this.name = name;
    this.username = username;
  }

  /*
   * public Date getDob() { return dob; }
   */

  public String getName() {
    return name;
  }

  public String getUsername() {
    return username;
  }
}
