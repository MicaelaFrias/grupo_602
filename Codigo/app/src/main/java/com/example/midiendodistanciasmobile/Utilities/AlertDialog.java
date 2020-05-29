package com.example.midiendodistanciasmobile.Utilities;

import android.app.Activity;
import android.content.DialogInterface;

public class AlertDialog {

    public static void displayAlertDialog(Activity activity, String title, String description, String labelButton) {
        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(activity).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(description);
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, labelButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


}
