package cn.zhmj.zkexample.configuration.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 
 * @author 章敏杰
 * @createdTime 2018年12月14日
 */
@Configuration
public class DruidConfiguration {

	@Bean
	public DataSource druidDataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		return druidDataSource;
	}

	@Bean
	@Autowired
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(druidDataSource());
	}

}
