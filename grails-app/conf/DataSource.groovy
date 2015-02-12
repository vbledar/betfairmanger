dataSource {
    pooled = true
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
//    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
    flush.mode = 'manual' // OSIV session flush mode outside of transactional context
}

// environment specific settings
environments {
    development {
        dataSource {
//            dbCreate = "update"
            driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
            username = "tipster11"
            password = "%^NHkiju>lki!"
            url = "jdbc:sqlserver://k3nqcihx0j.database.windows.net:1433;database=Tipster11DB;user=tipster11@k3nqcihx0j;password=%^NHkiju>lki!;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"
            logSql = true
        }
    }
    test {
        dataSource {
//            dbCreate = "update"
            driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
            username = "tipster11"
            password = "%^NHkiju>lki!"
            url = "jdbc:sqlserver://k3nqcihx0j.database.windows.net:1433;database=Tipster11DB;user=tipster11@k3nqcihx0j;password=%^NHkiju>lki!;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"
            logSql = true
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
            properties {
               // See http://grails.org/doc/latest/guide/conf.html#dataSource for documentation
               jmxEnabled = true
               initialSize = 5
               maxActive = 50
               minIdle = 5
               maxIdle = 25
               maxWait = 10000
               maxAge = 10 * 60000
               timeBetweenEvictionRunsMillis = 5000
               minEvictableIdleTimeMillis = 60000
               validationQuery = "SELECT 1"
               validationQueryTimeout = 3
               validationInterval = 15000
               testOnBorrow = true
               testWhileIdle = true
               testOnReturn = false
               jdbcInterceptors = "ConnectionState"
               defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
            }
        }
    }
}
