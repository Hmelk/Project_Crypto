package com.grove.project_crypto.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.grove.project_crypto.CryptoHelper.Encryptor;
import com.grove.project_crypto.R;
import java.security.SecureRandom;
import java.util.Locale;

import javax.crypto.SecretKey;

public class TestedActivity extends AppCompatActivity {

    Button b1,b2;
    TextView tv1;
    EditText ed1;

    SecretKey secretKey;
/*    IvParameterSpec ivParameterSpec;
    CryptoHelper ch;*/

    private static SecureRandom random = new SecureRandom();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tested);
        b1 = findViewById(R.id.enc);
        b2 = findViewById(R.id.dec);
        tv1 = findViewById(R.id.tv1);
        ed1 = findViewById(R.id.ed1);

//       ch = new CryptoHelper();
//        secretKey = SKGenerate();
        final Encryptor encryptor = new Encryptor();
//        ivParameterSpec = new IvParameterSpec(generateIv(16));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Locale myLocale = new Locale("ru");
//                Resources res = getResources();
//                DisplayMetrics dm = res.getDisplayMetrics();
//                Configuration conf = res.getConfiguration();
//                conf.locale = myLocale;
//                res.updateConfiguration(conf, dm);
//                Intent refresh = new Intent(TestedActivity.class ,MainActivity.class);
//                startActivity(refresh);
//                finish();
////                String rowMessage = ed1.getText().toString();
//                secretKey =  encryptor.deriveKey("password");
//                byte[] iv = encryptor.generateIV(16);
//                String st =  encryptor.encrypt(rowMessage,secretKey,iv);  //ch.makeAes(rowMessage,ivParameterSpec, secretKey, Cipher.ENCRYPT_MODE);
//                tv1.setText(st);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1.setText(encryptor.decrypt(tv1.getText().toString(), "password"));
//                ch.makeAes(tv1.getText().toString(),ivParameterSpec ,secretKey, Cipher.DECRYPT_MODE);
            }
        });


    }

/*    public static byte[] generateIv(int length) {
        byte[] b = new byte[length];
        random.nextBytes(b);

        return b;
    }

    private SecretKey SKGenerate(){
        final String password = "password";
        int pswdIterations = 1000;
        int keySize = 128;
        byte[] ivBytes;
        byte[] saltBytes = new byte[8];
        random.nextBytes(saltBytes);
//        byte[] saltBytes = {0,1,2,3,4,5,6};

        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {}

        KeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                saltBytes,
                pswdIterations,
                keySize
        );
        try {
            byte[] b = factory.generateSecret(spec).getEncoded();
            return new SecretKeySpec(b,"AES");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
//        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//            keyGenerator.init(128);
//            return  keyGenerator.generateKey();
////            byte[] a =  secretKey.getEncoded();
////            String s = secretKey.getEncoded().toString();Y
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//
        return  null;

    }*/


}
