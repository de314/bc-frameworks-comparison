package com.bettercloud.perf.churchspringboot.db;

import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.ConnectionProviderFromUrl;
import com.github.davidmoten.rx.jdbc.Database;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RxJDBCConfig {

        @Value("${spring.datasource.username}")
        private String username;
        @Value("${spring.datasource.password}")
        private String password;
        @Value("${spring.datasource.url}")
        private String host;

    @Bean Database db() {
        ConnectionProvider connectionProvider
                = new ConnectionProviderFromUrl(
                host, username, password);
        return Database.from(connectionProvider);
    }

}
