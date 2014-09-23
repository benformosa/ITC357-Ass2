package model;

import java.util.Date;

public class User {
  Date dob;
  String gender;
  String hashedPassword;
  int id;
  String name;
  String username;

  public Date getDob() {
    return dob;
  }

  public String getGender() {
    return gender;
  }

  public String getName() {
    return name;
  }

  public String getUsername() {
    return username;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
