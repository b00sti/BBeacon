package com.example.bskeleton.basics;

/**
 * Created by Dominik (b00sti) Pawlik on 2016-11-15
 */

public class Triple<A, B, C> {
    public A first;
    public B second;
    public C third;

    public Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
}
