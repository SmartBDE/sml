package me.smartbde.sml.commonutils;

import java.util.HashMap;
import java.util.UUID;

public class FilterSession implements ISession {
    private String uuid;
    private String jobName;
    private HashMap<String, Object> attributes;

    public FilterSession(String jobName) {
        uuid = UUID.randomUUID().toString();
        this.jobName = jobName;
        attributes = new HashMap<>();
    }

    @Override
    public String getJobName() {
        return jobName;
    }

    @Override
    public String getId() {
        return uuid;
    }

    @Override
    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public void setAttribute(String key, Object object) {
        attributes.put(key, object);
    }

    @Override
    public void removeAttribute(String key) {
        attributes.remove(key);
    }
}
