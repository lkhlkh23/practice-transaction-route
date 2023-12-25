package com.practice.transaction.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

	@Bean("masterDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.master.hikari")
	public DataSource masterDataSource() {
		return DataSourceBuilder.create()
								.type(HikariDataSource.class)
								.build();
	}

	@Bean("slaveDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.slave.hikari")
	public DataSource slaveDataSource() {
		return DataSourceBuilder.create()
								.type(HikariDataSource.class)
								.build();
	}

	@Bean("switchDataSource")
	@DependsOn({"masterDataSource", "slaveDataSource"})
	public DataSource switchDataSource(@Qualifier("masterDataSource") final DataSource masterDataSource,
									   @Qualifier("slaveDataSource") final DataSource slaveDataSource) {
		final DataSourceSwitch sourceSwitch = new DataSourceSwitch();
		final Map<Object, Object> dataSources = new HashMap<>() {
			{
				put("master", masterDataSource);
				put("slave", slaveDataSource);
			}
		};

		sourceSwitch.setTargetDataSources(dataSources);
		sourceSwitch.setDefaultTargetDataSource(masterDataSource);

		return sourceSwitch;
	}

	@Bean
	@Primary
	@DependsOn("switchDataSource")
	public LazyConnectionDataSourceProxy routingDataSource(@Qualifier("switchDataSource") final DataSource switchDataSource) {
		return new LazyConnectionDataSourceProxy(switchDataSource);
	}

	public static class DataSourceSwitch extends AbstractRoutingDataSource {

		@Override
		protected Object determineCurrentLookupKey() {
			return (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) ? "slave" : "master";
		}
	}

}
