package br.com.soaresdeveloper.tribarato;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.soaresdeveloper.tribarato.Utils.ViewUtils;
import br.com.soaresdeveloper.tribarato.entidades.Usuario;

public class CadastrarActivity extends AppCompatActivity {

    private static final String TAG = " TRI BARATO ";

    EditText mNome, mSobrenome, mEmail, mSenha, mConfirmaSenha, mDataNascimento;
    Spinner mSpinnerEstado;
    RadioGroup groupSexo;
    RadioButton rbMasculino, rbFeminino;
    Button btnCadastrar;
    ProgressBar mProgressBar;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsuarioDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        mNome = (EditText) findViewById(R.id.nome);
        mSobrenome = (EditText) findViewById(R.id.sobrenome);
        mEmail = (EditText) findViewById(R.id.emailCadastro);
        mSenha = (EditText) findViewById(R.id.senha);
        mConfirmaSenha = (EditText) findViewById(R.id.confirmasenha);
        mDataNascimento = (EditText) findViewById(R.id.dataNascimento);
        mSpinnerEstado = (Spinner) findViewById(R.id.spEstado);
        groupSexo = (RadioGroup) findViewById(R.id.groupSexo);
        rbMasculino = (RadioButton) findViewById(R.id.rbMasculino);
        rbFeminino = (RadioButton) findViewById(R.id.rbFeminino);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrarUsuario);
        mProgressBar = (ProgressBar) findViewById(R.id.progressCdUsuario);

        // Inicializacao comp Firebase
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUsuarioDatabaseReference = mFirebaseDatabase.getReference().child("usuarios");

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<EditText> campos = new ArrayList<EditText>();
                campos.add(mNome);
                campos.add(mSobrenome);
                campos.add(mEmail);
                campos.add(mConfirmaSenha);
                campos.add(mSenha);
                campos.add(mDataNascimento);

                if(ViewUtils.validarCampos(campos)){
                    if (validarSenha()){

                        mProgressBar.setVisibility(View.VISIBLE);

                        final String nome = mNome.getText().toString();
                        final String sobrenome = mSobrenome.getText().toString();
                        String email = mEmail.getText().toString();
                        String password = mSenha.getText().toString();
                        final String estado = mSpinnerEstado.getSelectedItem().toString();
                        final String dataNascimento = mDataNascimento.getText().toString();
                        final String sexo = (rbMasculino.isChecked() ? "Masculino" : "Feminino");

                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(CadastrarActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "createUserWithEmail:success");
                                            mProgressBar.setVisibility(View.INVISIBLE);
                                            FirebaseUser user = mAuth.getCurrentUser();

                                            // cadastro usuario
                                            Usuario usuario = new Usuario(nome,sobrenome,estado,dataNascimento,sexo);
                                            mUsuarioDatabaseReference.child(user.getUid()).setValue(usuario);

                                            ViewUtils.chamarToast(CadastrarActivity.this,"Cadastro efetuado com sucesso!");
                                            Intent intent = new Intent(CadastrarActivity.this,MainActivity.class);
                                            startActivity(intent);

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            ViewUtils.chamarToast(CadastrarActivity.this,"Ocorreu um erro durante o cadastro, tente novamente.");                                 }

                                    }
                                });
                    }else {
                        mSenha.setError("As senhas não correspondem!");
                    }
                }
            }
        });

    }

    private boolean validarSenha(){
        if (mSenha.getText().toString().equals(mConfirmaSenha.getText().toString())){
            return true;
        }else {
            return false;
        }
    }
}
