package com.bettercloud.perf.churchspringboot;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@Service
public class Fibonacci {
    private Flux<Long> fibGenerator = Flux.generate(() -> Tuples.of(1L, 1L),
            (state, sink) -> {
                sink.next(state.getT1());
                return Tuples.of(state.getT2(), state.getT1() + state.getT2());
            })
            .cast(Long.class);

    public Mono<Long> fib(long position) {
        return fibGenerator.take(position).last() ;
    }
}
