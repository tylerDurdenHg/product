package com.hg.product.utility.strings;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringOperation {

    public static void main(String[] args) {
        StringOperation so = new StringOperation();
        List<String> cars = List.of("bmw", "togg", "ford", "mercedes");

        // convert list to string
        String joinedList = so.joinList(cars);
        System.out.println("joinedList = " + joinedList);

        // string char array to stream
        String di = "define insertion";
        List<Character> characterList = so.charArrayStream(di);
        System.out.println("characterList = " + characterList);

        // find if two strings are palindrome
        String st1 = "tyler";
        String st2 = "relyt";
        boolean isPalindrome = so.isPalindrome(st1, st2);
        System.out.println("isPalindrome = " + isPalindrome);

        // find if two strings are anagram
        String ana1 = "ozge";
        String ana2 = "goze";
        boolean isAnagram = so.isAnagram(ana1, ana2);
        System.out.println("isAnagram = " + isAnagram);

        // remove special chars in the string
        String withSpecialChars = "test13@abc.com ' , .";
        String removedSpecialChars = so.removeSpecialChars(withSpecialChars);
        System.out.println("removedSpecialChars = " + removedSpecialChars);

    }

    private String removeSpecialChars(String withSpecialChars) {
//        String regex = "[,.']";
        String regex = "[^a-zA-Z]";
        return withSpecialChars.replaceAll(regex, "");
    }

    private List<Character> charArrayStream(String st) {
        return st.chars()
                .mapToObj(s -> Character.valueOf((char) s))
                .toList();
    }

    private boolean isAnagram(String ana1, String ana2) {
        char[] arrayAna1 = ana1.toCharArray();
        char[] arrayAna2 = ana2.toCharArray();
        Arrays.sort(arrayAna1);
        Arrays.sort(arrayAna2);
        return Arrays.equals(arrayAna1, arrayAna2);
    }

    private boolean isPalindrome(String st1, String st2) {
        return st1.equalsIgnoreCase(new StringBuilder(st2).reverse().toString());
    }

    private String joinList(List<String> cars) {
        // or use stream join
//        String secondX = cars.stream()
//                .collect(Collectors.joining(", "));
//        System.out.println("secondX = " + secondX);
        return String.join(",", cars);
    }
}
