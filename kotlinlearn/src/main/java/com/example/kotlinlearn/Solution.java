package com.example.kotlinlearn;

class Solution {
    public String longestPalindrome(String s) {
        if(s.length() < 2){
            return s;
        }
        char[] chars = s.toCharArray();
        int maxLength = 0;
        int begin = 0;
        for(int i = 0;i < s.length() - 1;i++){
            for(int j = i + 1;j < s.length();j++){
                if(isAlive(chars,i,j) && j-i+1 > maxLength){
                    maxLength = j-i +1;
                    begin = i;
                }
            }
        }
        return s.substring(begin,begin+maxLength+1);
    }
    public boolean isAlive(char[] chars,int left,int right){
        while(left < right){
            if(chars[left] == (chars[right])){
                left--;
                right--;
            }else{
                return false;
            }
        }
        return true;
    }
}