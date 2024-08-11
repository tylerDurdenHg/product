package com.hg.product.utility.strings;

public class TextBlockOperation {
    public static void main(String[] args) {
        TextBlockOperation tbo = new TextBlockOperation();
        tbo.textBlock();
    }

    private void textBlock() {
        String text = """
                    Line 1 line 2 %s
                 third line ?  \s
                    Line 2 last name is:%s
                """.formatted("tyler", "durden");
        System.out.println(text);
    }
}
