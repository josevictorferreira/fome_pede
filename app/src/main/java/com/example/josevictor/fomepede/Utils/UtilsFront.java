package com.example.josevictor.fomepede.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by josevictor on 15/11/17.
 */

public class UtilsFront {

    public static void mensagemErro(Context contexto, int idTexto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        builder.setTitle("Aviso");

        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(idTexto);

        builder.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
