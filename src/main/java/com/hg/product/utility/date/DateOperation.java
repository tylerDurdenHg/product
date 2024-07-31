package com.hg.product.utility.date;

import org.springframework.util.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DateOperation {

    public record Project(long id, String name, LocalDate startDate, LocalDate endDate) {

    }

    public static void main(String[] args) {
        DateOperation dop = new DateOperation();

        // find a specific date with UTC
        int daysLater = 14;
        LocalDateTime timeOfThere = dop.nextDay(daysLater);
        System.out.println("timeOfThere = " + timeOfThere);

        // convert String to date
        List<String> stDates = List.of(
                "2024-12-01",
                "2023-5-22",
                "2024-12-1",
                "2024-8-11"
        );
        List<LocalDate> localDates = dop.convertToDate(stDates);
        System.out.println("localDates = " + localDates);

        // find the maximum time for the project
        List<Project> projects = List.of(
                new Project(1, "web", LocalDate.of(2020, 1, 2), LocalDate.of(2023, 1, 2)),
                new Project(2, "web", LocalDate.of(2020, 1, 2), LocalDate.of(2024, 1, 2)),
                new Project(3, "web", LocalDate.of(2020, 1, 2), null)
        );
        Project maxProjectTime = dop.findMaxProjectTime(projects);
        System.out.println("maxProjectTime = " + maxProjectTime);

        // find the execution time of the method
        long executionTimeOfMethod = dop.findExecutionTimeOfMethod();
        System.out.println("executionTimeOfMethod = " + executionTimeOfMethod);
    }

    private long findExecutionTimeOfMethod() {
        long start = Instant.now().toEpochMilli();
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long end = Instant.now().toEpochMilli();
        return end - start;
    }

    private Project findMaxProjectTime(List<Project> projects) {
        Comparator<Project> comparator = (a, b) -> {
            LocalDate aEndOfProject = a.endDate() == null ? LocalDate.now() : a.endDate();
            LocalDate bEndOfProject = b.endDate() == null ? LocalDate.now() : b.endDate();
            Long aTime = ChronoUnit.DAYS.between(a.startDate(), aEndOfProject);
            Long bTime = ChronoUnit.DAYS.between(b.startDate(), bEndOfProject);
            return aTime.compareTo(bTime);

        };
        return projects.stream()
                .max(comparator)
                .orElseThrow(RuntimeException::new);
    }

    private List<LocalDate> convertToDate(List<String> stDates) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return stDates.stream()
                    .map(s -> {
                        String[] split = s.split("-");
                        String month = leadingZero(split[1]);
                        String day = leadingZero(split[2]);
                        return split[0] + "-" + month + "-" + day;
                    })
                    .map(s -> LocalDate.parse(s, formatter))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String leadingZero(String st) {
        if (st.length() == 1)
            return "0" + st;
        return st;
    }

    private LocalDateTime nextDay(int daysLater) {
        return LocalDateTime.now(ZoneId.of("UTC")).plusDays(daysLater);
    }

}
