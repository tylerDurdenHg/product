package com.hg.product.utility.records;

import java.util.List;
import java.util.Objects;

import static com.hg.product.utility.records.StringUtils.*;

public class RecordOperation {
    private record Account(int id, String name, String department) {
        public Account {
            if (isNullOrEmpty(name)) {
                throw new RuntimeException("name cannot be null or blank");
            }
            if (isNullOrEmpty(department)) {
                throw new RuntimeException("department cannot be null or empty");
            }
        }

        public Account(String name, String department) {
            this(111, name, department);
        }
    }

    public static void main(String[] args) {
        RecordOperation ro = new RecordOperation();
        ro.checkExceptionInRecord();
    }

    private void checkExceptionInRecord() {
        Account account = new Account(1, "ted", "IT");
        System.out.println("account = " + account);

        Account account2 = new Account("barney", "HR");
        System.out.println("account2 = " + account2);

    }

}


class StringUtils {
    public static boolean isNullOrEmpty(String st) {
        return st == null || st.isBlank();
    }
}