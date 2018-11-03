package com.azhen.copy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Transactional
public class ApplicationTests {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate1;

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate2;

   @Before
    public void setUp() {

    }
    private static final Integer THRESHOLD = 100;

    @Test
    @Repeat(10)
    public void test() throws Exception {
        syncRealtime("realtime_ai");
        syncRealtime("realtime_di");
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

        Assert.assertEquals(String.valueOf(realtimeAis.size()), jdbcTemplate1.queryForObject("select count(1) from " + table, String.class));
    }
}