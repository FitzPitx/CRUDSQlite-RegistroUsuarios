package com.edu.uniempresarial.crud_registrousuarios.classes;

public class User {
    private int document;
    private String user;
    private String names;
    private String lastnames;
    private String pass;

    private int status;

    public User() {
    }

    public User(int document, String user, String names, String lastnames, String pass, int status) {
        this.document = document;
        this.user = user;
        this.names = names;
        this.lastnames = lastnames;
        this.pass = pass;
        this.status = status;
    }

    public int getDocument() {
        return document;
    }

    public void setDocument(int document) {
        this.document = document;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastnames() {
        return lastnames;
    }

    public void setLastnames(String lastnames) {
        this.lastnames = lastnames;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "document=" + document +
                ", user='" + user + '\'' +
                ", names='" + names + '\'' +
                ", lastnames='" + lastnames + '\'' +
                ", pass='" + pass + '\'' +
                ", status=" + status +
                '}';
    }
}
