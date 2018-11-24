package com.grove.project_crypto.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.grove.project_crypto.App;
import com.grove.project_crypto.Encrypted;
import com.grove.project_crypto.CryptoHelper.CryptoHelper;
import com.grove.project_crypto.Helper.DataBase;
import com.grove.project_crypto.Helper.EncryptedDAO;
import com.grove.project_crypto.Helper.JSONHelper;
import com.grove.project_crypto.InterfaceEncoder;
import com.grove.project_crypto.JsonSaver;
import com.grove.project_crypto.R;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.LinkedList;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ViewItemActivity extends AppCompatActivity implements JsonSaver,InterfaceEncoder {

    LinkedList<Encrypted> CryptoList;
    Toolbar toolbar;
    EditText etRMes, etEncMes;

    Encrypted CrItem;

    int position;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_reafactor:
                    return true;
                case R.id.navigation_share:

                    return true;
                case R.id.navigation_delete:
                    onRemove();
                    return true;
            }
            return false;
        }
    };

    @Override
    public void Export() {
        JSONHelper.exportToJSON(this, CryptoList);
    }

    @Override
    public void Import() {
        if (JSONHelper.importFromJSON(this) != null) {
            CryptoList.addAll(JSONHelper.importFromJSON(this));
        }
    }

    private void onRemove() {
        DataBase database = App.getInstance().getDatabase();
        EncryptedDAO encryptedDAO = database.encryptedDAO();
        encryptedDAO.delete(CrItem);
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.slide_up, R.anim.alpha);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.zero, R.anim.slide_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        toolbar = findViewById(R.id.item_activity_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle arguments = getIntent().getExtras();
        etRMes = findViewById(R.id.Raw_Message);
        etEncMes = findViewById(R.id.Encrypted_Message);
        if (arguments != null) {
            CrItem = (Encrypted) arguments.getSerializable(Encrypted.class.getSimpleName());
            position = arguments.getInt("Position");
        }

        etRMes.setText(CrItem.getMessage());
        etEncMes.setText(Decryptor(CrItem.getMessage()));


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onResume() {
        if(toolbar.getTitle() !=  CrItem.getTitle()) toolbar.setTitle(CrItem.getTitle());
        super.onResume();
    }

    @Override
    public String Decryptor(String raw) {

        CryptoHelper ch = new CryptoHelper();
        return ch.makeAes(raw,new IvParameterSpec(new byte[] {} ),CrItem.getSecretKey(), Cipher.DECRYPT_MODE);
    }

    public static String generateRandomIV() {
        SecureRandom ranGen = new SecureRandom();
        byte[] aesKey = new byte[16];
        ranGen.nextBytes(aesKey);
        StringBuffer result = new StringBuffer();
        for (byte b : aesKey) {
            result.append(String.format("%02x", b));
        }
        if (16 > result.toString().length()) {
            return result.toString();
        } else {
            return result.toString().substring(0, 16);
        }
    }

    @Override
    public String Encryptor() {
        return null;
    }
}
