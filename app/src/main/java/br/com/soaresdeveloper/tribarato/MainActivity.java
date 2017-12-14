package br.com.soaresdeveloper.tribarato;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.soaresdeveloper.tribarato.adapter.OfertaAdapter;
import br.com.soaresdeveloper.tribarato.entidades.Oferta;

public class MainActivity extends AppCompatActivity {

    public static final String ANONIMO = "anônimo";

    Button btnCriarOferta;
    ListView mOfertasListView;

    private String mUsuario;
    private OfertaAdapter mOfertaAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mOfertasDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCriarOferta = (Button) findViewById(R.id.btnCriarOferta);
        mOfertasListView = (ListView) findViewById(R.id.listOfertas);

        mUsuario = ANONIMO;

        // Inicialização componentes Firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mOfertasDatabaseReference = mFirebaseDatabase.getReference().child("ofertas");


        // Initialize ofertas ListView and its adapter
        final List<Oferta> ofertas = new ArrayList<>();
        mOfertaAdapter = new OfertaAdapter(this, R.layout.item_oferta, ofertas);
        mOfertasListView.setAdapter(mOfertaAdapter);


        btnCriarOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CriarOfertaActivity.class);
                startActivity(intent);
            }
        });

        // Sincroniza as ofertas com o banco
        if (mChildEventListener == null) {
            // Sincroniza o banco com a aplicacao
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    // referencia a mensagem que foi adicionada e converte na classe FriendlyMessage que possui os mesmos atributos do nós messages
                    Oferta novaOferta = dataSnapshot.getValue(Oferta.class);
                    mOfertaAdapter.add(novaOferta);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Oferta ofertaExcluida = dataSnapshot.getValue(Oferta.class);
                    mOfertaAdapter.remove(ofertaExcluida);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };

            // Adiciona o Listener que escuta apenas o child messages
            mOfertasDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mOfertaAdapter.clear();
    }
}
