package sml.smartdbe.me;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class StatJobTimeServiceImpl extends RemoteServiceServlet implements StatJobTimeService {
    @Override
    public Map<String, Map<String, Integer>> jobTime() throws IllegalArgumentException {
        DBHelper dbHelper = new DBHelper();
        List<Map<String, Object>> items = dbHelper.queryJobTime();

        Map<String, Map<String, Integer>> r = new HashMap<>();
        for (Map<String, Object> item : items) {
            String sid = item.get("sessionid").toString();
            Double d = Double.parseDouble(item.get("t").toString());
            Integer t = d.intValue();
            Map<String, Integer> m = new HashMap<>();
            m.put(sid, t);

            r.put(item.get("jobname").toString(), m);
        }
        return r;
    }
}
