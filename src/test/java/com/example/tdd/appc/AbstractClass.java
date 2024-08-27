package com.example.tdd.appc;

public abstract class AbstractClass {
    public abstract int nextNum();

    public void printNext() {
        System.out.println(nextNum());
    }
}
