package sml.smartdbe.me.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import sml.smartdbe.me.DBHelper;
import sml.smartdbe.me.StatJobCountService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class StatJobCountServiceImpl extends RemoteServiceServlet implements StatJobCountService {
    @Override
    public Map<String, Integer> jobCount() throws IllegalArgumentException {
        DBHelper dbHelper = new DBHelper();
        List<Map<String, Object>> items = dbHelper.queryObjects("select jobname, count(1) as num from logs group by jobname");

        Map<String, Integer> r = new HashMap<>();
        for (Map<String, Object> item : items) {
            r.put(item.get("jobname").toString(), Integer.parseInt(item.get("num").toString()));
        }
        return r;
    }
}
