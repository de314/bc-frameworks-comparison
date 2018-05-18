package com.bettercloud.perf.churchspringboot.db;
//
//import com.github.mauricio.async.db.Configuration;
//import com.github.mauricio.async.db.Configuration$;
//import com.github.mauricio.async.db.Connection;
//import com.github.mauricio.async.db.mysql.MySQLConnection;
//import com.github.mauricio.async.db.mysql.MySQLConnection$;
//import com.github.mauricio.async.db.mysql.pool.MySQLConnectionFactory;
//import com.github.mauricio.async.db.mysql.util.CharsetMapper;
//import com.github.mauricio.async.db.mysql.util.URLParser;
//import com.github.mauricio.async.db.pool.ConnectionPool;
//import com.github.mauricio.async.db.pool.ObjectFactory;
//import com.github.mauricio.async.db.pool.PoolConfiguration;
//import com.github.mauricio.async.db.util.ExecutorServiceUtils;
//import io.netty.channel.DefaultEventLoop;
//import io.netty.channel.EventLoopGroup;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import scala.collection.immutable.Map;
//import scala.concurrent.ExecutionContext;
//
//import java.net.URI;
//import java.nio.charset.Charset;

//@org.springframework.context.annotation.Configuration
public class DbConfig {
//
//    @Value("${spring.datasource.username}")
//    private String username;
//    @Value("${spring.datasource.password}")
//    private String password;
//    @Value("${spring.datasource.url}")
//    private String host;
//
//    @Bean
//    Connection dbConnection() {
//        Configuration configuration = URLParser.parse(host + "?user=" + username + "&password=" + password, Charset.defaultCharset());
//        ConnectionPool cp = new ConnectionPool(new MySQLConnectionFactory(configuration), PoolConfiguration.Default(), ExecutorServiceUtils.CachedExecutionContext());
//        return cp;
//
//    }
}
