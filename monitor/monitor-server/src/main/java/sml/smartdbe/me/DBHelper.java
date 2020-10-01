package sml.smartdbe.me;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Map;

public class DBHelper {
    private static final Jdbi queryJdbi = Jdbi.create(
            PropertiesUtil.prop("application.datasource.url"),
            PropertiesUtil.prop("application.datasource.username"),
            PropertiesUtil.prop("application.datasource.password"));
    private static final Handle queryHandle = queryJdbi.open();

    public List<Map<String, Object>> queryJobCount() {
        String sql = String.format("select jobname, count(1) as num from logs group by jobname");

        List<Map<String, Object>> items = queryHandle.createQuery(sql)
                .mapToMap()
                .list();

        return items;
    }

    public List<Map<String, Object>> queryJobTime() {
        String sql = String.format("select a.jobname, a.sessionid, (a.acttime-b.acttime) as t from " +
                "(select act, acttime, sessionid, jobname from logs where act='stop') a " +
                "join " +
                "(select act, acttime, sessionid, jobname from logs where act='start') b " +
                "on a.sessionid=b.sessionid order by a.jobname");

        List<Map<String, Object>> items = queryHandle.createQuery(sql)
                .mapToMap()
                .list();

        return items;
    }
}
