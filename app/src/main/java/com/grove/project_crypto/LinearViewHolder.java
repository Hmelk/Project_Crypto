package com.grove.project_crypto;

import android.annotation.SuppressLint;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LinearViewHolder extends RecyclerView.ViewHolder {

    private TextView title, message, password, type, data, imgType;

    private int position;
    private ActionListener actionListener;
    private boolean sks;
    private EncryptedClass item;


    LinearViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.tv_Title);
        message = itemView.findViewById(R.id.tv_resMessage);
        password = itemView.findViewById(R.id.tv_SecretKey);
        type = itemView.findViewById(R.id.tv_cr);
        data = itemView.findViewById(R.id.tv_data);
        imgType = itemView.findViewById(R.id.img_type);
        sks = false;
        OnClickListener passwordShowListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sks) password.setText(item.getPassword());
                else password.setText("");
                sks = !sks;
            }
        };
        password.setOnClickListener(passwordShowListener);
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListener != null) actionListener.OnItemClick(item.getId());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void bindView(EncryptedClass item, int position, ActionListener listener) {
        actionListener = listener;
        this.position = position;
        this.item = item;
//        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        title.setText(item.getTitle());

        message.setText(item.getMessage().length() > 256 ? item.getMessage().substring(0,256) + "..." : item.getMessage());
        type.setText(item.getCryptoMethod());
        data.setText(item.getDate());
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
//            if (!sks) password.setText(item.getPassword());
//            //if (actionListener != null) actionListener.OnItemClick(position);
//        }


    public interface ActionListener {
        void OnItemClick(long id);
    }
}