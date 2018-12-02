package com.grove.project_crypto.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grove.project_crypto.App;
import com.grove.project_crypto.CryptoHelper.Encryptor;
import com.grove.project_crypto.EncryptedClass;
import com.grove.project_crypto.Helper.DataBase;
import com.grove.project_crypto.Helper.EncryptedDAO;
import com.grove.project_crypto.Pagers.PageItem;
import com.grove.project_crypto.Pagers.SwipeItemsAdapter;
import com.grove.project_crypto.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.crypto.SecretKey;

public class ImageActivity extends AppCompatActivity  implements FragmentManager.OnBackStackChangedListener  {

    static final int GALLERY_REQUEST = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;

    private boolean mShowingBack = false;

    private String password;

    private Encryptor encryptor;


    private ImageView image;
    private TextView tv;
    private EditText etPassword;
    private Bitmap picture;

    private SwipeItemsAdapter methodadapter;
    private ViewPager ChipherPager;

    List<PageItem> MethodEncryptedItems;
    private String Ct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        picture = null;

        encryptor = new Encryptor();

        Button button = findViewById(R.id.button2);
        Button button1 = findViewById(R.id.button3);
        Button btnEncrypted = findViewById(R.id.btn_encrypted_on_image);
        tv = findViewById(R.id.tv_message_on_image);
        etPassword = findViewById(R.id.etPassword_on_image);
        image =  findViewById(R.id.imageView);

        MethodEncryptedItems = new ArrayList<>();
        fillList();

        ChipherPager = findViewById(R.id.pv_on_image);
        methodadapter = new SwipeItemsAdapter(this);
        methodadapter.setItems(MethodEncryptedItems);
        ChipherPager.setAdapter(methodadapter);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_addParam_on_image, new ImageActivity.CardFrontFragment())
                    .commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }
        getFragmentManager().addOnBackStackChangedListener(this);



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
        });

        btnEncrypted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] b = getByteArrayfromBitmap(picture);
                tv.setText(Arrays.toString(b));
                password = etPassword.getText().toString();
                SecretKey secretKey;
                if (!password.isEmpty()) {
                    secretKey = encryptor.deriveKey(password);
                    byte[] iv = encryptor.generateIV(16);
                    Ct = encryptor.encrypt(Arrays.toString(b), secretKey, iv);
                    tv.setText(Ct.length() > 256 ? Ct.substring(0,256)+ "..." : Ct);
                }else Snackbar.make(v,getResources().getString(R.string.errPass),Snackbar.LENGTH_SHORT).show();
//               im.setImageBitmap(getBitmapfromByteArray(b));

            }
        });
    }

    private void fillList() {
        for (int i = 0; i < getResources().getStringArray(R.array.method_encrypted).length; i++)
            MethodEncryptedItems.add(new PageItem("Method", getResources().getStringArray(R.array.method_encrypted)[i]));
    }

    @Override
    public void onBackStackChanged() {

    }


    public byte[] getByteArrayfromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        picture = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    image.setImageBitmap(picture);
                }break;
            case REQUEST_IMAGE_CAPTURE:
               if (resultCode == RESULT_OK) {
                Bundle extras = imageReturnedIntent.getExtras();
                picture = (Bitmap) Objects.requireNonNull(extras).get("data");
                image.setImageBitmap(picture);
            }


        }
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

    public void onAddParam(View view) {
        flipCard();
    }

    public void onCopyInBuffer(View v) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", Ct);
        Objects.requireNonNull(clipboard).setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "Текст скопирован в буфер обмена", Toast.LENGTH_SHORT).show();
    }

    public void onSave(View view) {
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
                        MethodEncryptedItems.get(ChipherPager.getCurrentItem()).getValue(), 'I', Ct, dateFormat.format(new Date().getTime()), password, isExOption());
                DataBase database = App.getInstance().getDatabase();
                EncryptedDAO encryptedDAO = database.encryptedDAO();
                encryptedDAO.insert(encrypted);
                Intent intent = new Intent(ImageActivity.this, MainActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.slide_up, R.anim.alpha);
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();
    }

    public void onShowPassword(View view) {
        etPassword.setInputType(etPassword.getInputType() == 129 ?  1  :  129);
    }

    private Map<String,Boolean> isExOption(){
        Map<String,Boolean> map = new ArrayMap<>();
        CheckBox cb1 = findViewById(R.id.checkBox);
        CheckBox  cb2 = findViewById(R.id.checkBox2);
        CheckBox  cb3 = findViewById(R.id.checkBox3);
        map.put(cb1.getText().toString(),cb1.isChecked());
        map.put(cb2.getText().toString(),cb2.isChecked());
        map.put(cb3.getText().toString(),cb3.isChecked());
        return map;
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
                    .replace(R.id.frame_addParam_on_image, new ImageActivity.CardBackFragment())
                    .addToBackStack(null)
                    .commit();
        }catch (Exception e) {
            Log.e("TAG", "flipCard: ", e);
        }
    }
}
