package com.grove.project_crypto;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class LinearViewHolder extends RecyclerView.ViewHolder {

    private TextView title, message, key, type, data;

    private int position;
    private ActionListener actionListener;

    public LinearViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.tv_Title);
        message = itemView.findViewById(R.id.tv_resMessage);
        key = itemView.findViewById(R.id.tv_SecretKey);
        type = itemView.findViewById(R.id.tv_cr);
        data = itemView.findViewById(R.id.tv_data);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListener != null) {
                    actionListener.OnItemClick(position);
                }
            }
        });
    }

    public void bindView(CryptoClass item, int position, ActionListener listener) {
        actionListener = listener;
        this.position = position;
        title.setText(item.getTitle());
        message.setText(item.getMessage());
        key.setText(item.getSecretKey());
        type.setText(item.getType());
        data.setText(item.getData());
    }


    public interface ActionListener {
        void OnItemClick(int position);
    }
}