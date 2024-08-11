package com.hg.product.utility.polymorphism;

public class CompileTimeOperation {

    public static void main(String[] args) {
        CompileTimeOperation cto = new CompileTimeOperation();
        cto.compileTime();
    }

    private void compileTime() {
        setNumber((Integer) 123);
    }

    public static void setNumber(Integer number) {
        System.out.println("Set number of type Integer");
    }

    public static void setNumber(short number) {
        System.out.println("Set number of type short");
    }
    public static void setNumber(int number) {
        System.out.println("Set number of type int");
    }

    public static void setNumber(long number) {
        System.out.println("Set number of type long");
    }

    public static void setNumber(Object number) {
        System.out.println("Set number of type Object");
    }
}
