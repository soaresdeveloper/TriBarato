package br.com.soaresdeveloper.tribarato;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.soaresdeveloper.tribarato.Utils.ViewUtils;
import br.com.soaresdeveloper.tribarato.adapter.OfertaAdapter;
import br.com.soaresdeveloper.tribarato.entidades.Oferta;

public class MainActivity extends AppCompatActivity {

    public static final String ANONIMO = "anônimo";
    public static final String ONLINE = "Você está conectado!";
    public static final int RC_SIGN_IN = 1;

    Button btnCriarOferta;
    ListView mOfertasListView;


    private String mUsuario;
    private OfertaAdapter mOfertaAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mOfertasDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
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
        mAuth = FirebaseAuth.getInstance();
        mOfertasDatabaseReference = mFirebaseDatabase.getReference().child("ofertas");


        // Initialize ofertas ListView and its adapter
        final List<Oferta> ofertas = new ArrayList<>();
        mOfertaAdapter = new OfertaAdapter(this, R.layout.item_oferta, ofertas);
        mOfertasListView.setAdapter(mOfertaAdapter);

        // Chamar Progress
        ViewUtils.chamarProgress(MainActivity.this, "Buscando Ofertas...");


        btnCriarOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CriarOfertaActivity.class);
                startActivity(intent);
            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // usuario esta logado
                    onSignedInInitializa();
                } else {
                    // usuario não esta logado
                    onSignedOutCleanup();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            ;
        };

    }


    @Override
    protected void onPause() {
        super.onPause();

        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabaseReadListener();
        mOfertaAdapter.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Vocês está conectado!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Configura o nome do usuario
    private void onSignedInInitializa() {
        attachDatabaseReadListener();
    }

    // Limpar todas as informações de usuario e mensagem quando ele desconectar
    private void onSignedOutCleanup() {
        mOfertaAdapter.clear();
        detachDatabaseReadListener();
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mOfertasDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            // Sincroniza o banco com a aplicacao
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
            ViewUtils.dismissProgress();
        }
    }


}
