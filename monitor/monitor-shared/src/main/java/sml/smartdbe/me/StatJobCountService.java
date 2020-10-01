package sml.smartdbe.me;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.Map;

@RemoteServiceRelativePath("jobCount")
public interface StatJobCountService extends RemoteService {
    /**
     * @return 任务，执行次数
     * @throws IllegalArgumentException
     */
    Map<String, Integer> jobCount() throws IllegalArgumentException;
}
