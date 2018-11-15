package com.grove.project_crypto;

import android.view.View;

public class ResAdapter extends RecyclerBindableAdapter<CryptoClass, LinearViewHolder> {
    private LinearViewHolder.ActionListener actionListener;


    @Override
    protected int layoutId(int type) {
        return R.layout.default_listitem;
    }

    @Override
    protected LinearViewHolder viewHolder(View view, int type) {
        return new LinearViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(LinearViewHolder viewHolder, final int position, int type) {
        viewHolder.bindView(getItem(position), position, actionListener);
    }

    public void setActionListener(LinearViewHolder.ActionListener actionListener) {
        this.actionListener = actionListener;
    }
}
