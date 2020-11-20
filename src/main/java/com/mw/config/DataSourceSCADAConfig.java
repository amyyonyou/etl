package com.mw.config;

//@Configuration
//@EnableTransactionManagement
public class DataSourceSCADAConfig {

//	@Inject
//	private DataSource dataSourceSCADA;

	/*@Bean(name = "jtSCADA")
	public NamedParameterJdbcTemplate jtSCADA() {
		return new NamedParameterJdbcTemplate(dataSourceSCADA);
	}

	@Bean(name = "transactionManagerSCADA")
	public PlatformTransactionManager transactionManagerSCADA() {
		return new DataSourceTransactionManager(dataSourceSCADA);
	}*/

	/*@Configuration
	static class Standard {

		@Inject
		private Environment environment;

		@Bean
		public DataSource dataSourceSCADA() {
			DataSource dataSource = new DataSource();
			dataSource.setDriverClassName(environment.getProperty("jdbc.driver.scada"));
			dataSource.setUrl(environment.getProperty("jdbc.url.scada"));
			dataSource.setUsername(environment.getProperty("jdbc.username.scada"));
			dataSource.setPassword(environment.getProperty("jdbc.password.scada"));

			dataSource.setInitialSize(5);
			dataSource.setMinIdle(5);
			dataSource.setMaxIdle(10);
			dataSource.setMaxActive(15);
			dataSource.setTestOnBorrow(true);
			dataSource.setValidationQuery("select 1");
			dataSource.setValidationInterval(30000);

			return dataSource;
		}
	}*/
}
