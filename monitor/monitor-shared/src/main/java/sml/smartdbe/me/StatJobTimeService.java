package sml.smartdbe.me;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.Map;

@RemoteServiceRelativePath("jobTime")
public interface StatJobTimeService extends RemoteService {
    /**
     * @return 任务，会话，耗时
     * @throws IllegalArgumentException
     */
    Map<String, Map<String, Integer>> jobTime() throws IllegalArgumentException;
}
