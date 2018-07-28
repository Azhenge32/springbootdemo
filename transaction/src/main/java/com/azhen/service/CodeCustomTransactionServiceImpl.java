package com.azhen.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Service
public class CodeCustomTransactionServiceImpl implements CodeCustomTransactionService {
    @Resource
    private DataSource dataSource;
    @Resource
    private DataSourceTransactionManager txManager;
    @Resource
    private JdbcTemplate jdbcTemplate;

    public void testPlatformTransactionManager() {
        String CREATE_TABLE_SQL = "";
        String INSERT_SQL = "";
        String DROP_TABLE_SQL = "";
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try{
            connection.prepareStatement(CREATE_TABLE_SQL).execute();
            PreparedStatement pstmt = connection.prepareStatement(INSERT_SQL);
            pstmt.setString(1, "test");
            pstmt.execute();
            connection.prepareStatement(DROP_TABLE_SQL).execute();
            txManager.commit(status);
        }catch(Exception ex){
            status.setRollbackOnly();
            txManager.rollback(status);
        }finally{
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public void testPlatformTransactionManager2() {
        String CREATE_TABLE_SQL = "";
        String INSERT_SQL = "";
        String DROP_TABLE_SQL = "";
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try{
            jdbcTemplate.execute(CREATE_TABLE_SQL);
            jdbcTemplate.update(INSERT_SQL, "test");
        }catch(Exception ex){
            txManager.rollback(status);
        }
        txManager.commit(status);
    }

    public void save() {
        String CREATE_TABLE_SQL = "";
        String INSERT_SQL = "";
        String DROP_TABLE_SQL = "";
        TransactionTemplate transactionTemplate = new TransactionTemplate(txManager);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionTemplate.setReadOnly(true);
        transactionTemplate.setTimeout(30);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus arg0) {
                jdbcTemplate.execute(CREATE_TABLE_SQL);
                jdbcTemplate.update(INSERT_SQL, "test");
            }
        });
        jdbcTemplate.execute(DROP_TABLE_SQL);
    }

    /**
     * 用编程式事务实现多线程分段提交
     * 减少网络传输和数据库执行的等待时间
     */
    public void batchSave() {

    }

    /**
     * 两段提交实现
     */
    public void twoStepCommit() {

    }

    /**
     * N段提交实现
     */
    public void nStepCommit() {

    }
}
