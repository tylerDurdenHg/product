package com.hg.product.utility.generic;


import org.hibernate.mapping.Any;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class GenericMethodOperation {
    public record AnyUser (long id, String name) implements Comparable<AnyUser> {
        @Override
        public int compareTo(AnyUser o) {
            int toId =  Objects.compare(this.id, o.id, Comparator.naturalOrder());
            if (toId == 0) {
                return Objects.compare(this.name, o.name, Comparator.naturalOrder());
            }
            return toId;
        }
    }
    public static void main(String[] args) {
        GenericMethodOperation gmo = new GenericMethodOperation();
        AnyUser tyler = gmo.findMaxNumber(new AnyUser(1L, "tyler"));
        AnyUser barney = gmo.findMaxNumber(new AnyUser(1L, "barney"));
        List<AnyUser> users = new ArrayList<>(List.of(tyler, barney));
        users.sort(Comparator.naturalOrder());
        System.out.println(users);
    }

    private <T extends Comparable<? extends T>> T findMaxNumber(T t) {
        return t;
    }
}
