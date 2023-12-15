package com.A3.AES;



import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import static javax.crypto.Cipher.*;

public class AES {

    private Cipher cipher;
    private SecretKey secretKey;

    public AES() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        //Generate 128bit key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        this.secretKey = keyGenerator.generateKey();
        //get the cipher instance
        this.cipher = Cipher.getInstance("AES");
        this.cipher.init(ENCRYPT_MODE,secretKey);

    }
    public void setCipherInDecryptMode() throws InvalidAlgorithmParameterException, InvalidKeyException {
        this.cipher.init(DECRYPT_MODE,secretKey);
    }
    public void setCipherInEncryptMode() throws InvalidAlgorithmParameterException, InvalidKeyException {
        this.cipher.init(ENCRYPT_MODE,secretKey);
    }

    public String encryptBlock(byte[] array) throws IllegalBlockSizeException, BadPaddingException {
        byte[] cipherText = this.cipher.doFinal(array);
        return Base64.getEncoder().encodeToString(cipherText);
    }

}
