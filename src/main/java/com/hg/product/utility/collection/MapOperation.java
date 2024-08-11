package com.hg.product.utility.collection;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class MapOperation {

    public static void main(String[] args) {
        MapOperation co = new MapOperation();
        Map<Long, String> map = new HashMap<>();
        map.put(1L, "tyler");
        map.put(2L, "micheal");
        map.put(3L, "micheal");

        co.checkHiearchySequentialCollection();
        co.forEachAndOrdered();
        co.mapComparingByKeyValue(map);
        co.mapGetOrDefault(map);
        co.mapReplaceAll(map);
        co.mapReplace(map);
        co.mapRemove(map);
        co.mapPutIfAbsent(map);
        co.mapComputeIfAbsent(map);
        co.mapCompute(map);
        co.mapMerge(map);
        co.mapComputeIfPresent(map);

    }


    private void mapMerge(Map<Long, String> map) {
        map.merge(5L, " add", String::concat);
        map.merge(35L, "any", String::concat);
        System.out.println("map = " + map);
    }

    private void mapComputeIfPresent(Map<Long, String> map) {
        map.computeIfPresent(11L, (k, v) -> v == null ? "11 val" : v.concat("!"));
        map.computeIfPresent(1L, (k, v) -> v == null ? "11 val" : v.concat("!"));
        System.out.println("map = " + map);
    }

    private void mapCompute(Map<Long, String> map) {
        map.compute(7L, (k, v) -> v == null ? "anything" : v.concat("#"));
        map.compute(1L, (k, v) -> v == null ? "anything" : v.concat("#"));
        System.out.println("map = " + map);
    }

    private void mapComputeIfAbsent(Map<Long, String> map) {
        map.computeIfAbsent(6L, (k) -> "5's value is".toUpperCase());
        map.computeIfAbsent(1L, (k) -> "5's value is".toUpperCase());
        System.out.println("map = " + map);
    }

    private void mapRemove(Map<Long, String> map) {
        map.remove(444L);
        map.remove(5L);
        System.out.println("map = " + map);
    }

    private void mapReplace(Map<Long, String> map) {
        map.replace(1L, "Tyler", "tylerX");
        map.replace(2L, "mike", "micheal");
    }

    private void mapPutIfAbsent(Map<Long, String> map) {
        map.putIfAbsent(1L, "four");
        map.putIfAbsent(5L, "five");
        System.out.println("map = " + map);
    }

    private void mapReplaceAll(Map<Long, String> map) {
        BiFunction<Long, String, String> bifunction = (k, v) -> k != 1 ? v.replaceAll("m", "M") : v.replaceAll("t", "T");
        map.replaceAll(bifunction);
        System.out.println("map = " + map);
    }

    private void mapGetOrDefault(Map<Long, String> map) {
        String absent = map.getOrDefault(5L, "default");
        String exist = map.getOrDefault(1L, "default");
        System.out.println("absent = " + absent);
        System.out.println("exist = " + exist);
    }

    private void forEachAndOrdered() {
        ArrayList<String> list = new ArrayList<>();
        list.add("tyler");
        list.add("durden");
        list.add("mike");

        list.parallelStream().forEach(System.out::println);
        list.parallelStream().forEachOrdered(System.out::println);
    }

    private void mapComparingByKeyValue(Map<Long, String> map) {

        Map<String, Long> sortedMap = map.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (k, v) -> {
//                    throw new IllegalArgumentException("same key not allowed");
                    return k;
                }, LinkedHashMap::new));
        System.out.println("sortedMap = " + sortedMap);
    }

    private void checkHiearchySequentialCollection() {
        List<String> list = new ArrayList<>();
        list.add("start");
        list.add("start");
        list.addFirst("first");
        System.out.println("list.getFirst() = " + list.getFirst());
    }

    public void collect() {

    }
}
