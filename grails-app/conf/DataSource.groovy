
hibernate {
	cache.use_second_level_cache = true
	cache.use_query_cache = false
	cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}

// environment specific settings
environments {
	development {
		dataSource {
			pooled = true
			dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
			driverClassName = "com.mysql.jdbc.Driver"
			url = "jdbc:mysql://127.0.0.1/testdb"
			dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
			username = "root"
			password = "password"
			properties {
				maxActive = -1
				maxIdle = 5
				minIdle = 0
				initialSize = 1
				maxWait = 10000
				validationQuery = "SELECT 1"
				testOnBorrow = true
				testOnReturn = true
				testWhileIdle = true
				timeBetweenEvictionRunsMillis = 1000 * 60 * 30
				numTestsPerEvictionRun = 3
				minEvictableIdleTimeMillis = 1000 * 60 * 30
			  }
			properties {

			}
		}
		dataSource_user {
			pooled = true
			dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
			driverClassName = "com.mysql.jdbc.Driver"
			url = "jdbc:mysql://127.0.0.1/testuser"
			dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
			username = "root"
			password = "password"
			properties {
				maxActive = -1
				maxIdle = 5
				minIdle = 0
				initialSize = 1
				maxWait = 10000
				validationQuery = "SELECT 1"
				testOnBorrow = true
				testOnReturn = true
				testWhileIdle = true
				timeBetweenEvictionRunsMillis = 1000 * 60 * 30
				numTestsPerEvictionRun = 3
				minEvictableIdleTimeMillis = 1000 * 60 * 30
			}
		}
	}
	production {
		dataSource {
			pooled = true
			dbCreate = "update"
			driverClassName = "com.mysql.jdbc.Driver"
			url = "jdbc:mysql://127.0.0.1/testdb"
			dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
			username = "root"
			password = "password"
			properties {
				maxActive = -1
				maxIdle = 5
				minIdle = 0
				initialSize = 1
				maxWait = 10000
				validationQuery = "SELECT 1"
				testOnBorrow = true
				testOnReturn = true
				testWhileIdle = true
				timeBetweenEvictionRunsMillis = 1000 * 60 * 30
				numTestsPerEvictionRun = 3
				minEvictableIdleTimeMillis = 1000 * 60 * 30
			}
		}
		dataSource_user {
			pooled = true
			dbCreate = "update"
			driverClassName = "com.mysql.jdbc.Driver"
			url = "jdbc:mysql://127.0.0.1/testuser"
			dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
			username = "root"
			password = "password"
			properties {
				maxActive = -1
				maxIdle = 5
				minIdle = 0
				initialSize = 1
				maxWait = 10000
				minEvictableIdleTimeMillis=1800000
				timeBetweenEvictionRunsMillis=1800000
				numTestsPerEvictionRun=3
				testOnBorrow=true
				testWhileIdle=true
				testOnReturn=false
				validationQuery="SELECT 1"
			}
		}
	}
}
