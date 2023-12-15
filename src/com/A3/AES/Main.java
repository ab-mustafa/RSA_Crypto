package com.A3.AES;

import com.A1.RSA.RSA;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] data = DataGenerator.generateData();
        AES aes = new AES();
        int i=0;
        long startTime1 = System.currentTimeMillis();
        while(i < data.length){
          String result = aes.encryptBlock(Arrays.copyOfRange(data,i,i+16));
          //System.out.println(result);
          i+=16;
        }
        long stopTime1 =System.currentTimeMillis();
        System.out.println("AES Time");
        System.out.println(stopTime1 - startTime1);


        //RSA Time
        // Block size = 3072 bits = 384 Bytes = 384 char in utf-8
        i=0;
        RSACustom rsaCustom = new RSACustom();
        long startTime2 = System.currentTimeMillis();
        while(i < data.length) {
            byte[] subarray= Arrays.copyOfRange(data, i, i+384);
            String message="";
            for(int v = 0;v<subarray.length;v++){
                message=message.concat(String.valueOf((char) subarray[v]));
            }
            BigInteger result = rsaCustom.EncryptMessage(rsaCustom.publicKey, message);
            //System.out.println(result);

            i+=384;
        }
        long stopTime2 =System.currentTimeMillis();
        System.out.println("RSA Time");
        System.out.println(stopTime2 - startTime2);

    }
}
