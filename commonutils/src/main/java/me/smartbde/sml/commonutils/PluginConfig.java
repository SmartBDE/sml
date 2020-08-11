package me.smartbde.sml.commonutils;

import javafx.util.Pair;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PluginConfig {
    public enum Type {
        INPUT, FILTER, OUTPUT
    }

    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Iterable<String> getSections() {
        throw new NotImplementedException();
    }

    public Iterable<Pair<String, String>> getKeyValues(String section) {
        throw new NotImplementedException();
    }

    public String getKeyValue(String section, String key) {
        throw new NotImplementedException();
    }
}
