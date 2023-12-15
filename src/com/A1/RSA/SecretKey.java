package com.A1.RSA;

import java.math.BigInteger;

public class SecretKey {

    private BigInteger d;
    private BigInteger p;
    private BigInteger q;
    public SecretKey(BigInteger d, BigInteger p, BigInteger q){
        this.d = d.add(BigInteger.ZERO);
        this.p = p.add(BigInteger.ZERO);
        this.q = q.add(BigInteger.ZERO);
    }
    public BigInteger getD(){
        return this.d;
    }
    public BigInteger getP(){
        return this.p;
    }
    public BigInteger getQ(){
        return this.q;
    }
    public void PrintKeyInfo(){
        System.out.println("\nSecret key info:");
        System.out.println("D: "+this.d.toString());
        System.out.println("P: "+this.p.toString());
        System.out.println("Q: "+this.q.toString());
    }
}
