package com.hg.product.utility.abstracts;

public class AbstractOperation {

    public static void main(String[] args) {
        AbstractOperation ao = new AbstractOperation();
        Reader reader = new PDFReader();
        reader.print();
        String max = reader.getMAX();
        System.out.println("max = " + max);

    }
}

interface A {
    void anotherPrint();
}

abstract class Reader implements  A {
    public Reader() {
        System.out.println("constructor is called");
    }

    abstract void print();
    abstract public void anotherPrint();

    public String fun() {
        return null;
    }

    private static final String MAX = "any";

    public final String getMAX() {
        return "not static";
    }


}

class PDFReader extends Reader {

    @Override
    void print() {
        System.out.println("pdf reader is printing");
    }

    @Override
    public void anotherPrint() {

    }
}