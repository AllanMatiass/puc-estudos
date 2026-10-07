package com.Ensinar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        int n = (int)  Math.pow(2, 20);

        List<Integer> list = IntStream.rangeClosed(1, n)
                .boxed()
                .toList();


        int low = 0;
        int high = list.size() - 1;
        Integer target = 524288;
        boolean flag = false;

        for (int i = 0; low <= high; i++) {
            int mid =  low + ((high - low) / 2);
            if (list.get(mid).equals(target)) {
                System.out.println("Achou em: " + i);
                flag = true;
                break;
            } else if (list.get(mid) > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        if (!flag) System.out.println("Não achou");

    }
}
