package com.grove.project_crypto.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.grove.project_crypto.App;
import com.grove.project_crypto.CryptoHelper.Encryptor;
import com.grove.project_crypto.EncryptedClass;
import com.grove.project_crypto.Helper.DataBase;
import com.grove.project_crypto.Helper.EncryptedDAO;
import com.grove.project_crypto.MyLocation;
import com.grove.project_crypto.Pagers.DialogPages;
import com.grove.project_crypto.Pagers.PageItem;
import com.grove.project_crypto.Pagers.SwipeItemsAdapter;
import com.grove.project_crypto.R;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.crypto.SecretKey;

public class MessageActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private EditText etMessage;
    private EditText etPassword;
    private TextView tvEMessage;
    Button btnEncrypt;
    Button cib;
    SwipeItemsAdapter methodadapter;
    List<PageItem> MethodEncryptedItems;
    SecretKey secretKey;


    ViewPager ChipherPager;
    DialogPages dialogPages;
    Encryptor encryptor;


    private boolean mShowingBack = false;
    private String Ct;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        etMessage = findViewById(R.id.Message);
        etPassword = findViewById(R.id.etPassword);
        tvEMessage = findViewById(R.id.tv_message);
        btnEncrypt = findViewById(R.id.btn_encrypted);
        cib = findViewById(R.id.btn_copy);

        encryptor = new Encryptor();

        MethodEncryptedItems = new ArrayList<>();
        fillList();

        ChipherPager = findViewById(R.id.pv);
        methodadapter = new SwipeItemsAdapter(this);
        methodadapter.setItems(MethodEncryptedItems);
        ChipherPager.setAdapter(methodadapter);

        btnEncrypt.setOnClickListener(onEncrypt);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_addParam, new CardFrontFragment())
                    .commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }
        getFragmentManager().addOnBackStackChangedListener(this);
    }


    private void fillList() {
        for (int i = 0; i < getResources().getStringArray(R.array.method_encrypted).length; i++)
            MethodEncryptedItems.add(new PageItem("Method", getResources().getStringArray(R.array.method_encrypted)[i]));
    }


    private View.OnClickListener onEncrypt = new View.OnClickListener() {
        public void onClick(View v) {
            password = etPassword.getText().toString();
            Log.i("TAG", "onDecryptClick: "+ password + getExtraOptionPassword());

            if (!password.isEmpty()) {
                secretKey = encryptor.deriveKey(password+getExtraOptionPassword());
                byte[] iv = encryptor.generateIV(16);
                String rowMessage = etMessage.getText().toString();
                Ct = encryptor.encrypt(rowMessage, secretKey, iv);
                tvEMessage.setText(Ct);
            }else Snackbar.make(v,getResources().getString(R.string.errPass),Snackbar.LENGTH_SHORT).show();
        }
    };
//    hrjrj
    @SuppressLint({"MissingPermission", "HardwareIds"})
    private String getExtraOptionPassword(){
        String result = "";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("H:mm");
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Map<String,Boolean> map = isExOption();
        result += map.get(getResources().getString(R.string.imei)) ?   Objects.requireNonNull(telephonyManager).getDeviceId() : "";
//        result += map.get(getResources().getString(R.string.gps)) ? MyLocation.imHere : "";
        result += map.get(getResources().getString(R.string.time)) ?  dateFormat.format(new Date()) : "";

        return result;
    }


    public void onCopyInBuffer(View v) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", dialogPages.getMessage());
        Objects.requireNonNull(clipboard).setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "Текст скопирован в буфер обмена", Toast.LENGTH_SHORT).show();
    }

    public void onSave(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.dialog_rootView), false);
        final EditText etTitle = dialogView.findViewById(R.id.etSetTitle);
        builder.setView(dialogView);
        builder.setTitle("EncryptedClass Message");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                EncryptedClass encrypted = new EncryptedClass(App.getInstance().getDatabase().encryptedDAO().getAll().size() + 1,etTitle.getText().toString(),
                        MethodEncryptedItems.get(ChipherPager.getCurrentItem()).getValue(), 'T', Ct, dateFormat.format(new Date().getTime()), password,isExOption());
                DataBase database = App.getInstance().getDatabase();
                EncryptedDAO encryptedDAO = database.encryptedDAO();
                encryptedDAO.insert(encrypted);
                Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.slide_up, R.anim.alpha);
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();
    }

    private Map<String,Boolean> isExOption(){
        CheckBox  cb1 = findViewById(R.id.checkBox);
        CheckBox  cb2 = findViewById(R.id.checkBox2);
        CheckBox  cb3 = findViewById(R.id.checkBox3);
        Map<String,Boolean> map = new ArrayMap<>();
        map.put("IMEI", cb1 != null && cb1.isChecked());
        map.put("GPS",cb2 != null && cb2.isChecked());
        map.put("Time",cb3 != null && cb3.isChecked());
        return map;
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
    public void onBackStackChanged() {

    }

    public void onAddParam(View view) {
        flipCard();
    }

    public void onPageArrowChange(View view) {
        switch (view.getId()) {
            case R.id.page_left:
                if (ChipherPager.getCurrentItem() != 0)
                    ChipherPager.setCurrentItem(ChipherPager.getCurrentItem() - 1);
                break;
            case R.id.page_right:
                if (ChipherPager.getCurrentItem() != Objects.requireNonNull(ChipherPager.getAdapter()).getCount() - 1)
                    ChipherPager.setCurrentItem(ChipherPager.getCurrentItem() + 1);
                break;
        }
    }

    public void onShowPassword(View view) {
        etPassword.setInputType(etPassword.getInputType() == 129 ?  1  :  129);
    }


    public static class CardFrontFragment extends Fragment {
        public CardFrontFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_front, container, false);
        }
    }


    public static class CardBackFragment extends Fragment {
        public CardBackFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_back, container, false);
        }
    }

    private void flipCard() {
        try {
            if (mShowingBack) {
                getFragmentManager().popBackStack();
                return;
            }
            mShowingBack = true;
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                    .replace(R.id.frame_addParam, new CardBackFragment())
                    .addToBackStack(null)
                    .commit();
        }catch (Exception e) {
            Log.e("TAG", "flipCard: ", e);
        }
    }
}
