package com.hg.product.utility.threads;

import java.io.Serializable;
import java.lang.constant.Constable;
import java.lang.constant.ConstantDesc;
import java.util.List;

public class IntersectionOperation {

    public static void main(String[] args) {
        IntersectionOperation ios = new IntersectionOperation();
        ios.genericInterSection();
    }

    private void genericInterSection() {
        var listMixed = List.of(1, "tyler");
        Object type = null;

     var st = switch (type) {
          case String s -> "home access";
          case Integer i -> {
            if (i > 10) {
                yield "more than 10: " + i;
            } else {
                yield "less than 10: " +i;
            }
          }
          case null -> "this is null";
          default -> 123;
      };
        System.out.println("st = " + st);

    }


}
