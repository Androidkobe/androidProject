package com.example.kotlinlearn;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Testt {

    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 200.0);
        map.put("c", "Stringaa");
        map.put("d", new Testt());
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        if (iterator != null) {
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                String key = entry.getKey();
                Object value = entry.getValue();
                System.out.println("key = " + key + " value " + value);
                if (value instanceof Integer) {
                    add(key, (Integer) value);
                }
                if (value instanceof Float) {
                    add(key, (Float) value);
                }

                if (value instanceof Double) {
                    add(key, (Double) value);
                }

                if (value instanceof String) {
                    add(key, (String) value);
                }
            }
        }

    }

    public static void add(String key, String value) {
        System.out.println("String key = " + key + " value " + value);
    }

    public static void add(String key, int value) {
        System.out.println("int key = " + key + " value " + value);
    }

    public static void add(String key, double value) {
        System.out.println("double key = " + key + " value " + value);
    }

    public static void add(String key, float value) {
        System.out.println("float key = " + key + " value " + value);
    }
}