package model;

public class Message {
  String Body;
  int recipientId;
  int senderId;
  String subject;

  public String getBody() {
    return Body;
  }

  public int getRecipientId() {
    return recipientId;
  }

  public int getSenderId() {
    return senderId;
  }

  public String getSubject() {
    return subject;
  }
}
