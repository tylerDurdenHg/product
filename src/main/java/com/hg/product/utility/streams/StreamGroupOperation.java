package com.hg.product.utility.streams;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class StreamGroupOperation {

    public record Stock(String name, int quantity, BigDecimal buyPrice, BigDecimal sellPrice) {
    }

    public record Order(long orderId, String name, String product, LocalDate date, double amount) {
    }

    public record RVNL(String date, double price, long volume) {
    }

    public static void main(String[] args) {
        StreamGroupOperation sgo = new StreamGroupOperation();
        List<Stock> stocks = List.of(
                new Stock("computer", 3, BigDecimal.TEN, new BigDecimal("10")),
                new Stock("notebook", 9, new BigDecimal(8), new BigDecimal("10")),
                new Stock("lamb", 3, new BigDecimal(7), new BigDecimal("10")),
                new Stock("notebook", 9, new BigDecimal(8), new BigDecimal("10")),
                new Stock("desk", 2, new BigDecimal(5), new BigDecimal("10"))
        );

        // max profited stock
        Stock maxProfitedStock = sgo.maxProfit(stocks);
        System.out.println("maxProfitedStock = " + maxProfitedStock + " profit is:" + sgo.profit(maxProfitedStock));

        //max selling product
        String maxSelledProduct = sgo.maxSellingProduct(stocks);
        System.out.println("maxSelledProduct = " + maxSelledProduct);

        // sum of amount for each day
        List<Order> orders = List.of(
                new Order(1, "tyler", "desktop", LocalDate.of(2024, 8, 1), 60.1),
                new Order(1, "david", "mouse", LocalDate.of(2024, 8, 1), 50.1),
                new Order(1, "angelica", "desk", LocalDate.of(2024, 8, 2), 60.1),
                new Order(1, "andriy", "notebook", LocalDate.of(2024, 9, 1), 60.1)
        );
        Map<LocalDate, Double> sumOfEachDay = sgo.sumAmountOfDay(orders);
        System.out.println("sumOfEachDay = " + sumOfEachDay);

        // max volume in which day in with Rvnl ?
        List<RVNL> rvnls = List.of(
                new RVNL("July 23, 2024", 10.3D, 12345),
                new RVNL("July 13, 2024", 10.3D, 1234512),
                new RVNL("July 3, 2024", 10.3D, 123457),
                new RVNL("July 8 2024", 10.3D, 123456)
        );
        RVNL maxVolumedRVNL = sgo.maxVolumeWhichDay(rvnls);
        System.out.println("max volumed date is = " + maxVolumedRVNL.date);
    }

    private String maxSellingProduct(List<Stock> stocks) {

        Map.Entry<String, Long> foundMaxSelled = stocks.stream()
                .collect(Collectors.groupingBy(Stock::name, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElseThrow(RuntimeException::new);

        System.out.println("foundMaxSelled = " + foundMaxSelled.getKey() + ", " + foundMaxSelled.getValue() + " times selled");


        List<String> selledProducts = stocks.stream()
                .map(Stock::name)
                .toList();
        return selledProducts.stream()
                .max(Comparator.comparing(s -> Collections.frequency(selledProducts, s)))
                .orElseThrow(RuntimeException::new);
    }

    private RVNL maxVolumeWhichDay(List<RVNL> rvnls) {
        return rvnls.stream()
                .max(Comparator.comparing(RVNL::volume))
                .orElseThrow(() -> new RuntimeException("not found rvls"));
    }

    private Map<LocalDate, Double> sumAmountOfDay(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::date, Collectors.summingDouble(Order::amount)));
    }

    private Stock maxProfit(List<Stock> stocks) {
        return stocks.stream()
                .peek(s -> {
                    BigDecimal a = s.sellPrice.subtract(s.buyPrice);
                    BigDecimal b = a.multiply(new BigDecimal(s.quantity));
                })
                .max(Comparator.comparing(this::profit))
//                .orElseThrow(RuntimeException::new);
                .orElseThrow(() -> new RuntimeException("not found max value"));
    }

    private BigDecimal profit(Stock s) {
        BigDecimal diff = s.sellPrice.subtract(s.buyPrice);
        return diff.multiply(new BigDecimal("" + s.quantity));
    }

}