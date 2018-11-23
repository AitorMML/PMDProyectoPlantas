package com.iteso.pmdproyectoplantas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityLogin extends AppCompatActivity {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*[0-9])(?=\\S+$).{6,}$");


    private EditText username;
    private EditText password;
    Button userSignInButton, googleSignInButton;

    private FirebaseAuth mAuth;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        userSignInButton = (Button) findViewById(R.id.sign_in_button);
        googleSignInButton = (Button) findViewById(R.id.google_sign_in_button);

        mAuth = FirebaseAuth.getInstance();

        userSignInButton.setOnClickListener((View v)->{
            attemptLogin();
        });
        googleSignInButton.setOnClickListener((View v)->{
                Toast.makeText(ActivityLogin.this, "G+", Toast.LENGTH_SHORT).show();
        });

        preferences = getPreferences(MODE_PRIVATE);

        //Stub para hacer más rápido mis pruebas
        username.setText("cianocrilato@hotmail.com");
        password.setText("1234567");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Login Mediante FireBase.
     *
     * Si introduce un usuario y no pone contraseña, pide contraseña.
     * Si sólo pone una contraseña, pide un usuario.
     * Si introduce usuario y contaseña y el primero no existe, Firebase crea
     *      el usuario con esa contraseña y manda a ActivityMain.
     * Si introduce un usuario ya existente en firebase con la contraseña equivocada,
     *      rechaza el intento.
     * Si introduce un usuario ya existente en Firebase con usuario y contraseña correcta,
     *      manda a ActivityMain.
     */
    private void attemptLogin() {
        boolean doContinue = true;
        String uid = getPreferences(MODE_PRIVATE).getString("UID", null);

        /*if(uid != null) {
            doLogin(uid);
            return;
        }*/

        // Reset errors.
        username.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String user = username.getText().toString();
        String pass = this.password.getText().toString();

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(user);
        if(!matcher.find()) {
            username.setError("Correo inválido");
            doContinue = false;
        }

        matcher =VALID_PASSWORD_REGEX.matcher(pass);
        if(!matcher.find()) {
            password.setError("Contraseña inválida");
            doContinue = false;
        }

        if(!doContinue) {
            return;
        }


        // [START sign_in_with_email] (@NonNull Task<AuthResult> task)
        mAuth.signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, (@NonNull Task<AuthResult> task)->{
                    if (task.isSuccessful()) {
                        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                        editor.putString("UID", mAuth.getUid());
                        editor.commit();

                        doLogin(mAuth.getUid());
                    } else {
                        Toast.makeText(ActivityLogin.this, "Autenticación fallida",
                                Toast.LENGTH_SHORT).show();
                        username.setError(null);
                        username.setText("");
                        password.setError(null);
                        password.setText("");
                    }

                    if (!task.isSuccessful()) {
                        Toast.makeText(ActivityLogin.this, "Task no terminado"
                                , Toast.LENGTH_SHORT).show();;
                    }
                });
        // [END sign_in_with_email]
    }

    private void doLogin(String uid) {
        //FirebaseDatabase.getInstance().getReference("users").child("KCeb2n1Ib6aJHY2tyi1LuoGQkIi2").child("plants").child("12345").

        Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
        startActivity(intent);
        finish();
    }
}

