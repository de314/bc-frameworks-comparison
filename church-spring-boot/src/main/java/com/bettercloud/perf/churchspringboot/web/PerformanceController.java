package com.bettercloud.perf.churchspringboot.web;

import com.bettercloud.perf.churchspringboot.db.Message;
import com.bettercloud.perf.churchspringboot.db.MessageGroup;
import com.bettercloud.perf.churchspringboot.db.MessageRepository;
import com.bettercloud.perf.churchspringboot.fib.Fibonacci;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Map;

@RestController
public class PerformanceController {
    private final MessageRepository repo;
    private final Fibonacci fibonacci;
    private final MessageRepository messageRepository;
    private final SecureRandom secureRandom = new SecureRandom();
    private static final String[] MESSAGES = new String[]{
            "Hello, World!",
            "foobar",
            "fizzbuzz",
            "That's not a bug..."
    };

    public PerformanceController(MessageRepository repo, Fibonacci fibonacci, MessageRepository messageRepository) {
        this.repo = repo;
        this.fibonacci = fibonacci;
        this.messageRepository = messageRepository;
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
    public Mono<Message> write() {
        final String message = randomMessage();
        return Mono.fromCallable(() -> repo.save(new Message(null, message)))
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping("read")
    public Mono<Map<String, Long>> read() {
        return Mono.fromCallable(() -> repo.groupByMessage())
                .flatMapMany(Flux::fromIterable)
                .collectMap(MessageGroup::getMessage, MessageGroup::getCount)
                .subscribeOn(Schedulers.elastic());
    }

    private String randomMessage() {
        return MESSAGES[(secureRandom.nextInt(MESSAGES.length))];
    }


}
