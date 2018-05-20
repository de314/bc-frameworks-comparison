package com.bettercloud.perf.churchspringboot;

import com.bettercloud.perf.churchspringboot.fib.DynamicFibonacci;
import com.bettercloud.perf.churchspringboot.fib.Fibonacci;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FibonacciTest {
    private Fibonacci fibonacci;

    @Before
    public void setUp() throws Exception {
        fibonacci = new DynamicFibonacci();
    }

    @Test
    public void fibTest() throws Exception {
        assertEquals(7540113804746346429L, fibonacci.fib(91).block().longValue());
        assertEquals(1, fibonacci.fib(1).block().intValue());
        assertEquals(1, fibonacci.fib(1).block().intValue());
        assertEquals(2, fibonacci.fib(2).block().intValue());
        assertEquals(3, fibonacci.fib(3).block().intValue());
        assertEquals(5, fibonacci.fib(4).block().intValue());
        assertEquals(8, fibonacci.fib(5).block().intValue());
        assertEquals(13, fibonacci.fib(6).block().intValue());
        assertEquals(21, fibonacci.fib(7).block().intValue());
        assertEquals(34, fibonacci.fib(8).block().intValue());
        assertEquals(267914296L, fibonacci.fib(41).block().intValue());
        assertEquals(55, fibonacci.fib(9).block().intValue());
        assertEquals(89, fibonacci.fib(10).block().intValue());
        assertEquals(144, fibonacci.fib(11).block().intValue());
    }
}