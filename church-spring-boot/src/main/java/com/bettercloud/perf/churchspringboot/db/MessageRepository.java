package com.bettercloud.perf.churchspringboot.db;

import com.bettercloud.perf.churchspringboot.Entry;
import com.github.davidmoten.rx.jdbc.Database;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rx.RxReactiveStreams;

@Service
public class MessageRepository {
    private final Database database;
    private static final String AGGRAGATE_QUERY = "SELECT message, COUNT(message) as 'count' FROM t1 GROUP BY message";
    private static final String INSERT_QUERY = "INSERT INTO t1 (message) VALUES (?)";


    public MessageRepository(Database database) {
        this.database = database;
    }

    public Mono<Entry> save(String message) {

        return Mono.from(RxReactiveStreams.toPublisher(database.update(INSERT_QUERY)
                .parameter(message)
                .returnGeneratedKeys()
                .getAs(Long.class)
                .toSingle()))
                .map(key -> new Entry(key, message));
    }

    public Flux<MessageGroup> getAggregate() {

        return Flux.from(RxReactiveStreams.toPublisher(database.select(AGGRAGATE_QUERY)
                .autoMap(MessageGroup.class)));


    }

}
