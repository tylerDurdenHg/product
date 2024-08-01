package com.hg.product.utility.exceptions;

import java.io.IOException;

public class ExceptionOperation {

    public static class MyGenericException extends RuntimeException {
        final  String code;
        final String message;

        public MyGenericException(String code, String message, Throwable cause) {
            super(message, cause);
            this.code = code;
            this.message = message;
        }
        @Override
        public String getMessage() {
            return message;
        }

        public String getCode() {
            return code;
        }
    }


    public static void main(String[] args) {
        ExceptionOperation eo = new ExceptionOperation();

        // is exception swallowed ?
        eo.isExceptionSwallowed();
    }

    private void isExceptionSwallowed() {

        try {
//            int a = 10 / 0;
            throw new IOException("my io exception");
        } catch (Exception e) {
            String s = null;
            try {
                s.length();
            } catch (Exception ex) {
                System.out.println("null pointer exception");
//                throw ex;
            }
//            System.out.println("Exception" + e);
//            System.out.println("Message" + e.getMessage());
//            System.out.println("Cause" + e.getCause());
//            System.out.println("--------------");
//            throw new RuntimeException(e);
            throw new MyGenericException("01120", "Case zero", e);
        }
    }
}
