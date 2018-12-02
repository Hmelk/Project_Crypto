package com.grove.project_crypto.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.grove.project_crypto.App;
import com.grove.project_crypto.EncryptedClass;
import com.grove.project_crypto.Helper.DataBase;
import com.grove.project_crypto.Helper.EncryptedDAO;
import com.grove.project_crypto.LinearViewHolder;
import com.grove.project_crypto.MyLocation;
import com.grove.project_crypto.R;
import com.grove.project_crypto.ResAdapter;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements  LinearViewHolder.ActionListener {


    private RecyclerView itemresviews;
    private ResAdapter linearAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        MyLocation.SetUpLocationListener(this);
        String lang = preferences.getString("lang", "default");
        if (lang.equals("default")) {
            lang =getResources().getConfiguration().locale.getCountry();}
        Locale locale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);

        List<EncryptedClass> cryptoList;
        DataBase database = App.getInstance().getDatabase();
        EncryptedDAO encryptedDAO = database.encryptedDAO();
        cryptoList = encryptedDAO.getAll();

        itemresviews = findViewById(R.id.itemresview);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        itemresviews.setLayoutManager(layoutManager);
        itemresviews.setItemAnimator(new DefaultItemAnimator());

        linearAdapter = new ResAdapter();
        if (cryptoList.size() != 0)
            linearAdapter.addAll(cryptoList);
        linearAdapter.addHeader(inflateHeader());
        linearAdapter.setActionListener(this);
        itemresviews.setAdapter(linearAdapter);
    }

    private View inflateHeader() {
        CardView Header = (CardView) getLayoutInflater().inflate(
                R.layout.header, itemresviews, false);
        Header.setUseCompatPadding(true);
        return Header;
    }

    public void onCipherClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case (R.id.btn_text):
                intent = new Intent(MainActivity.this, MessageActivity.class);
                break;
            case (R.id.btn_file):
                intent = new Intent(MainActivity.this, FileActivity.class);
                break;
            case (R.id.btn_image):
                intent = new Intent(MainActivity.this, ImageActivity.class);
                break;
            case (R.id.btn_open):
                intent = new Intent(MainActivity.this, TestedActivity.class);
                break;
        }
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.slide_right, R.anim.alpha);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, 1);
            overridePendingTransition(R.anim.slide_up, R.anim.alpha);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnItemClick(long id) {
        Intent intent = new Intent(MainActivity.this, ViewItemActivity.class);
      //  intent.putExtra(EncryptedClass.class.getSimpleName(), linearAdapter.getItem(position));
        intent.putExtra("Position",id);
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.slide_right, R.anim.alpha);
    }


}
