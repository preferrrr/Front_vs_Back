package com.android.front_vs_back;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment {
    private Context context;

    public MyDialog(Context context) {
        this.context = context;
    }

    public void callDialog(){
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_maker);
        dialog.show();

        final Button btnOk = (Button) dialog.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
