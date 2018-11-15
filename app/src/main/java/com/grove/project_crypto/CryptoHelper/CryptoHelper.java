package com.grove.project_crypto.CryptoHelper;

import android.util.Base64;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;


public class CryptoHelper {
    private SecretKey secretKey;

    public CryptoHelper() {

    }

    public String makeAes(String rawMessage,SecretKey secretKey, int cipherMode) {
        this.secretKey = secretKey;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] inputByte = rawMessage.getBytes("UTF-8");

            if(cipherMode == Cipher.DECRYPT_MODE) {
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                return byte2Hex(cipher.doFinal(Base64.decode(inputByte, Base64.DEFAULT)));
            }else {
                cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
                return byte2Hex (Base64.encode(cipher.doFinal(inputByte), Base64.DEFAULT));
            }
         } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] hex2Byte(String str) {
        int len = str.length();
        if (len % 2 != 0) return null;
        byte r[] = new byte[len / 2];
        int k = 0;
        for (int i = 0; i < str.length() - 1; i += 2) {
            r[k] = hex2Byte(str.charAt(i), str.charAt(i + 1));
            k++;
        }
        return r;
    }



    public static byte hex2Byte(char a1, char a2) {
        int k;
        if (a1 >= '0' && a1 <= '9') k = a1 - 48;
        else if (a1 >= 'a' && a1 <= 'f') k = (a1 - 97) + 10;
        else if (a1 >= 'A' && a1 <= 'F') k = (a1 - 65) + 10;
        else k = 0;
        k <<= 4;
        if (a2 >= '0' && a2 <= '9') k += a2 - 48;
        else if (a2 >= 'a' && a2 <= 'f') k += (a2 - 97) + 10;
        else if (a2 >= 'A' && a2 <= 'F') k += (a2 - 65) + 10;
        else k += 0;
        return (byte) (k & 0xff);
    }

    public static String byte2Hex(byte b[]) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toLowerCase();
    }


}





























//
//import android.util.Base64;
//
//import java.security.GeneralSecurityException;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
//public class CryptoHelper {
//
//    public static String decrypt(String key, String iv, String encrypted)
//            throws GeneralSecurityException {
//
////Преобразование входных данных в массивы байт
//
//        final byte[] keyBytes = key.getBytes();
//        final byte[] ivBytes = iv.getBytes();
//
//        final byte[] encryptedBytes = Base64.decode(encrypted, Base64.DEFAULT);
//
//        //Инициализация и задание параметров расшифровки
//
//        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));
//
////Расшифровка
//
//        final byte[] resultBytes = cipher.doFinal(encryptedBytes);
//        return new String(resultBytes);
//    }
//    public static String encrypt(String key, String iv, String encrypted)
//            throws GeneralSecurityException {
//
//
//
//        final byte[] keyBytes = key.getBytes();
//        final byte[] ivBytes = iv.getBytes();
//
//        final byte[] encryptedBytes = Base64.decode(encrypted, Base64.DEFAULT);
//
//
//
//        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));
//
//
//
//        final byte[] resultBytes = cipher.doFinal(encryptedBytes);
//        return new String(resultBytes);
//    }
//
//}
//
















































