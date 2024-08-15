package com.hg.product.utility.streams;

import org.springframework.data.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamExceptionOperation {

    private static final List<String> CITIES = List.of("istanbul", "samsun", "tokat", "izmir");

    public static void main(String[] args) {
        StreamExceptionOperation seo = new StreamExceptionOperation();

        List<String> uncheckedCities = seo.unCheckedException(CITIES);
        System.out.println("uncheckedCities = " + uncheckedCities);

        List<String> checkedCities = seo.checkedException(CITIES);
        System.out.println("checkedCities = " + checkedCities);

        List<Integer> checkedWorkAroundCities = seo.checkedExceptionWorkAround1(CITIES);
        System.out.println("checkedWorkAroundCities = " + checkedWorkAroundCities);

        List<Either> eithersCities = seo.checkedExceptionWithEither(CITIES);
        System.out.println("eithersCities = " + eithersCities);
    }

    private List<Either> checkedExceptionWithEither(List<String> cities) {
        List<Either> eitherList = cities.stream()
                .map(wrapWithEither(this::throwForEither))
                .toList();
        System.out.println("eitherList = " + eitherList);
        Map<Boolean, List<Either>> eitherListToPosition = eitherList.stream()
                .collect(Collectors.partitioningBy(Either::isRight));
        System.out.println("Either Rights = " + eitherListToPosition.get(Boolean.TRUE));
        System.out.println("Either Exceptions = " + eitherListToPosition.get(Boolean.FALSE));
        return eitherList;
    }

    private String throwForEither(String st) throws IOException {
        if (st.length() > 5) {
            throw new IOException("length bigger than 6");
        }
        return st;
    }

    private <T, R> Function<T, Either> wrapWithEither(CheckedExceptionWrapped<T, R> fun) {
        return x -> {
            try {
                return Either.Right(fun.wrap(x));
            } catch (Exception e) {
                return Either.Left(Pair.of(e, x));
            }
        };
    }

    private List<Integer> checkedExceptionWorkAround1(List<String> cities) {
        List<String> result = cities.stream()
                .map(wrapCheckedException(this::throwCheckedException))
                .toList();
        System.out.println("wrapped for String = " + result);

        List<Integer> numbers = IntStream.rangeClosed(1, 10).boxed().toList();
        return numbers.stream()
                .map(wrapCheckedException(s -> s / 0))
                .toList();
    }

    private List<String> unCheckedException(List<String> cities) {
        List<String> result = null;
        try {
            result = cities.stream()
                    .map(this::throwUncheckedExption)
                    .filter(Objects::isNull)
                    .map(s -> "is null")
                    .toList();
        } catch (Exception e) {
//            throw new RuntimeException(e);
            System.out.println("unchecked exception e = " + e);
        }
        return result;
    }

    private String throwUncheckedExption(String st) {
        throw new IllegalArgumentException("illegal argument");
    }

    private List<String> checkedException(List<String> cities) {
        List<String> listForCheckedCities;
        try {
            listForCheckedCities = cities.stream()
                    .map(name -> {
                        try {
                            return throwCheckedException(name);
                        } catch (IOException e) {
                            return null; // or throw new RuntimeException("xx");
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (Exception e) {
            throw e;
        }
        return listForCheckedCities;
    }

    private String throwCheckedException(String st) throws IOException {
        throw new IOException("i didn't allow");
//        return "xxx";
    }


    private <T, R> Function<T, R> wrapCheckedException(CheckedExceptionWrapped<T, R> fun) {
        return x -> {
            try {
                return fun.wrap(x);
            } catch (Exception e) {
//                throw new RuntimeException(e);
                System.out.println("wrapped exception = " + e);
                return null;
            }
        };
    }
}

interface CheckedExceptionWrapped<T, R> {
    R wrap(T t) throws Exception;
}
