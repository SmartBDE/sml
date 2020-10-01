package sml.smartdbe.me;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Map;

public interface StatJobCountServiceAsync {
    void jobCount(AsyncCallback<Map<String, Integer>> callback)
            throws IllegalArgumentException;
}
