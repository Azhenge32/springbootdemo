package com.azhen.copy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Component
public class PilotSynchronizeJob {
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate1;

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate2;

    private static final Integer THRESHOLD = 100;
    @Scheduled(cron = "0/30 * * * * ?")
    public void pushDataScheduled(){
        syncRealtime("realtime_ai");
        syncRealtime("realtime_di");
        syncRealtime("realtime_acc");
    }

    private void syncRealtime(String table) {
        List<Map<String, Object>> realtimeAis = jdbcTemplate1.queryForList("select " + "PtId,Value,UpdateTime" +" from " + table);
        String insertSql = "insert into " + table + "(PtId,Value,UpdateTime) values";
        StringBuilder sqlBuilder = new StringBuilder(insertSql);
        int listSize = 0;
        boolean checkExists = false;
        boolean needToSave = true;
        for (Map<String, Object> map : realtimeAis) {
            Object PtId = map.get("PtID");
            Object Value = map.get("Value");
            Object UpdateTime = map.get("UpdateTime");

            if (checkExists == false) {
                String query = "select * from " + table + " where PtId = " + PtId + " and  UpdateTime = '" + UpdateTime + "'";
                System.out.println(query);
                List<Map<String, Object>> count = jdbcTemplate2.queryForList(query);
                if (!CollectionUtils.isEmpty(count)) {
                    needToSave = false;
                    break;
                } else {
                    jdbcTemplate2.update("DELETE  FROM " + table);
                }

                checkExists = true;
            }

            sqlBuilder.append("(").append(PtId).append(",")
                    .append(Value).append(",")
                    .append("'").append(UpdateTime).append("')");
            System.out.println(sqlBuilder.toString());
            listSize ++;
            if (listSize == THRESHOLD) {
                sqlBuilder.append(";");
                System.out.println(sqlBuilder.toString());
                jdbcTemplate2.update(sqlBuilder.toString());

                listSize = 0;
                sqlBuilder = new StringBuilder(insertSql);
            } else {
                sqlBuilder.append(",");
            }
        }

        if (needToSave && listSize != 0) {
            String sql = sqlBuilder.toString();
            sql = sql.substring(0, sql.length() - 1);
            System.out.println(sql);
            jdbcTemplate2.update(sql);
        }
    }
}
