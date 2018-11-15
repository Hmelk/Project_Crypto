package com.grove.project_crypto.Pagers;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grove.project_crypto.R;

import java.util.List;

public class SwipeItemsAdapter extends android.support.v4.view.PagerAdapter {

    private List<PageItem> items;


        private Context mContext;

        public SwipeItemsAdapter(Context context) {
            mContext = context;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup viewGroup, int position) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewGroup layout = (ViewGroup) inflater.inflate(
                    R.layout.pager, viewGroup, false);
            TextView tv = layout.findViewById(R.id.tv);
            tv.setText(items.get(position).getValue());
            viewGroup.addView(layout);


            return layout;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup viewGroup, int position, @NonNull Object view) {
            viewGroup.removeView((View) view);
        }

        @Override
        public int getCount() {
            return items.size() ;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

    public void setItems(List<PageItem> items) {
        this.items = items;
    }

    @Override
        public CharSequence getPageTitle(int position) {
            return items.get(position).getTitle();
        }


    }

