package br.com.soaresdeveloper.tribarato;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.soaresdeveloper.tribarato.Utils.ViewUtils;
import br.com.soaresdeveloper.tribarato.entidades.Oferta;
import br.com.soaresdeveloper.tribarato.entidades.Usuario;

public class CriarOfertaActivity extends AppCompatActivity {

    EditText edtTituloOferta, edtDescricaoOferta, edtPrecoOferta, edtEstadoOferta, edtCidadeOferta, edtSiteOferta, edtLocalOferta;
    Button btnPublicarOferta, btnLimparCampos;

    private Oferta oferta;
    private Usuario mUsuario;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mOfertasDatabaseReference;
    private DatabaseReference mUsuariosDatabaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_oferta);

        edtTituloOferta = (EditText) findViewById(R.id.tituloOferta);
        edtDescricaoOferta = (EditText) findViewById(R.id.descricaoOferta);
        edtPrecoOferta = (EditText) findViewById(R.id.precoOferta);
        edtLocalOferta = (EditText) findViewById(R.id.localOferta);
        edtEstadoOferta = (EditText) findViewById(R.id.estadoOferta);
        edtCidadeOferta = (EditText) findViewById(R.id.cidadeOferta);
        edtSiteOferta = (EditText) findViewById(R.id.siteOferta);
        btnPublicarOferta = (Button) findViewById(R.id.btnPublicarOferta);
        btnLimparCampos = (Button) findViewById(R.id.btnLimparCampos);


        // Inicializacao componentes Firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mOfertasDatabaseReference = mFirebaseDatabase.getReference().child("ofertas");
        mUsuariosDatabaseReference = mFirebaseDatabase.getReference().child("usuarios");
        auth = FirebaseAuth.getInstance();

        edtPrecoOferta.addTextChangedListener(new MascaraMonetaria(edtPrecoOferta));

        btnPublicarOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<EditText> campos = new ArrayList<EditText>();
                campos.add(edtTituloOferta);
                campos.add(edtDescricaoOferta);
                campos.add(edtPrecoOferta);
                if (edtLocalOferta.getText().toString().trim().equals("") || edtLocalOferta.getText().toString() == null) {
                    campos.add(edtSiteOferta);
                } else {
                    campos.add(edtLocalOferta);
                    campos.add(edtEstadoOferta);
                    campos.add(edtCidadeOferta);
                }

                if (ViewUtils.validarCampos(campos)) {

                    if (validarURL()) {

                        ViewUtils.chamarProgress(CriarOfertaActivity.this, "Publicando...");

                        String titulo = edtTituloOferta.getText().toString();
                        String descricao = edtDescricaoOferta.getText().toString();
                        String preco = edtPrecoOferta.getText().toString();
                        String local = edtLocalOferta.getText().toString();
                        String estado = edtEstadoOferta.getText().toString();
                        String cidade = edtCidadeOferta.getText().toString();
                        String site = edtSiteOferta.getText().toString();
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        Date date = new Date();
                        String data = dateFormat.format(date);

                        oferta = new Oferta(mUsuario, null, titulo, descricao, preco, estado, cidade, local, site, data);
                        mOfertasDatabaseReference.push().setValue(oferta).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                ViewUtils.dismissProgress();
                                ViewUtils.chamarToast(CriarOfertaActivity.this, "Oferta publicada com sucesso!");

                                limparCampos();

                                Intent intent = new Intent(CriarOfertaActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        });

        btnLimparCampos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCampos();
            }
        });
    }

    private void limparCampos() {
        edtTituloOferta.setText("");
        edtDescricaoOferta.setText("");
        edtPrecoOferta.setText("");
        edtSiteOferta.setText("");
        edtEstadoOferta.setText("");
        edtCidadeOferta.setText("");
        edtLocalOferta.setText("");
    }

    private boolean validarURL() {

        boolean r = true;
        if (!edtSiteOferta.getText().toString().isEmpty() && edtSiteOferta.getText().toString() != null) {
            try {
                if (!edtSiteOferta.getText().toString().trim().equals("") || edtSiteOferta.getText() != null) {
                    if (!edtSiteOferta.getText().toString().startsWith("http://") && !edtSiteOferta.getText().toString().startsWith("https://")) {
                        edtSiteOferta.setText("http://" + edtSiteOferta.getText().toString());
                    }
                }
                URL url = new URL(edtSiteOferta.getText().toString());
                url.toURI();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                edtSiteOferta.setError("Endereço URL inválido, tente algo como: www.meusite.com.br");
                r = false;
            } catch (URISyntaxException e) {
                e.printStackTrace();
                edtSiteOferta.setError("Endereço URL inválido, tente algo como: www.meusite.com.br");
                r = false;
            }
        }

        return r;
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            mUsuariosDatabaseReference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mUsuario = dataSnapshot.getValue(Usuario.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}

class MascaraMonetaria implements TextWatcher {

    final EditText campo;
    private boolean isUpdating = false;
    // Pega a formatacao do sistema, se for brasil R$ se EUA US$
    private NumberFormat nf = NumberFormat.getCurrencyInstance();

    public MascaraMonetaria(EditText campo) {
        super();
        this.campo = campo;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int after) {
        // Evita que o método seja executado varias vezes.
        // Se tirar ele entre em loop
        if (isUpdating) {
            isUpdating = false;
            return;
        }

        isUpdating = true;
        String str = s.toString();
        // Verifica se já existe a máscara no texto.
        boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1) &&
                (str.indexOf(".") > -1 || str.indexOf(",") > -1));
        // Verificamos se existe máscara
        if (hasMask) {
            // Retiramos a máscara.
            str = str.replaceAll("[R$]", "").replaceAll("[,]", "")
                    .replaceAll("[.]", "");
        }

        try {
            // Transformamos o número que está escrito no EditText em
            // monetário.
            str = nf.format(Double.parseDouble(str) / 100);
            campo.setText(str);
            campo.setSelection(campo.getText().length());
        } catch (NumberFormatException e) {
            s = "";
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // Não utilizado
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Não utilizado
    }
}


