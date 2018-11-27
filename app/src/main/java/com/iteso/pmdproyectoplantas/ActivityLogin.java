package com.iteso.pmdproyectoplantas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iteso.pmdproyectoplantas.tools.Constants;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityLogin extends AppCompatActivity {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*[0-9])(?=\\S+$).{6,}$");

    private EditText username;
    private EditText password;
    Button userSignInButton;
    TextView registrarse;
    LoginButton facebookLogInButton;
    ProgressBar progressBar;
    CallbackManager callbackManager;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        userSignInButton = (Button) findViewById(R.id.sign_in_button);
        facebookLogInButton = (LoginButton) findViewById(R.id.fb_login_button);
        registrarse = findViewById(R.id.activity_login_registrarse);
        progressBar = findViewById(R.id.progressBar);
        SpannableString spannableString = new SpannableString(registrarse.getText());
        spannableString.setSpan(new URLSpan("#"), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registrarse.setText(spannableString, TextView.BufferType.SPANNABLE);
        callbackManager = CallbackManager.Factory.create();

        mAuth = FirebaseAuth.getInstance();

        userSignInButton.setOnClickListener((View v)->{
            attemptLogin();
        });

        registrarse.setOnClickListener((View v)->{
            attemptSignUp();
        });

        facebookLogInButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        facebookLogInButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(ActivityLogin.this, (@NonNull Task<AuthResult> task) ->{
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    doLogin();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(ActivityLogin.this, getString(R.string.activity_login_task_fail)
                                     , Toast.LENGTH_SHORT).show();
                                }
                            }
                        );
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        if(mAuth.getCurrentUser() != null ) {
            doLogin();
            return;
        }

        //Stub para hacer más rápido mis pruebas
        username.setText("cianocrilato@hotmail.com");
        password.setText("123456");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
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
    public boolean validateLogin(String usr, String pwd) {
        boolean val = true;
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(usr);
        if(!matcher.find()) {
            username.setError(getString(R.string.activity_login_invalid_email));
            val = false;
        }

        matcher =VALID_PASSWORD_REGEX.matcher(pwd);
        if(!matcher.find()) {
            password.setError(getString(R.string.activity_login_invalid_password));
            return false;
        }

        return val;
    }

    protected void clearErrors(boolean clearAll) {
        username.setError(null);
        password.setError(null);
        if(clearAll) {
            username.setText("");
            password.setText("");
        }
    }

    private void attemptSignUp() {
        String user = username.getText().toString();
        String pass = password.getText().toString();
        if(!validateLogin(user, pass)) {
            return;
        }

        startShowProgress();
        mAuth.createUserWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, (@NonNull Task<AuthResult> task)->{
            if(task.isSuccessful()) {
                doLogin();
            } else {
                Toast.makeText(ActivityLogin.this, getString(R.string.activity_main_registro_fallido),
                        Toast.LENGTH_SHORT).show();
            }

            endShowProgress();
        });
    }

    private void attemptLogin() {
        // Store values at the time of the login attempt.
        String user = username.getText().toString();
        String pass = password.getText().toString();

        if(!validateLogin(user, pass)) {
            return;
        }

        startShowProgress();
        // [START sign_in_with_email] (@NonNull Task<AuthResult> task)
        mAuth.signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, (@NonNull Task<AuthResult> task)->{
                    if (task.isSuccessful()) {
                        doLogin();
                    } else {
                        Toast.makeText(ActivityLogin.this, getString(R.string.activity_login_auth_fail),
                                Toast.LENGTH_SHORT).show();
                    }

                    if (!task.isSuccessful()) {
                        Toast.makeText(ActivityLogin.this, getString(R.string.activity_login_task_fail)
                                , Toast.LENGTH_SHORT).show();;
                    }

                    endShowProgress();
                });
        // [END sign_in_with_email]
    }

    private void doLogin() {
        //FirebaseDatabase.getInstance().getReference("users").child("KCeb2n1Ib6aJHY2tyi1LuoGQkIi2").child("plants").child("12345").
        SharedPreferences.Editor editor = getSharedPreferences(Constants.sharedKeyName, MODE_PRIVATE).edit();
        editor.putString(Constants.sharedKeyUID, mAuth.getUid());
        editor.commit();

        Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
        startActivity(intent);
        finish();
    }

    public void startShowProgress() {
        progressBar.setVisibility(View.VISIBLE);
        userSignInButton.setEnabled(false);
        facebookLogInButton.setEnabled(false);
        registrarse.setEnabled(false);
    }

    public void endShowProgress() {
        progressBar.setVisibility(View.GONE);
        userSignInButton.setEnabled(true);
        facebookLogInButton.setEnabled(true);
        registrarse.setEnabled(true);
    }
}

