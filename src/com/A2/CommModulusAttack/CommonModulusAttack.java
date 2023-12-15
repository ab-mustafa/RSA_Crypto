package com.A2.CommModulusAttack;

import java.math.BigInteger;

public class CommonModulusAttack {
    BigInteger N;
    BigInteger eMessage1;
    BigInteger eMessage2;
    BigInteger cipherMessage1;
    BigInteger cipherMessage2;

    public CommonModulusAttack(BigInteger N, BigInteger eMessage1, BigInteger eMessage2, BigInteger cipherMessage1, BigInteger cipherMessage2){
        this.N = N;
        this.eMessage1=eMessage1;
        this.eMessage2=eMessage2;
        this.cipherMessage1=cipherMessage1;
        this.cipherMessage2=cipherMessage2;
    }
    public BigInteger ExtractMessage(){
        BigInteger[] factors = this.extendedEuclidean(this.eMessage1,this.eMessage2);
        BigInteger M;
        M = (cipherMessage1.modPow(factors[1],N).multiply(cipherMessage2.modPow(factors[2],N))).mod(N);
        return M;
    }



    private BigInteger[] extendedEuclidean(BigInteger p, BigInteger q) {
        BigInteger[] val = new BigInteger[3];

        if (q.equals(BigInteger.ZERO)) {
            val[0] = p;
            val[1] = BigInteger.ONE;
            val[2] = BigInteger.ZERO;
        } else {
            BigInteger[] val2 = extendedEuclidean(q, p.mod(q));
            val[0] = val2[0];
            val[1] = val2[2];
            val[2] = val2[1].subtract(p.divide(q).multiply(val2[2]));
        }

        return val;
    }



}
