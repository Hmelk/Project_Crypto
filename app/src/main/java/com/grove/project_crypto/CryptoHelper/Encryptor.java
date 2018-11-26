package com.grove.project_crypto.CryptoHelper;

import javax.crypto.SecretKey;

public class Encryptor {
    SecretKey key;
    byte[] salt;

    public SecretKey deriveKey(String password) {
        salt = Crypto.generateSalt();
        return Crypto.generateKey(salt, password);
    }
    public byte[] generateIV(int length){
        return Crypto.generateIv(length);
    }

    public String encrypt(String plaintext,SecretKey secretKey,byte[] iv) {

//        key = deriveKey(password, salt);
//            Log.d("TAG", "Generated key: " + getRawKey());

        return Crypto.encrypt(plaintext, secretKey,iv, salt);
    }

    public String decrypt(String ciphertext, String password) {
        return Crypto.decryptPbkdf2(ciphertext, password);
    }
}