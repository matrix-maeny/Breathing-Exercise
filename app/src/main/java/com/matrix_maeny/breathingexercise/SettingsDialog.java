package com.matrix_maeny.breathingexercise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatButton;

public class SettingsDialog extends AppCompatDialogFragment {

    private SettingsDialogListener listener;

    EditText enteredTime;
    AppCompatButton setBtn;
    int time = 7;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        ContextThemeWrapper wrapper = new ContextThemeWrapper(getContext(), androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.setting_dialog_layout,null);
        builder.setView(root);

        enteredTime = root.findViewById(R.id.enteredTime);
        setBtn = root.findViewById(R.id.setBtn);

        setBtn.setOnClickListener(v->{

            if(checkTime()){
                listener.getTime(time);
                dismiss();
            }
        });

        return builder.create();
    }

    private boolean checkTime() {

        try {
            time = Integer.parseInt(enteredTime.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Please enter a valid time", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SettingsDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface SettingsDialogListener{
        void getTime(int time);
    }
}
