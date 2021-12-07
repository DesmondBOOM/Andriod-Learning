package com.example.hello.functors;

public interface Action<T> {
    void invoke(T t);
}
