package com.bettercloud.perf.churchspringboot.fib;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@Service
@Primary
public class DynamicFibonacci implements Fibonacci {
    private Flux<Long> fibGenerator = Flux.generate(() -> Tuples.of(1L, 1L),
            (state, sink) -> {
                sink.next(state.getT2());
                return Tuples.of(state.getT2(), state.getT1() + state.getT2());
            })
            .cast(Long.class);

    @Override
    public Mono<Long> fib(long position) {
        return fibGenerator.take(position).last() ;
    }
}
