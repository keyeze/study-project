package com.chan.study.concurrent.atomic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * 原子类试用。
 *
 * @author CtChan
 */
@Slf4j
public class AtomicTest {
    @Test
    public void usingAtomicInteger() throws Exception {
        final AtomicInteger value = new AtomicInteger(10);
        assertFalse(value.compareAndSet(1, 2));
        assertEquals(value.get(), 10);
        assertTrue(value.compareAndSet(10, 3));
        assertEquals(value.get(), 3);
        value.set(0);
        //
        assertEquals(value.incrementAndGet(), 1);
        assertEquals(value.getAndAdd(2), 1);
        assertEquals(value.getAndSet(5), 3);
        assertEquals(value.get(), 5);
        //
        final int threadSize = 10;
        Thread[] ts = new Thread[threadSize];
        for (int i = 0; i < threadSize; i++) {
            ts[i] = new Thread(value::incrementAndGet);
        }
        //
        for (Thread t : ts) {
            t.start();
        }
        for (Thread t : ts) {
            t.join();
        }
        //
        assertEquals(value.get(), 5 + threadSize);

    }
}

