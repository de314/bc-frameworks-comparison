package com.bettercloud.perf.churchspringboot.db;

import com.bettercloud.perf.churchspringboot.Entry;
import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.ConnectionProviderFromUrl;
import com.github.davidmoten.rx.jdbc.Database;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public class RxJDBCConfigTest {
    private RxJDBCConfig rxJDBCConfig;

    private MessageRepository messageRepository;

    @Before
    public void setUp() throws Exception {
        ConnectionProvider connectionProvider = new ConnectionProviderFromUrl("jdbc:mysql://127.0.0.1:3306/test", "root", "b3ttercl0ud");
        Database database = Database.from(connectionProvider);
        messageRepository = new MessageRepository(database);

    }

    @Test
    public void testDb() throws Exception {

        Entry entry = messageRepository.save("message").block();
        System.out.println("Done:  " + entry);
    }

    @Test
    public void read() throws Exception {
        List<MessageGroup> m = messageRepository.getAggregate()
                .collectList()
                .block();


        m.forEach(me -> System.out.println(me.getCount()));
        System.out.println("Done" + m);
    }
}