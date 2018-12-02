package com.grove.project_crypto.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.grove.project_crypto.App;
import com.grove.project_crypto.CryptoHelper.Encryptor;
import com.grove.project_crypto.EncryptedClass;
import com.grove.project_crypto.Helper.DataBase;
import com.grove.project_crypto.Helper.EncryptedDAO;
import com.grove.project_crypto.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class ViewItemActivity extends AppCompatActivity{

    Toolbar toolbar;
    TextView tvRMes, tvEncMes,tvDate;
    EditText etPassword;

    EncryptedClass CrItem;
    Encryptor encryptor;

    long id;
    char type;

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



    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);



        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            //CrItem = (EncryptedClass) arguments.getSerializable(EncryptedClass.class.getSimpleName());
            id = arguments.getLong("Position");
        }
        DataBase database = App.getInstance().getDatabase();
        EncryptedDAO encryptedDAO = database.encryptedDAO();
        CrItem = encryptedDAO.getById(id);

        type = CrItem.getType();

        ViewGroup inclusionViewGroup = findViewById(R.id.include);
        View child = null;

        switch (type){
            case 'T' :
                 child = LayoutInflater.from(this).inflate(R.layout.content_view_item, null);break;
            case 'I' :
                 child = LayoutInflater.from(this).inflate(R.layout.content_view_item, null);break;
            case 'F' :
                 child = LayoutInflater.from(this).inflate(R.layout.content_view_item, null);break;
        }
        inclusionViewGroup.removeAllViews();
        inclusionViewGroup.addView(child);

        toolbar = findViewById(R.id.item_activity_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tvRMes = findViewById(R.id.Raw_Message);
        tvDate = findViewById(R.id.tv_IV_date);
        tvEncMes = findViewById(R.id.Encrypted_Message);
        etPassword = findViewById(R.id.etPassword_on_itemView);

        encryptor = new Encryptor();

        tvRMes.setText(CrItem.getMessage());
        tvDate.setText(CrItem.getDate());
        etPassword.setText(CrItem.getPassword());


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onResume() {
        if(toolbar.getTitle() !=  CrItem.getTitle()) toolbar.setTitle(CrItem.getTitle());
        super.onResume();
    }

    public Bitmap getBitmapfromByteArray(byte[] bitmap) {
        return BitmapFactory.decodeByteArray(bitmap , 0, bitmap.length);
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    private String getExtraOptionPassword(){
        String result = "";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("H:mm");
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Map<String,Boolean> map = isExOption();
//        boolean a = map.containsKey("IMEI");
//        boolean ax = map.containsKey("Time");
//        boolean asx = map.containsKey(getResources().getString(R.string.imei));

        result += map.get(getResources().getString(R.string.imei)) ?   Objects.requireNonNull(telephonyManager).getDeviceId() : "";
//        result += map.get(getResources().getString(R.string.gps)) ?    : "";
        result += map.get(getResources().getString(R.string.time)) ?  dateFormat.format(new Date()) : "";

        return result;
    }

    private Map<String,Boolean> isExOption() {
        return CrItem.getExOption();
    }

    public void onDecryptClick(View view) {
        Log.i("TAG", "onDecryptClick: "+ CrItem.getPassword() + getExtraOptionPassword());
            String decrypted = encryptor.decrypt(CrItem.getMessage(), CrItem.getPassword() + getExtraOptionPassword());
            if (decrypted != null)
            switch (type) {
                case 'T' :  tvEncMes.setText(decrypted); break;
                case 'I' :
                    ImageView imageView = findViewById(R.id.imageView2);
                    byte[] bs;
                    String[] dec = decrypted.substring(1, decrypted.length() - 1).split(",");
                    bs = new byte[dec.length];
                    int i = 0;
                    for (String s: dec) {
                        bs[i] = Byte.parseByte(s.trim());
                        i++;
                    }
                    imageView.setImageBitmap(getBitmapfromByteArray(bs));
            }
            else Snackbar.make(view,"Не удалось",Snackbar.LENGTH_SHORT);
    }
}
