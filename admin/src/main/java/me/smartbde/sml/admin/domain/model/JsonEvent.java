package me.smartbde.sml.admin.domain.model;

import java.util.HashMap;

public class JsonEvent {
    public HashMap<String, String> headers;
    public String body;

    public JsonEvent() {
        headers = new HashMap<>();
    }
}
