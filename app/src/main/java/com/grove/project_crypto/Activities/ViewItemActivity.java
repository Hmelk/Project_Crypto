package com.grove.project_crypto.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.grove.project_crypto.CryptoClass;
import com.grove.project_crypto.Helper.JSONHelper;
import com.grove.project_crypto.JsonSaver;
import com.grove.project_crypto.R;

import java.util.Arrays;
import java.util.LinkedList;

public class ViewItemActivity extends AppCompatActivity implements JsonSaver {

    LinkedList<CryptoClass> CryptoList;

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

    private void onRemove(){

        CryptoList = new LinkedList<>();
        Import();
        CryptoList.remove(position);
        Export();
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.slide_up, R.anim.alpha);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);


        Bundle arguments = getIntent().getExtras();

        if(arguments!=null) {
            item = (CryptoClass) arguments.getSerializable(CryptoClass.class.getSimpleName());
            position = arguments.getInt("Position");
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
