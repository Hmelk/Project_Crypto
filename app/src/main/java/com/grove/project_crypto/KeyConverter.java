package com.grove.project_crypto;

import android.arch.persistence.room.TypeConverter;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class KeyConverter {
    @TypeConverter
    public String fromKey(SecretKey key) {
       return new BigInteger(1, key.getEncoded()).toString(16);
    }

    @TypeConverter
    public SecretKey toKey(String key) {
        byte[] keyByteAr = new byte[16];
        System.arraycopy(new BigInteger(key, 16).toByteArray(), 0, keyByteAr, 1, 15);
        int s = keyByteAr.length * 8;
        try {
            return new SecretKeySpec(keyByteAr,0,128, "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
//        final String password = "test";
//        int pswdIterations = 65536;
//        int keySize = 128;
//        byte[] ivBytes;
//        byte[] saltBytes = {0,1,2,3,4,5,6};
//
//        SecretKeyFactory factory = null;
//        try {
//            factory = SecretKeyFactory.getInstance("PBEwithMD5AND128BITAES-CBC-OPENSSL");
//        } catch (NoSuchAlgorithmException e) {}
//
//        PBEKeySpec spec = new PBEKeySpec(
//                password.toCharArray(),
//                saltBytes,
//                pswdIterations,
//                keySize
//        );
//        try {
//            secretKey = factory.generateSecret(spec);
//        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
//        }

//        return  new SecretKeySpec(secretKey.getEncoded(),"AES");
    }
}
