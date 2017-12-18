package br.com.soaresdeveloper.tribarato.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Soares on 14/12/2017.
 */

public class ViewUtils {

    public static ProgressDialog mProgressDialog;

    public static void chamarToast(Context context, String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }

    public static boolean validarCampos(List<EditText> campos) {
        boolean result = true;
        for (EditText campo : campos) {
            if (campo.getText().toString().trim().equals("") || campo.getText().toString() == null) {
                campo.setError("Este campo é obrigatório");
                result = false;
                break;
            }
        }
        return result;
    }

    public static void chamarProgress(Context context, String titulo) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMax(100);
        mProgressDialog.setTitle(titulo);
        mProgressDialog.show();
    }

    public static void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
