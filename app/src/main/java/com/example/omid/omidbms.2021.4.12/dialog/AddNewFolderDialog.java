package com.example.omid.omidbms.dialog;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omid.omidbms.R;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Taher on 28/05/2017.
 * Project: notes
 */

public class AddNewFolderDialog extends DialogFragment implements View.OnClickListener {
    private EditText edtip;
    private EditText edt_port;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_new_folder, container, false);
        edtip = view.findViewById(R.id.edtip);
        edt_port = view.findViewById(R.id.edtport);
        TextView save = view.findViewById(R.id.txtSave);
        TextView cancel = view.findViewById(R.id.txtCancel);

        save.setOnClickListener(this);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        if (edt_port.getText().toString().length() > 0 && edtip.getText().toString().length() > 0) {
            String server = edtip.getText().toString().trim();
            String port = edt_port.getText().toString().trim();
            SharedPreferences pref = getActivity().getSharedPreferences("myprefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("server", server);
            editor.putString("port", port);
            editor.apply();
            Toast.makeText(getActivity(), "ذخیره شد", Toast.LENGTH_LONG).show();
            getDialog().dismiss();
        }
    }


    public interface OnSaveClickListener {
        /**
         * @param name Save folder's name in database
         */
        void onSaveClick(String name);
    }
}
