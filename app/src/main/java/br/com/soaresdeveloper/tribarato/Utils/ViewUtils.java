package br.com.soaresdeveloper.tribarato.Utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Soares on 14/12/2017.
 */

public class ViewUtils {

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
}
