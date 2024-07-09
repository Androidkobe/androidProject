package com.example.javalearn;


import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

class Test2 {
    public boolean isValid(String s) {
        HashMap<Character,Character> map = new HashMap();
        map.put(')','(');
        map.put('}','{');
        map.put(']','[');
        Deque<Character> stack = new LinkedList<Character>();
        for (int i = 0;i<s.length();i++){
            if (map.containsKey(s.charAt(i)) && map.get(s.charAt(i)).equals(s.charAt(i))){
                stack.pop();
            }else{
                stack.push(s.charAt(i));
            }
        }
        return stack.isEmpty();
    }
}