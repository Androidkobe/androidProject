package com.example.demo;

import java.util.HashMap;

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 */

class Test {
    volatile HashMap<String, Class> datat = new HashMap<>();
    StringBuffer sb = new StringBuffer();

    public void add(String str1, String str2) {
        sb.append(str1).append(str2);
    }
}
