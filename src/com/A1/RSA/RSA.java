package com.A1.RSA;

import java.math.BigInteger;
import java.util.ArrayList;

import static java.lang.Integer.*;

public class RSA {
    public PublicKey publicKey;
    public SecretKey secretKey;

    public RSA(){

    }

    public void KeyGeneration(BigInteger p, BigInteger q){
        BigInteger n = p.multiply(q);
        BigInteger PhiN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = findEncryptionExponent(PhiN);
        BigInteger d = e.modInverse(PhiN);

        this.publicKey=new PublicKey(e,n);
        this.secretKey=new SecretKey(d,p,q);
    }
    public BigInteger EncryptMessage(PublicKey publicKey,String message){
        BigInteger m = this.convertMessageToAscii(message);
        BigInteger c = m.modPow(publicKey.getE(),publicKey.getN());
        return c;
    }

    public String DecryptMessage(SecretKey secretKey,BigInteger c){
        BigInteger q_dash = secretKey.getQ().modInverse(secretKey.getP());
        BigInteger p_dash = secretKey.getP().modInverse(secretKey.getQ());
        BigInteger Dp = secretKey.getD().mod(secretKey.getP().subtract(BigInteger.ONE));
        BigInteger Dq = secretKey.getD().mod(secretKey.getQ().subtract(BigInteger.ONE));
        BigInteger Cp = c.mod(secretKey.getP());
        BigInteger Cq = c.mod(secretKey.getQ());
        BigInteger Mp = Cp.modPow(Dp,secretKey.getP());
        BigInteger Mq = Cq.modPow(Dq,secretKey.getQ());
        BigInteger M= ((Mp.multiply(secretKey.getQ()).multiply(q_dash)).add(Mq.multiply(secretKey.getP()).multiply(p_dash))).mod(secretKey.getP().multiply(secretKey.getQ()));

        return  this.convertAsciiToText(M) ;
    }

    private BigInteger findEncryptionExponent(BigInteger PhiN){
        //Generate an integer e with 2≤e≤φ(n) such that gcd(e,φ(n))=1
        BigInteger e = BigInteger.valueOf(2);

        while (e.gcd(PhiN).compareTo(BigInteger.ONE)!=0 ){
            e=e.add(BigInteger.ONE);
        }
        return e;
    }


    private BigInteger convertMessageToAscii(String Message){
        String Result = "";
        for(int i=0; i< Message.length(); i++){
            Result = Result.concat(String.format("%03d", (int)Message.charAt(i)));
        }
        String LeftPadding="111"; // Padding to avoid this case
                                  // In case the message start with Z then the zero will be neglected 072 -> 72
        return new BigInteger(LeftPadding+Result);
    }

    private String convertAsciiToText(BigInteger Message){
        String Result = "";
        String Number = Message.toString();
        int index = 3; //Neglect the left padding at the beginning of string
        while (index<Number.length()){
            String substr = Number.substring(index,index+3);
            index+=3;
            char T = (char) parseInt(substr);
            Result = Result.concat(String.valueOf(T));
        }

        return Result;
    }

}
