package com.example.javalearn;

import java.util.Random;

public class Test1 {
    public static void main(String[] args) {
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            if (random.nextInt(1000) == 1) {
                System.out.println("运行 = " + i);
            }
        }

    }
}
