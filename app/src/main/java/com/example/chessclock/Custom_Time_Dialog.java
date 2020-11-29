package com.example.chessclock;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Custom_Time_Dialog extends AppCompatDialogFragment {

    private EditText title,minute,second;
    private CustomTimerDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.add_timer_dialog,null);

        builder.setView(view).setTitle("Add Timer")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String custom_title=title.getText().toString();
                        int custom_minute=Integer.parseInt(minute.getText().toString());
                        int custom_second=Integer.parseInt(second.getText().toString());
                        listener.applyTexts(custom_title,custom_minute,custom_second);
                    }
                });
        title=view.findViewById(R.id.custom_title);
        minute=view.findViewById(R.id.custom_minute);
        second=view.findViewById(R.id.custom_second);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (CustomTimerDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface CustomTimerDialogListener {
        void applyTexts(String custom_title, int custom_minute,int custom_second);
    }
}
