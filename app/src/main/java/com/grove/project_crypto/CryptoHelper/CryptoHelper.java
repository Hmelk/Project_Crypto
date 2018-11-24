package com.grove.project_crypto.CryptoHelper;

import android.util.Base64;
import android.util.Log;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;


public class CryptoHelper {

    public CryptoHelper() {

    }

    public String makeAes(String rawMessage, IvParameterSpec ivSpec, SecretKey secretKey, int cipherMode) {


        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] inputByte = hex2Byte(rawMessage); //rawMessage.getBytes("UTF-8");

            if (cipherMode == Cipher.DECRYPT_MODE) {
                cipher.init(Cipher.DECRYPT_MODE, secretKey,ivSpec);
                byte[] b = Base64.decode(rawMessage, Base64.NO_WRAP);
//                int a = b.length;
//                byte[] ne = new byte[16];
//                System.arraycopy(b, 0, ne, 0, 16);
                byte[] plaintext = cipher.doFinal(b);
                return new String(plaintext, "UTF-8");
//                return byte2Hex(cipher.doFinal(b));
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey,ivSpec);
                return byte2Hex(Base64.encode(cipher.doFinal(inputByte), Base64.NO_WRAP));
            }

        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            Log.e("TAG", "makeAes: ", e);
            return null;
        }
    }

    private static String hex2ASCII(String hex) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            String str = hex.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
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


    private static byte hex2Byte(char a1, char a2) {
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

    private static String byte2Hex(byte b[]) {
        String hs = "";
        String stmp = "";
        for (byte aB : b) {
            stmp = Integer.toHexString(aB & 0xff);
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
















































