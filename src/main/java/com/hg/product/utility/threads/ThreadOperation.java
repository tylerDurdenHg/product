package com.hg.product.utility.threads;

import java.util.concurrent.*;

public class ThreadOperation {

    public static class Terminator implements  Runnable {
        private final MyThread myThread;

        public Terminator(MyThread myThread) {
            this.myThread = myThread;
        }

        public void run () {
            System.out.println("Terminator is waiting your request" );
            try {
                TimeUnit.SECONDS.sleep(5L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            myThread.terminate();
        }
    }

    public static class MyThread implements Callable {
        private volatile boolean flag = true;

        public void terminate() {
            System.out.println("Terminating ....");
            flag = false;
        }

        @Override
        public Object call() throws Exception {
            TimeUnit.SECONDS.sleep(1L);
            System.out.println("Thread after the sleeping");
            while (flag) {
                System.out.println("while is working");
            }
            return null;
        }
    }

    public static void main(String[] args) {
        ThreadOperation to = new ThreadOperation();
        MyThread myThread = new MyThread();
        Terminator terminator = new Terminator(myThread);
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            Future<Object> future = executorService.submit(myThread);
            Object o = future.get(10L, TimeUnit.SECONDS);
            executorService.submit(terminator);
            executorService.shutdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // stop thread
    }
}
