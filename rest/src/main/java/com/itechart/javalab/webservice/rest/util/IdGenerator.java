package com.itechart.javalab.webservice.rest.util;

public class IdGenerator {

    private static int id = 1;

    public static Integer nextId() {
        return id++;
    }
}
