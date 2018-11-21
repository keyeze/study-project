package com.chan.study;


import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureTest {
    private ExecutorService executor = Executors.newFixedThreadPool(5);

    @Test
    public void test() throws ExecutionException, InterruptedException {
        List<CompletableFuture<Map<Integer, Integer>>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futures.add(buildTestMap());
        }
        Map<Integer, Integer> result = new HashMap<>();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).get();
        for (CompletableFuture<Map<Integer, Integer>> future : futures) {
            Map<Integer, Integer> item = future.get();
            item.forEach((key, value) -> result.put(key, result.getOrDefault(key, 0) + value));
        }
        System.out.println("result->" + result);

    }

    private CompletableFuture<Map<Integer, Integer>> buildTestMap() {
        return CompletableFuture.supplyAsync(() -> {
            Random random = new Random();
            Map<Integer, Integer> map = new HashMap<>(50);
            int total = random.nextInt(70) + 70;
            for (int i = 0; i < total; i++) {
                Integer key = random.nextInt(50);
                map.put(key, map.getOrDefault(key, 0) + 1);
            }
            System.out.println("item->" + map);
            return map;
        });
    }
}
