package com.example.javalearn;

public class JoinTest implements Runnable {
    public int i = 0;

    public static void main(String[] args) {
        try {
            JoinTest test = new JoinTest();
            Thread th = new Thread(test);
            th.start();
            th.join();
            System.out.println(test.i);
        } catch (Exception e) {

        }
    }

    @Override
    public void run() {
        System.out.println("start");
        try {
            Thread.sleep(4000);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        i = 10;
        System.out.println("end");
    }
}