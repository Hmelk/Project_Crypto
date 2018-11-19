package com.grove.project_crypto;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class LinearViewHolder extends RecyclerView.ViewHolder {

    private TextView title, message, key, type, data, imgType;

    private int position;
    private ActionListener actionListener;
    private boolean sks;
    private CryptoClass item;


    LinearViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.tv_Title);
        message = itemView.findViewById(R.id.tv_resMessage);
        key = itemView.findViewById(R.id.tv_SecretKey);
        type = itemView.findViewById(R.id.tv_cr);
        data = itemView.findViewById(R.id.tv_data);
        imgType = itemView.findViewById(R.id.img_type);
        sks = false;
        OnClickListener secretKeyShowListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sks) key.setText(item.getSecretKey());
                else key.setText("");
                sks = !sks;
            }
        };
        key.setOnClickListener(secretKeyShowListener);
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListener != null) actionListener.OnItemClick(position);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void bindView(CryptoClass item, int position, ActionListener listener) {
        actionListener = listener;
        this.position = position;
        this.item = item;
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        title.setText(item.getTitle());
        message.setText(item.getMessage());
        type.setText(item.getCryptoMethod());
        data.setText(dateFormat.format(item.getDate().getTime()));
        imgType.setText(item.getType()+"");
        int color = R.color.tpTColor ;
        switch (item.getType()){
            case 'T' : color = R.color.tpTColor;break;
            case 'F' : color = R.color.tpFColor;break;
            case 'I' : color = R.color.tpIColor;break;
        }
        DrawableCompat.setTint(imgType.getBackground(),color);

        imgType.setBackgroundResource(R.drawable.imagetype);
    }
//        @Override
//        public void onClick(View v) {
//            if (!sks) key.setText(item.getSecretKey());
//            //if (actionListener != null) actionListener.OnItemClick(position);
//        }


    public interface ActionListener {
        void OnItemClick(int position);
    }
}