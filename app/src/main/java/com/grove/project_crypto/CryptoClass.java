package com.grove.project_crypto;


import java.io.Serializable;

public class CryptoClass implements Serializable {

    private String title;
    private String type;
    private String message;
    private String data;
    private String secretKey;

     public CryptoClass(String title, String type, String message, String data, String secretKey){
        this.title = title;
        this.type  = type;
         this.message = message;
         this.data = data;
         this.secretKey = secretKey;
     }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
