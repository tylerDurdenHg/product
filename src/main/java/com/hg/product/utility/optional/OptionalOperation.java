package com.hg.product.utility.optional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class OptionalOperation {

    public record Address(String street) {
        public String myAddress() {
            return "Uskudar";
        }
    }

    public record User(long id, String name) {

        public static Optional<User> findById(int id) {
            return Optional.ofNullable(users.get(id));
        }

        public String nullName() {
            return null;
        }

        public Optional<Address> getAddress() {
            return Optional.of(new Address("kadikoy street"));
        }
    }

    private static final List<User> users = List.of(
            new User(1L, "tyler"),
            new User(2L, "michael")
    );

    private static final List<Optional<User>> optList = List.of(
            Optional.of(new User(22L, "mike")),
            Optional.of(new User(33L, "nike"))
    );

    public static void main(String[] args) {
        OptionalOperation oo = new OptionalOperation();
        oo.map();
        oo.flatMap();
        oo.or();
        oo.orElse();
        oo.orElseGet();
        oo.orElseThrow();
        oo.inStream();
    }

    private void inStream() {
        List<Long> ids = optList.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(User::id)
                .toList();
        System.out.println("ids = " + ids);
    }

    private void or() {
        String opt = "sera";

        Optional<String> or = Optional.of(opt)
                .or(() -> Optional.of(String.valueOf(100)));
        System.out.println("or = " + or);

    }

    private void orElseThrow() {
        String opt = "sera";
        String orElseThrow = Optional.of(opt)
                .orElseThrow(RuntimeException::new);
        System.out.println("orElseThrow = " + orElseThrow);
    }

    private void orElseGet() {
        String opt = "sera";
        List<String> supplierList = IntStream.rangeClosed(1, 10)
                .mapToObj(s -> Optional.of(opt)
                        .orElseGet(this::handleSupplier)).toList();
        System.out.println("supplierList = " + supplierList);
    }

    private void orElse() {
        User user = User.findById(1)
                .orElse(new User(99, "jack"));
        System.out.println("user = " + user);
    }

    private void flatMap() {
        User.findById(1)
                .flatMap(User::getAddress)
                .map(Address::myAddress)
                .map(String::toUpperCase)
                .ifPresentOrElse(this::handle, () -> {
                    throw new RuntimeException("asdf");
                });
    }

    private void map() {
        String userName = User.findById(0)
                .map(User::nullName)
                .filter(s -> s.length() > 2)
                .map(String::toUpperCase)
                .orElse("xx");
        System.out.println("userName = " + userName);
    }

    private void handle(String streetName) {
        System.out.println("handled streetName = " + streetName);
    }

    private String handleSupplier() {
        System.out.println(" I am working");
        return "this is supplier";
    }

}
