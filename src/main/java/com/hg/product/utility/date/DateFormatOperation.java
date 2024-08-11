package com.hg.product.utility.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.TimeUnit;

public class DateFormatOperation {

    public static void main(String[] args) {
        DateFormatOperation dfo = new DateFormatOperation();
        dfo.formatISO();
    }

    private void formatISO() {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        YearMonth yearMonth = YearMonth.now();
        Period twoYears3M10D = Period.of(2, 3, 10);
        LocalDate next = localDate.plus(twoYears3M10D);
        
        String result = STR."localDate:\{localDate}, localDateTime: \{localDateTime}, zonedDateTime:\{zonedDateTime}";
        System.out.println("result = " + result);
        System.out.println("yearMonth = " + yearMonth);
        System.out.println("next = " + next + " " + twoYears3M10D);
        String formattedLocalDate = localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        System.out.println("formattedLocalDate = " + formattedLocalDate);

        DateTimeFormatter formatPattern = DateTimeFormatter.ofPattern("yy/ MMMM eee d h:m:s");
        String formattedByPattern = localDateTime.format(formatPattern);
        System.out.println("formattedByPattern = " + formattedByPattern);

    }
}
