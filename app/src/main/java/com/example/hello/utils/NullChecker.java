package com.example.hello.utils;

public class NullChecker {
    public static String nullCheckString(String value) {
        return value == null ? "" : value;
    }
}
