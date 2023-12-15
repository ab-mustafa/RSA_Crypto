package com.A3.AES;

import java.util.Random;

public class DataGenerator {

    public  static  byte[] generateData(){
        //one char size => two bytes utf-16 (like in java)
        //one char size => one byte ASCII code
        //10 * 1024 * 1024 Bytes = 10,485,760 Byte
        //10 M bytes => 10,485,760 char utf-8
        Random rd = new Random();
        byte[] arr = new byte[10485760];
        new Random().nextBytes(arr);
        return arr;
    }

}
