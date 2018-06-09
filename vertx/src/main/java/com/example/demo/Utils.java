package com.example.demo;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Utils {

    private static final String[] MESSAGES = new String[] {
        "Hello, World!",
        "foobar",
        "fizzbuzz",
        "That's not a bug..."
    };

    public static JDBCClient createJdbcClient(final Vertx vertx) {
        return JDBCClient.createShared(vertx, new JsonObject()
            .put("url", "jdbc:mysql://127.0.0.1:3306/test")  // TODO: Move to external properties
            .put("driver_class", "com.mysql.cj.jdbc.Driver")
            .put("user", "root")
            .put("password", "root")
            .put("initial_pool_size", 25)
            .put("min_pool_size", 25)
            .put("max_pool_size", 25)
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
