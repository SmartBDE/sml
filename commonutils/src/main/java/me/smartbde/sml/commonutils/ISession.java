package me.smartbde.sml.commonutils;

public interface ISession {
    String getId();
    Object getAttribute(String key);
    void setAttribute(String key, Object object);
    void removeAttribute(String key);
}
