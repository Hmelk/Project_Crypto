package com.grove.project_crypto.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import com.grove.project_crypto.App;
import com.grove.project_crypto.Encrypted;
import com.grove.project_crypto.Helper.DataBase;
import com.grove.project_crypto.Helper.EncryptedDAO;
import com.grove.project_crypto.LinearViewHolder;
import com.grove.project_crypto.R;
import com.grove.project_crypto.ResAdapter;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements  LinearViewHolder.ActionListener {

//    public static App instance;

    private DataBase database;
    private EncryptedDAO encryptedDAO;

    RecyclerView itemresviews;

    private List<Encrypted> CryptoList;

    private ResAdapter linearAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CryptoList = new LinkedList<>();
        database = App.getInstance().getDatabase();
        encryptedDAO = database.encryptedDAO();
        CryptoList  = encryptedDAO.getAll();

        itemresviews = findViewById(R.id.itemresview);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        itemresviews.setLayoutManager(layoutManager);
        itemresviews.setItemAnimator(new DefaultItemAnimator());

        linearAdapter = new ResAdapter();
        if (CryptoList.size() != 0)
            linearAdapter.addAll(CryptoList);
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
                intent = new Intent(MainActivity.this, MessageActivity.class);
                break;
            case (R.id.btn_image):
                intent = new Intent(MainActivity.this, MessageActivity.class);
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

//    @Override
//    public void Export() {
//        JSONHelper.exportToJSON(this, CryptoList);
//    }
//
//    @Override
//    public void Import() {
//        if (JSONHelper.importFromJSON(this) != null) {
//            CryptoList.addAll(JSONHelper.importFromJSON(this));
//            linearAdapter.addAll(CryptoList);
//            linearAdapter.notifyDataSetChanged();
//        }
//    }


    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, ViewItemActivity.class);
        intent.putExtra(Encrypted.class.getSimpleName(), linearAdapter.getItem(position));
        intent.putExtra("Position",position);
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.slide_right, R.anim.alpha);
    }

}
