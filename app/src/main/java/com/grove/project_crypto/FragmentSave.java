package com.grove.project_crypto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

public class FragmentSave extends DialogFragment {
final String LOG_TAG = "myLogs";

        View.OnClickListener onOk;

@NonNull
public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return builder
                        .setTitle("Choose a title:")
                        .setView(R.layout.fragment_save)
                        .setPositiveButton("OK", null)
                        .setNegativeButton("Отмена", null)
                        .create();
        }else return builder.create();
}
//        View dialogView = inflater.inflate(R.layout.fragment_save,(ViewGroup) getActivity().findViewById(R.id.frag_save), false );
//        LayoutInflater inflater = this.getLayoutInflater();
//        builder.setView(dialogView);
//        builder.setTitle("EncryptedClass Message");
//        builder.setPositiveButton("OK", null);
//        return builder.create();
//        }

        public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 2: onDismiss");
        }

public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 2: onCancel");
        }
        }
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        //            textMsg = (TextView) view.findViewById(R.id.tv_message);
//
//        return inflater.inflate(R.layout.fragment_save, null);
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//}
