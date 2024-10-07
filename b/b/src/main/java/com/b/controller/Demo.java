package com.b.controller;

import java.io.File;

public class Demo {
    public static void main(String[] args) {
        File file=new File("output.json");
        String absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath);
    }
}
