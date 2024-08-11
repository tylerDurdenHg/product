package com.hg.product.utility.threads;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class VirtualThreadOperation {

    public static final int MAX_NUMBER = 10_000;
    private final AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        VirtualThreadOperation vto = new VirtualThreadOperation();
        vto.createPlatformThread();
        vto.createVirtualThread();
        vto.createExecutorCachedPlatformThread();
        vto.createExecutorFixedPlatformThread();
    }

    private void createExecutorFixedPlatformThread() {
        Instant start = Instant.now();
        int fixedThreadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(fixedThreadCount);
        CountDownLatch latch = new CountDownLatch(fixedThreadCount);
        Runnable r = () -> {
            IntStream.rangeClosed(1, MAX_NUMBER)
                            .forEach(s -> {
                                count.getAndAdd(1);
                            });
            latch.countDown();
        };
        IntStream.rangeClosed(1, fixedThreadCount)
                .forEach(s -> {
                    executor.submit(r);
                });
        try {
            latch.await();
            executor.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        calculateDurationAndPrint("Executor Fixed", start);
    }

    private void createExecutorCachedPlatformThread() {
        Instant start = Instant.now();
        AtomicReference<Future<Integer>> future = new AtomicReference<>();
        ExecutorService executor = Executors.newCachedThreadPool();
        IntStream.rangeClosed(1, MAX_NUMBER)
                .forEach(s -> {
                    future.set(executor.submit(() -> count.getAndAdd(1)));
                });
        while (!future.get().isDone()) {}
        executor.shutdown();
        calculateDurationAndPrint("Executor cached", start);
    }

    private void createVirtualThread() {
        Instant start = Instant.now();
        IntStream.rangeClosed(1, MAX_NUMBER)
                .forEach(s -> {
                    try {
                        Thread.ofVirtual().start(() -> count.getAndAdd(1)).join();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        calculateDurationAndPrint("Virtual Thread", start);
    }

    private void createPlatformThread() {
        Instant start = Instant.now();
        IntStream.rangeClosed(1, MAX_NUMBER)
                .forEach(s -> {
                    try {
                        Thread.ofPlatform().start(() -> count.getAndAdd(1)).join();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        calculateDurationAndPrint("Platform", start);
    }

    private void calculateDurationAndPrint(String method, Instant start) {
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println(method + " duration = " + duration + " atomic =" + count);
    }
}
