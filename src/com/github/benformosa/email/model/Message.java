package com.github.benformosa.email.model;

import java.io.Serializable;

public class Message implements Serializable {
  private static final long serialVersionUID = 1L;
  public static final String messageColumnBody = "body";
  public static final String messageColumnId = "id";
  public static final String messageColumnRecipient = "recipient";
  public static final String messageColumnSender = "sender";
  public static final String messageColumnSubject = "subject";
  public static final String messageColumnTrash = "trash";
  public static final String messageTable = "messages";

  public String body;
  public int id;
  public String recipient;
  public String sender;
  public String subject;
  public boolean trash;

  public Message(int id, String sender, String recipient, String subject,
      String body, boolean trash) {
    this.id = id;
    this.sender = sender;
    this.recipient = recipient;
    this.subject = subject;
    this.body = body;
    this.trash = trash;
  }

  public String getBody() {
    return body;
  }

  public int getId() {
    return id;
  }

  public String getRecipient() {
    return recipient;
  }

  public String getSender() {
    return sender;
  }

  public String getSubject() {
    return subject;
  }

  public boolean isTrash() {
    return trash;
  }
}
