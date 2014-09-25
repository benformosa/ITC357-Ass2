package com.github.benformosa.email.model;

import java.io.Serializable;

public class User implements Serializable {
  private static final long serialVersionUID = 1L;
  public static final String userColumnHashedPassword = "hashedpassword";
  public static final String userColumnSalt = "salt";
  public static final String userColumnUsername = "username";
  public static final String userTable = "users";

  public String username;

  public User(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
