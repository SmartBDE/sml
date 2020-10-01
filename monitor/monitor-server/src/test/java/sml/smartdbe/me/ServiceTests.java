package sml.smartdbe.me;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceTests {
    @Test
    public void test() throws Exception {
        DBHelper dbHelper = new DBHelper();
        List<Map<String, Object>> items = dbHelper.queryJobTime();

        Map<String, Map<String, Integer>> r = new HashMap<>();
        String currentJob = "";
        Map<String, Integer> m = new HashMap<>();
        for (Map<String, Object> item : items) {
            if (!currentJob.equals(item.get("jobname").toString())) {
                m = new HashMap<>();
                currentJob = item.get("jobname").toString();
            }
            String sid = item.get("sessionid").toString();
            Double d = Double.parseDouble(item.get("t").toString());
            Integer t = d.intValue();
            m.put(sid, t);

            r.put(currentJob, m);
        }
        System.out.println(r.toString());
    }
}
