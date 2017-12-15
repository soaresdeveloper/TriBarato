package br.com.soaresdeveloper.tribarato;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import br.com.soaresdeveloper.tribarato.Utils.ViewUtils;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "TRI BARATO ";

    Button btnEntrar, btnCadastrar;
    ProgressBar mProgressBar;
    EditText mEmail, mSenha;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (EditText) findViewById(R.id.emailEntrar);
        mSenha = (EditText) findViewById(R.id.senhaEntrar);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        // Inicializacao componentes Firebase
        mAuth = FirebaseAuth.getInstance();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<EditText> campos = new ArrayList<EditText>();
                campos.add(mEmail);
                campos.add(mSenha);

                if (ViewUtils.validarCampos(campos)) {

                    String email = mEmail.getText().toString();
                    String password = mSenha.getText().toString();
                    mProgressBar.isIndeterminate();
                    mProgressBar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        mProgressBar.setVisibility(View.INVISIBLE);

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        ViewUtils.chamarToast(LoginActivity.this, "Ocorreu uma falha durante a autenticação, tente novamente");
                                    }
                                }
                            });
                }

            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,CadastrarActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
