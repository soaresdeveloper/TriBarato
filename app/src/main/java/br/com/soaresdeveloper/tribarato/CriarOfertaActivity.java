package br.com.soaresdeveloper.tribarato;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.soaresdeveloper.tribarato.Utils.ViewUtils;
import br.com.soaresdeveloper.tribarato.entidades.Oferta;
import br.com.soaresdeveloper.tribarato.entidades.Usuario;

public class CriarOfertaActivity extends AppCompatActivity {

    EditText edtTituloOferta, edtDescricaoOferta, edtPrecoOferta, edtEnderecoOferta;
    Button btnPublicarOferta, btnLimparCampos;
    ProgressBar mProgressBar;

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
        edtEnderecoOferta = (EditText) findViewById(R.id.enderecoOferta);
        btnPublicarOferta = (Button) findViewById(R.id.btnPublicarOferta);
        btnLimparCampos = (Button) findViewById(R.id.btnLimparCampos);
        mProgressBar = (ProgressBar) findViewById(R.id.progressPbOferta);

        // Inicializacao componentes Firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mOfertasDatabaseReference = mFirebaseDatabase.getReference().child("ofertas");
        mUsuariosDatabaseReference = mFirebaseDatabase.getReference().child("usuarios");
        auth = FirebaseAuth.getInstance();

        btnPublicarOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<EditText> campos = new ArrayList<EditText>();
                campos.add(edtTituloOferta);
                campos.add(edtDescricaoOferta);
                campos.add(edtEnderecoOferta);
                campos.add(edtPrecoOferta);

                if (ViewUtils.validarCampos(campos)) {

                    mProgressBar.setVisibility(View.VISIBLE);

                    String titulo = edtTituloOferta.getText().toString();
                    String descricao = edtDescricaoOferta.getText().toString();
                    String preco = edtPrecoOferta.getText().toString();
                    String endereco = edtEnderecoOferta.getText().toString();

                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date date = new Date();
                    String data = dateFormat.format(date);

                    oferta = new Oferta(mUsuario, titulo, descricao, preco, endereco, data);
                    mOfertasDatabaseReference.push().setValue(oferta).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mProgressBar.setVisibility(View.INVISIBLE);
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
