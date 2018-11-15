package com.grove.project_crypto.Pagers;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.grove.project_crypto.R;

import java.util.List;

public class DialogPages extends android.support.v4.view.PagerAdapter {

    private final int MT = 0;
    private final int TMC = 1;


    private List<Integer> items;
    private String message;


        private Context mContext;

        public DialogPages(Context context) {
            mContext = context;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup viewGroup, int position) {
            ViewGroup layout;
            if (position == 0) {

                LayoutInflater inflater = LayoutInflater.from(mContext);
                layout = (ViewGroup) inflater.inflate(
                        R.layout.fragment_message, viewGroup, false);
                viewGroup.addView(layout);
                TextView tv = layout.findViewById(R.id.tv_message);
                Button btn = layout.findViewById(R.id.btn_copy);
                tv.setText(message);
            }else {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                layout = (ViewGroup) inflater.inflate(
                        R.layout.fragment_save, viewGroup, false);
                viewGroup.addView(layout);
//                InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Service.INPUT_METHOD_SERVICE);
//                EditText ed =  layout.findViewById(R.id.editText2);
//                ed.clearFocus();
//                assert imm != null;
//                imm.showSoftInput(ed, 0);
            }
//            else{ LayoutInflater inflater = LayoutInflater.from(mContext);
//            layout = (ViewGroup) inflater.inflate(
//                    R.layout.pager, viewGroup, false);
//            viewGroup.addView(layout);}
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

    public void setItems(List<Integer> items) {
        this.items = items;
    }
    public void setItems(List<Integer> items,String message) {
        this.items = items;
        this.message = message;
    }


        public CharSequence getMessage() {
            return message;
        }
    }

