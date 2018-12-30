package me.principality.springtest.model;

public class UserAction {
    public String user;
    public String action;
    public String parameter;
    public String context;

    public UserAction(String user, String action, String parameter, String context) {
        this.user = user;
        this.action = action;
        this.parameter = parameter;
        this.context = context;
    }

    public String toString() {
        return "/" + this.user + "/" + this.action + "/" + this.parameter + "/" + this.context;
    }
}
