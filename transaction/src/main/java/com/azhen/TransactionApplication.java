package com.azhen;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Date;

@SpringBootApplication
@EnableTransactionManagement
public class TransactionApplication implements TransactionManagementConfigurer {
//public class TransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionApplication.class, args);
	}

	@Resource(name="txManager1")
	private PlatformTransactionManager txManager1;

	@Resource(name="transactionManager")
	private PlatformTransactionManager txManager2;

	@Bean
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("Linkgoo123!@#");
		dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3308/esquelpass2?verifyServerCertificate=false&useSSL=false&allowMultiQueries=true&useUnicode=true&characterEncoding=utf-8");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setAutoCommit(true);
		// dataSource.setAutoCommit(true);
		return dataSource;
	}

	// 创建事务管理器1
	@Bean(name = "txManager1")
	public PlatformTransactionManager txManager(DataSource dataSource) {
		System.out.println(dataSource.getClass().getName());
		return new DataSourceTransactionManager(dataSource);
	}

	// 创建事务管理器2
	@Bean(name = "transactionManager")
	public JpaTransactionManager txManager2(EntityManagerFactory factory) {
		return new JpaTransactionManager(factory);
	}

	@Bean
	public Object testBean(JpaTransactionManager platformTransactionManager){
		System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
		return new Object();
	}

	@Bean
	public Object testBean2(DataSourceTransactionManager platformTransactionManager){
		System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
		return new Object();
	}

	// 设置默认事务管理器
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return txManager2;
	}
}