package model;

public class Message {
  public String body;
  public String recipient;
  public String sender;
  public String subject;

  public Message(String sender, String recipient, String subject, String body) {
    this.sender = sender;
    this.recipient = recipient;
    this.subject = subject;
    this.body = body;
  }
}
