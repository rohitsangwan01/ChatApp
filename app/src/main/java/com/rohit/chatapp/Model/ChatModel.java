package com.rohit.chatapp.Model;

public class ChatModel {
    String message,sender,reciever,number;

    public ChatModel(String message, String sender, String reciever, String number) {
        this.message = message;
        this.sender = sender;
        this.reciever = reciever;
        this.number = number;
    }

    public ChatModel(){};

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
