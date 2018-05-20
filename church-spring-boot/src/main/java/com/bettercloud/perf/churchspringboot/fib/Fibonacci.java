package com.bettercloud.perf.churchspringboot.fib;

import reactor.core.publisher.Mono;

public interface Fibonacci {
    Mono<Long> fib(long position);
}
