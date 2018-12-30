package me.principality.springtest.model;

import java.util.HashMap;

public class FlumeEvent {
    public HashMap<String, String> headers;
    public String body;

    public FlumeEvent() {
        headers = new HashMap<>();
    }
}
