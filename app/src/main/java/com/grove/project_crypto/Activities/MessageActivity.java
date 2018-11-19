package com.grove.project_crypto.Activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.grove.project_crypto.CryptoClass;
import com.grove.project_crypto.CryptoHelper.CryptoHelper;
import com.grove.project_crypto.Helper.JSONHelper;
import com.grove.project_crypto.JsonSaver;
import com.grove.project_crypto.Pagers.DialogPages;
import com.grove.project_crypto.Pagers.PageItem;
import com.grove.project_crypto.Pagers.SwipeItemsAdapter;
import com.grove.project_crypto.R;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class MessageActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, JsonSaver {

    private EditText etMessage;
    private EditText etSecretKey;
    private EditText etIV;
    Button btnEncrypt;
    Button cib;
    SwipeItemsAdapter methodadapter;
    List<PageItem> MethodEncryptedItems;
    List<PageItem> TypeEncryptedItems;
    SecretKey secretKey;
    LinkedList<CryptoClass> CryptoList;


    ViewPager ChipherPager;
    DialogPages dialogPages;
    ViewPager dialogpager;


    private Handler mHandler = new Handler();
    private boolean mShowingBack = false;
    private String Ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        etMessage = findViewById(R.id.Message);
        etSecretKey = findViewById(R.id.et_sk);
        etIV = findViewById(R.id.et_iv);
        btnEncrypt = findViewById(R.id.btn_encrypted);
        cib = findViewById(R.id.btn_copy);

        MethodEncryptedItems = new ArrayList<>();
        TypeEncryptedItems = new ArrayList<>();
        fillList();

        ChipherPager = findViewById(R.id.pv);
        methodadapter = new SwipeItemsAdapter(this);
        methodadapter.setItems(MethodEncryptedItems);
        ChipherPager.setAdapter(methodadapter);

        btnEncrypt.setOnClickListener(onEncrypt);
        etSecretKey.addTextChangedListener(new TextWatcher() {
            private String current = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                try {

                } catch (IndexOutOfBoundsException e) {
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
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
            buildDialog();
        }
    };


    public void onCopyInBuffer(View v) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", dialogPages.getMessage());
        Objects.requireNonNull(clipboard).setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "Текст скопирован в буфер обмена", Toast.LENGTH_SHORT).show();
    }

    public void onSave(View v) {
        CryptoList = new LinkedList<>();
        Import();
        CryptoList.addFirst(new CryptoClass("Example", MethodEncryptedItems.get(ChipherPager.getCurrentItem()).getValue(),'I', Ct, new Date(), secretKey.getEncoded().toString()));

        Export();
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.slide_up, R.anim.alpha);
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CryptoHelper ch = new CryptoHelper();
        String rowMessage = etMessage.getText().toString();

        Ct = ch.makeAes(rowMessage, secretKey, Cipher.ENCRYPT_MODE);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.dialog_rootView), false);

        dialogpager = dialogView.findViewById(R.id.dialog_pager);
        dialogPages = new DialogPages(this);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        dialogPages.setItems(list, Ct);
        dialogpager.setAdapter(dialogPages);

        dialogpager.beginFakeDrag();

        builder.setView(dialogView);


        builder.setTitle("Encrypted Message");
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;


        dialog.show();
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


    public void onSKGenerate(View view) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            secretKey = keyGenerator.generateKey();
            etSecretKey.setText(secretKey.getEncoded().toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void onIVGenerate(View view) {//throws NoSuchPaddingException, NoSuchAlgorithmException {
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        etIV.setText(cipher.getIV().toString());

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
    }
}
