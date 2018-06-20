package net.mcavenue.redspigot.configuration.context;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import net.mcavenue.redspigot.configuration.AdapterlessConfigManager;
import net.mcavenue.redspigot.configuration.DBConfig;

@Configuration
@EnableJpaRepositories(basePackages = "net.mcavenue.redspigot")
public class DBContext {
	@Bean
	@DependsOn("data-source")
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		return jdbcTemplate;
	}

	@Bean(name = "db-config")
	public AdapterlessConfigManager<DBConfig> cfg() {
		AdapterlessConfigManager<DBConfig> cfg = new AdapterlessConfigManager("db.json", DBConfig.class);
		cfg.init();
		return cfg;
	}

	@Bean("data-source")
	@DependsOn("db-config")
	public DataSource dataSource(AdapterlessConfigManager<DBConfig> cfgM) {
		DBConfig cfg = cfgM.getConfig();
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(cfg.getUrl());
		dataSource.setUsername(cfg.getUser());
		dataSource.setPassword(cfg.getPassword());
		return dataSource;
	}

	@Bean
	public DataSourceTransactionManager txManager(DataSource source) {
		return new DataSourceTransactionManager(source);
	}

	@Bean
	public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
		return transactionManager;
	}
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan(new String[]{"net.mcavenue.redspigot.data", "net.mcavenue.redspigot.model"});
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		em.setJpaProperties(properties);
		return em;
	}

}
