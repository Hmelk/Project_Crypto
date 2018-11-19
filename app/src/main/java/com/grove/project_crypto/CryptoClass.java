package com.grove.project_crypto;


import java.io.Serializable;
import java.util.Date;

public class CryptoClass implements Serializable {

    private String title;
    private String cryptoMethod;
    private String message;
    private Date data;
    private String secretKey;
    private char type;

     public CryptoClass(String title, String cryptoMethod,char type, String message, Date data, String secretKey){
        this.title = title;
        this.cryptoMethod = cryptoMethod;
        this.type = type;
         this.message = message;
         this.data = data;
         this.secretKey = secretKey;

     }

    public String getTitle() {
        return title;
    }

    public String getCryptoMethod() {
        return cryptoMethod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return data;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }
}
