package com.example.demo;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.Router;

import java.util.concurrent.ExecutionException;

import static com.example.demo.DataConfig.clearExistingData;
import static com.example.demo.DataConfig.createJdbcClient;
import static com.example.demo.DataConfig.generateFibonocci;

public class Server {

    public static void main(final String[] args) throws ExecutionException, InterruptedException {
        final Vertx vertx = Vertx.vertx();
        final JDBCClient jdbcClient = createJdbcClient(vertx);

        clearExistingData(jdbcClient);

        final HttpServer httpServer = vertx.createHttpServer();
        final Router router = Router.router(vertx);

        router.route("/noop").handler(ctx -> {
            ctx.response()
                .putHeader("content-type", "text/plain")
                .end("Hello world");
        });

        router.route("/cpu").blockingHandler(ctx -> {
            final int randomNum = (int) (Math.floor(Math.random() * 8) + 30);
            final long fibonocci = generateFibonocci(randomNum);
            ctx.response()
                .putHeader("content-type", "text/plain")
                .end(new StringBuilder("fib(").append(randomNum).append(") = ").append(fibonocci).toString());
        });

        httpServer.requestHandler(router::accept).listen(8080);
        System.out.println("HTTP server started on port 8080");
    }


}
