package com.A3.AES;


import com.A1.RSA.PublicKey;
import com.A1.RSA.SecretKey;

import java.math.BigInteger;

import static java.lang.Integer.parseInt;

public class RSACustom {


    public PublicKey publicKey;
    public SecretKey secretKey;

    public RSACustom(){
        BigInteger P = new BigInteger("15554903035303856344007671063568213071669822184616101992595534860863803506262760067615727000088295330493705796902296102481798240988227195060316199080930616035532980617309644098719341753037782435645781436420697261984870969742096465765855782491538043554917285285471407866976465359446400695692459955929581561107496250057761324472438514351159746606737260676765872636140119669971105314539393270612398055538928361845237237855336149792618908050931870177925910819318623");
        BigInteger Q = new BigInteger("15239930048457525970295803203207379514343031714151154517998415248470711811442956493342175286216470497855132510489015253513519073889825927436792580707512051299817290925038739023722366499292196400002204764665762114445764643179358348705750427753416977399694184804769596469561594013716952794631383872745339020403548881863215482480719445814165242627056637786302612482697923973303250588684822021988008175106735736411689800380179302347354882715496632291069525885653297");
        this.KeyGeneration(P,Q);
    }

    public void KeyGeneration(BigInteger p, BigInteger q){
        BigInteger n = p.multiply(q);
        BigInteger PhiN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = new BigInteger("65537");
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
