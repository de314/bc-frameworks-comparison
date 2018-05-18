package com.bettercloud.perf.churchspringboot.web;

import com.bettercloud.perf.churchspringboot.Entry;
import com.bettercloud.perf.churchspringboot.Fibonacci;
import com.bettercloud.perf.churchspringboot.db.MessageGroup;
import com.bettercloud.perf.churchspringboot.db.MessageRepository;
import com.github.davidmoten.rx.jdbc.Database;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Map;

@RestController
public class PerformanceController {
    private final Fibonacci fibonacci;
    private final MessageRepository messageRepository;
    private final SecureRandom secureRandom = new SecureRandom();
    private static final String[] MESSAGES = new String[]{
            "Hello, World!",
            "foobar",
            "fizzbuzz",
            "That's not a bug..."
    };

    private final Database db;
    public PerformanceController(Fibonacci fibonacci, MessageRepository messageRepository, Database db) {
        this.fibonacci = fibonacci;
        this.messageRepository = messageRepository;
        this.db = db;
    }

    @GetMapping("noop")
    public Mono<String> noop() {
        return Mono.just("Hello, World");
    }

    @GetMapping("cpu")
    public Mono<String> cpu() {
        final int input = secureRandom.nextInt(8) + 30;
        return fibonacci.fib(input)
                .map(fib -> String.format("fib(%d) = %d", input, fib));
    }

    @GetMapping("sleep")
    public Mono<String> sleep() {
        return Mono.just("HEY, WAKE UP!")
                .delayElement(Duration.ofMillis(500L));
    }

    @GetMapping("write")
    public Mono<Entry> write() {
        final String message = randomMessage();
        return messageRepository.save(message);
    }

    @GetMapping("read")
    public Mono<Map<String, Long>> read() {
        return messageRepository.getAggregate()
                .collectMap(MessageGroup::getMessage, MessageGroup::getCount);
    }

    private String randomMessage() {
        return MESSAGES[(secureRandom.nextInt(MESSAGES.length))];
    }



}
