package com.grove.project_crypto.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.grove.project_crypto.R;

import java.nio.ByteBuffer;
import java.util.Objects;

import static com.grove.project_crypto.activities.ImageActivity.REQUEST_IMAGE_CAPTURE;


public class FileActivity extends AppCompatActivity {

    private static final int PICKFILE_RESULT_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent,PICKFILE_RESULT_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap picture = null;
        ImageView im = findViewById(R.id.im);
        switch(requestCode){
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    picture = (Bitmap) Objects.requireNonNull(extras).get("data");
                    im.setImageBitmap(picture);
                }
            case PICKFILE_RESULT_CODE:
                if(resultCode == RESULT_OK){
                    Object s = data.getData();
//                    byte[] b = Byte.;
//                    textView.setText(FilePath);
                }
                break;
        }
    }

}
