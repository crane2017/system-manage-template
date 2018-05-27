package com.mksoft.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@MapperScan(basePackages = {"com.mksoft.shop.query","com.mksoft.shop.model.mapper"})
@SpringBootApplication
@ServletComponentScan
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	//
	//
//	@Bean
//	public DataSource dataSource() {
//		DruidDataSource dataSource = new DruidDataSource();
//		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://192.168.157.131:3306/springboot");
//		dataSource.setUsername("code");
//		dataSource.setMaxActive(30);
//		dataSource.setPassword("123456");
//		DatabaseDriver databaseDriver = DatabaseDriver.fromJdbcUrl("jdbc:mysql://192.168.157.131:3306/springboot");
//		String validationQuery = databaseDriver.getValidationQuery();
//		try {
//			dataSource.setFilters("stat, wall");
//			if (validationQuery != null) {
//				dataSource.setTestOnBorrow(true);
//				dataSource.setValidationQuery(validationQuery);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return dataSource;
//	}
//
//	@Bean
//	public SqlSessionFactory sqlSessionFactory() throws Exception {
//		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//		bean.setDataSource(dataSource());
//		return bean.getObject();
//
//	}

}
