package com.example.mvn.pkg.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig implements TransactionManagementConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	public DataSource druidDataSource() {
		final DruidDataSource druidDataSource = new DruidDataSource();
		logger.info("druid dataSource init success ......");
		return druidDataSource;
	}

	@Bean
	@Override
	public TransactionManager annotationDrivenTransactionManager() {
		final DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(druidDataSource());
		logger.info("TransactionManager init success ......");
		return transactionManager;
	}

}
