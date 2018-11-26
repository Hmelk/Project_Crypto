package com.grove.project_crypto;

import android.arch.persistence.room.TypeConverter;
import java.math.BigInteger;
import javax.crypto.SecretKey;
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
    }
}
