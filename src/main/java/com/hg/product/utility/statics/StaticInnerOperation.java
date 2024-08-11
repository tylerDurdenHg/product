package com.hg.product.utility.statics;

public class StaticInnerOperation {

    private long id;
    private String name = "tyler";

    private static long countThread= 10;

    public StaticInnerOperation() {
        countThread = countThread + 1;
    }

    private static class  MyStatic {
        int myId = 1;
        private int getMyId () {
            return myId;
        }
    }
    private class MyInner {
        private String getMyInnerString () {
            return name + " #### " ;
        }
    }

    public static void main(String[] args) {
        MyStatic myStatic = new MyStatic();
        int myId = myStatic.getMyId();
        System.out.println("myId = " + myId);

        MyInner myInner = new StaticInnerOperation(). new MyInner();
        String myInnerString = myInner.getMyInnerString();
        System.out.println("myInnerString = " + myInnerString);

        StaticInnerOperation counter1 = new StaticInnerOperation();
        StaticInnerOperation counter2 = new StaticInnerOperation();
        System.out.println("countThread = " + countThread);
    }
}
