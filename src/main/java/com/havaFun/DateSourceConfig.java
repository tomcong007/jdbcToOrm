package com.havaFun;
import com.mchange.v2.c3p0.DataSources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
@Configuration
public class DateSourceConfig {
    @Value("${fun.db.driver-name}")
    String driver_name;
    @Value("${fun.db.url}")
    String url;
    @Value("${fun.db.user}")
    String user;
    @Value("${fun.db.password}")
    String password;
    @Value("${fun.db.initialPoolSize}")
    String initialPoolSize;
    @Value("${fun.db.minPoolSize}")
    String minPoolSize;
    @Value("${fun.db.maxPoolSize}")
    String maxPoolSize;
    @Value("${fun.db.idleConnectionTestPeriod}")
    String idleConnectionTestPeriod;
    @Value("${fun.db.checkoutTimeout}")
    String checkoutTimeout;
    @Value("${fun.db.maxStatements}")
    String maxStatements;
    @Value("${fun.db.acquireIncrement}")
    String acquireIncrement;
    @Bean
    public DataSource getConnection() {
        Map<String, String> datacourceInitMap = new HashMap<String, String>(8);
        datacourceInitMap.put("driver-name", driver_name);
        datacourceInitMap.put("initialPoolSize", initialPoolSize);
        datacourceInitMap.put("minPoolSize", minPoolSize);
        datacourceInitMap.put("maxPoolSize", maxPoolSize);
        datacourceInitMap.put("idleConnectionTestPeriod", idleConnectionTestPeriod);
        datacourceInitMap.put("checkoutTimeout", checkoutTimeout);
        datacourceInitMap.put("maxStatements", maxStatements);
        datacourceInitMap.put("acquireIncrement", acquireIncrement);
        try {
            Class.forName(driver_name);
            DataSource unpooled = DataSources.unpooledDataSource(url, user, password);
            DataSource dataSource = DataSources.pooledDataSource(unpooled, datacourceInitMap);
            return dataSource;
        } catch (Exception e) {
            e.fillInStackTrace();
            return null;
        }
    }


}
