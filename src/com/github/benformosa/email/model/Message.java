package com.github.benformosa.email.model;

public class Message {
  public static final String messageColumnBody = "body";
  public static final String messageColumnId = "id";
  public static final String messageColumnRecipient = "recipient";
  public static final String messageColumnSender = "sender";
  public static final String messageColumnSubject = "subject";
  public static final String messageTable = "messages";

  public String body;
  public int id;
  public String recipient;
  public String sender;
  public String subject;

  public Message(int id, String sender, String recipient, String subject,
      String body) {
    this.id = id;
    this.sender = sender;
    this.recipient = recipient;
    this.subject = subject;
    this.body = body;
  }

  public static Message getMessage(int id) {

    return null;

  }
}
