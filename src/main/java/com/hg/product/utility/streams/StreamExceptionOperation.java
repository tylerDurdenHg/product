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

    public static void main(String[] args) {
        StreamExceptionOperation seo = new StreamExceptionOperation();
        seo.unCheckedException();
        seo.checkedException();
        seo.checkedExceptionWorkAround1();
        seo.checkedExceptionWithEither();
    }

    private void checkedExceptionWithEither() {
        List<String> cities = List.of("istanbul", "samsun", "tokat", "izmir");
        List<Either> eitherList = cities.stream()
                .map(wrapWithEither(this::throwForEither))
                .toList();
        System.out.println("eitherList = " + eitherList);
        Map<Boolean, List<Either>> eitherListToPosition = eitherList.stream()
                .collect(Collectors.partitioningBy(Either::isRight));
        System.out.println("Either Rights = " + eitherListToPosition.get(Boolean.TRUE));
        System.out.println("Either Exceptions = " + eitherListToPosition.get(Boolean.FALSE));

    }

    private String throwForEither(String st) throws IOException {
        if (st.length() > 5) {
            throw new IOException("length bigger than 6");
        }
        return st;
    }

    private <T, R> Function<T,Either> wrapWithEither(CheckedExceptionWrapped<T, R> fun) {
        return x -> {
            try {
                return Either.Right(fun.wrap(x));
            } catch (Exception e) {
                return Either.Left(Pair.of(e, x));
            }
        };
    }

    private void checkedExceptionWorkAround1() {
        List<String> cities = List.of("istanbul", "samsun", "tokat", "izmir");
        List<String> result = cities.stream()
                .map(wrapCheckedException(this::throwCheckedException))
                .toList();
        System.out.println("wrapped for String = " + result);

        List<Integer> numbers = IntStream.rangeClosed(1, 10).boxed().toList();
        List<Integer> numbersDivideZero = numbers.stream()
                .map(wrapCheckedException(s -> s / 0))
                .toList();
        System.out.println("numbersDivideZero = " + numbersDivideZero);
    }

    private void unCheckedException() {
        List<String> cities = List.of("istanbul", "samsun", "tokat", "izmir");
        List<String> result = null;
        try {
            result = cities.stream()
                    .map(s -> throwUncheckedExption(s))
                    .filter(Objects::isNull)
                    .map(s -> "is null")
                    .toList();
        } catch (Exception e) {
//            throw new RuntimeException(e);
            System.out.println("unchecked exception e = " + e);
        }
        System.out.println("result = " + result);
    }

    private String throwUncheckedExption(String st) {
        throw new IllegalArgumentException("illegal argument");
    }

    private void checkedException() {
        List<String> list = null;
        try {
            List<String> names = List.of("istanbul", "samsun", "tokat", "izmir");
            list = names.stream()
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
        System.out.println("list = " + list);
    }

    private String throwCheckedException(String st) throws IOException {
        throw new IOException("i didn't allow");
//        return "xxx";
    }



    private <T, R> Function<T,R> wrapCheckedException(CheckedExceptionWrapped<T, R> fun) {
        return  x -> {
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
    R wrap(T t) throws  Exception;
}
