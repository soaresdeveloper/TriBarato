package br.com.soaresdeveloper.tribarato;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.soaresdeveloper.tribarato.Utils.ViewUtils;
import br.com.soaresdeveloper.tribarato.entidades.Oferta;

public class CriarOfertaActivity extends AppCompatActivity {

    EditText edtTituloOferta, edtDescricaoOferta, edtPrecoOferta, edtEnderecoOferta;
    Button btnPublicarOferta, btnLimparCampos;

    Oferta oferta;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mOfertasDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_oferta);

        edtTituloOferta = (EditText) findViewById(R.id.tituloOferta);
        edtDescricaoOferta = (EditText) findViewById(R.id.descricaoOferta);
        edtPrecoOferta = (EditText) findViewById(R.id.precoOferta);
        edtEnderecoOferta = (EditText) findViewById(R.id.enderecoOferta);
        btnPublicarOferta = (Button) findViewById(R.id.btnPublicarOferta);
        btnLimparCampos = (Button) findViewById(R.id.btnLimparCampos);

        // Inicializacao componentes Firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mOfertasDatabaseReference = mFirebaseDatabase.getReference().child("ofertas");

        btnPublicarOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<EditText> campos = new ArrayList<EditText>();
                campos.add(edtTituloOferta);
                campos.add(edtDescricaoOferta);
                campos.add(edtEnderecoOferta);
                campos.add(edtPrecoOferta);

                if (ViewUtils.validarCampos(campos)) {

                    String titulo = edtTituloOferta.getText().toString();
                    String descricao = edtDescricaoOferta.getText().toString();
                    String preco = edtPrecoOferta.getText().toString();
                    String endereco = edtEnderecoOferta.getText().toString();

                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date date = new Date();
                    String data = dateFormat.format(date);

                    oferta = new Oferta(null, titulo, descricao, preco, endereco,data);
                    mOfertasDatabaseReference.push().setValue(oferta).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            ViewUtils.chamarToast(CriarOfertaActivity.this, "Oferta publicada com sucesso!");
                            limparCampos();
                        }
                    });
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
        edtEnderecoOferta.setText("");
    }
}
