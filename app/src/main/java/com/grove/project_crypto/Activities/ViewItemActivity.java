package com.grove.project_crypto.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.grove.project_crypto.CryptoClass;
import com.grove.project_crypto.Helper.JSONHelper;
import com.grove.project_crypto.JsonSaver;
import com.grove.project_crypto.R;

import java.util.LinkedList;
import java.util.Objects;

public class ViewItemActivity extends AppCompatActivity implements JsonSaver {

    LinkedList<CryptoClass> CryptoList;
    Toolbar toolbar;
    EditText etRMes, etEncMes;

    CryptoClass item;

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

        CryptoList = new LinkedList<>();
        Import();
        CryptoList.remove(position);
        Export();
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
            item = (CryptoClass) arguments.getSerializable(CryptoClass.class.getSimpleName());
            position = arguments.getInt("Position");
        }

        etRMes.setText(item.getMessage());


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        toolbar.setTitle(item.getTitle());
        toolbar.setSubtitle(item.getTitle());
    }

}
