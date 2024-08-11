package com.hg.product.utility.strings;

import org.springframework.data.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        String sentence = "java is very well know in JAVA island";
        Pattern pattern = Pattern.compile("java", Pattern.CASE_INSENSITIVE);
        String matchesToRegex = so.regexMatcher(sentence, pattern);
        System.out.println("matchesToRegex = " + matchesToRegex);

    }

    private String regexMatcher(String sentence, Pattern pattern) {
        Matcher matcher = pattern.matcher(sentence);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            System.out.println("matcher.group() = " + matcher.group());
            sb.append(Pair.of(matcher.start(), matcher.end())).append(" ");
        }
        return sb.toString();
    }

    private String removeSpecialChars(String withSpecialChars) {
        String regex = "[^a-zA-Z]";
        return withSpecialChars.replaceAll(regex, "");
    }

    private List<Character> charArrayStream(String st) {
        return st.chars()
                .mapToObj(s -> (char) s)
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
        return String.join(",", cars);
    }
}
