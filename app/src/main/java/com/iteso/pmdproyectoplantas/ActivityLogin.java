package com.iteso.pmdproyectoplantas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class ActivityLogin extends AppCompatActivity {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
        // private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText username;
    private EditText password;
                //private View mProgressView;
                //private View mLoginFormView;
    Button userSignInButton, googleSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        username = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        userSignInButton = (Button) findViewById(R.id.sign_in_button);
        googleSignInButton = (Button) findViewById(R.id.google_sign_in_button);
            //mLoginFormView = findViewById(R.id.login_form);
            //mProgressView = findViewById(R.id.login_progress);


        userSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        userSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
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
       /*
        if (mAuthTask != null) {
            return;
        }*/


        // Reset errors.
        username.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String user = username.getText().toString();
        String pass = this.password.getText().toString();

        boolean cancel = false;
        //View focusView = null;


        if(!user.isEmpty() && pass.isEmpty()) {
            cancel = true;
            Toast.makeText(this, R.string.error_invalid_password, Toast.LENGTH_LONG).show();
            password.setText("");
        }

        if(!pass.isEmpty() && user.isEmpty()) {
            cancel = true;
            Toast.makeText(this, R.string.error_invalid_username, Toast.LENGTH_LONG).show();
            password.setText("");
        }


        // Check for a valid password, if the user entered one.
        if (!pass.isEmpty() && !isPasswordValid(pass)) {
            this.password.setError(getString(R.string.error_invalid_password));
            //focusView = this.password;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(user)) {
            username.setError(getString(R.string.error_field_required));
            //focusView = username;
            cancel = true;
        } else if (!isUsernameValid(user)) {
            username.setError(getString(R.string.error_invalid_username));
            //focusView = username;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            //focusView.requestFocus();

            username.setText("");
            password.setText("");
        } else {
            //Ir a activityMain al estar todo correcto.
            Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
            startActivity(intent);
            finish();


            /*
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(user, password);
            mAuthTask.execute((Void) null);
            */
        }
    }

    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        // comprobación de Firebase
        return true; //username.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    /*
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
*/




    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */

    /*
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                password.setError(getString(R.string.error_incorrect_password));
                password.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    */


}

