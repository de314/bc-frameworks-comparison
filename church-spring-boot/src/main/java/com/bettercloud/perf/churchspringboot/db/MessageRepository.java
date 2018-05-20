package com.bettercloud.perf.churchspringboot.db;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    @Query("SELECT new com.bettercloud.perf.churchspringboot.db.MessageGroup(message, COUNT(message)) FROM t1 GROUP BY message")
    List<MessageGroup> groupByMessage();
}
