package com.A1.RSA;

import java.math.BigInteger;

public class PublicKey {
    private BigInteger e;
    private BigInteger n;
    public PublicKey(BigInteger e, BigInteger n){
        this.e = e.add(BigInteger.ZERO);
        this.n = n.add(BigInteger.ZERO);
    }
    public BigInteger getE(){
        return this.e;
    }
    public BigInteger getN(){
        return this.n;
    }
    public void PrintKeyInfo(){
        System.out.println("\nPublic key info:");
        System.out.println("E: "+this.e.toString());
        System.out.println("N: "+this.n.toString());
    }

}
