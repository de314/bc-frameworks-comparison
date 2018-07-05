package com.example.demo;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Utils {

    private static Logger LOGGER = LoggerFactory.getLogger(Utils.class);
    private static final String[] MESSAGES = new String[] {
        "Hello, World!",
        "foobar",
        "fizzbuzz",
        "That's not a bug..."
    };

    public static Properties loadProperties() {
        final Properties properties = new Properties();
        try {
            properties.load(Server.class.getClassLoader().getResourceAsStream("server.properties"));
        } catch (IOException e) {
            LOGGER.error("Could not load properties", e);
        }
        return properties;
    }

    public static JDBCClient createJdbcClient(final Vertx vertx, final Properties properties) {
        return JDBCClient.createShared(vertx, new JsonObject()
            .put("url", properties.getProperty("jdbc.url"))
            .put("driver_class", properties.getProperty("jdbc.driver_class"))
            .put("user", properties.getProperty("jdbc.user"))
            .put("password", properties.getProperty("jdbc.password"))
            .put("initial_pool_size", Integer.parseInt(properties.getProperty("jdbc.initial_pool_size")))
            .put("min_pool_size", Integer.parseInt(properties.getProperty("jdbc.min_pool_size")))
            .put("max_pool_size", Integer.parseInt(properties.getProperty("jdbc.max_pool_size")))
        );
    }

    public static void clearExistingData(final JDBCClient jdbcClient) throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> existingDataCleared = new CompletableFuture<>();
        jdbcClient.getConnection(connEvent -> {
            if (connEvent.failed()) throw new RuntimeException(connEvent.cause());
            final SQLConnection connection = connEvent.result();
            connection.execute("DELETE FROM t1", exeEvent -> {
                if (exeEvent.failed()) {
                    throw new RuntimeException(exeEvent.cause());
                }
                System.out.println("Existing data cleared");
                existingDataCleared.complete(null);
            });
        });
        existingDataCleared.get();
    }

    public static long generateFibonocci(final int i) {
        return i <= 1
            ? 1
            : generateFibonocci(i - 1) + generateFibonocci(i - 2);
    }

    public static String generateRandomMessage() {
        return MESSAGES[(int) (Math.random() * MESSAGES.length)];
    }

}
