package com.renner.reposicao.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.renner.reposicao.R;
import com.renner.reposicao.constants.ApplicationConstants;
import com.renner.reposicao.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dico
 */
public class LoginActivity extends AppCompatActivity {

    private boolean isProgressBarShowing = false;

    private Context context;
    private Button btnEntrar;
    private Button btnProblemasAcesso;
    private EditText editTextLogin;
    private EditText editTextPassword;
    private Toast toast;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnProblemasAcesso = (Button) findViewById(R.id.btnProblemasAcesso);
        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        initWidgets();
    }

    /**
     * Starts the required widgets
     */
    private void initWidgets() {
        ApplicationConstants.currentActivity = this;
        context = LoginActivity.this;
        btnEntrar.setOnClickListener(clickedEntrar);
        btnProblemasAcesso.setOnClickListener(showLoginDialog);
        editTextPassword.setOnEditorActionListener(enteredPassword);
    }

    /**
     * Calls checkCredentials on the ENTRAR button press
     */
    View.OnClickListener clickedEntrar = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            checkCredentials();
        }
    };

    /**
     * Calls checkCredentials on the keyboard DONE press
     */
    EditText.OnEditorActionListener enteredPassword = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                checkCredentials();
            }
            return false;
        }
    };

    /**
     * This method validates if both User and Password are filled
     */
    private void checkCredentials() {
        String login = editTextLogin.getText().toString();
        String senha = editTextPassword.getText().toString();

        if (!login.isEmpty() && !senha.isEmpty()) {
            validateCredentials(login, senha);
            shouldDisplayProgressBar();
        } else {
            Toast toast = Toast.makeText(
                    context,
                    "Preencha os campos de login e senha!",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Validates the user credentials against the login service at: https://maratona.herokuapp.com/login.
     * In case of success: Calls the method that creates the currentUser for this session
     * In case of error: Displays the appropriate message
     */
    private void validateCredentials(final String login, final String senha) {

        String loginURI = String.format(ApplicationConstants.LOGIN_URL, login, senha);

        StringRequest postRequest = new StringRequest(Request.Method.POST, loginURI,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response).getJSONObject("data");
                            String nome = jsonResponse.getString("nome");
                            String lojaId = jsonResponse.getString("lojaId");

                            createCurrentUser(login, senha, nome, lojaId);

                        } catch (JSONException e) {
                            toast = Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
                            toast.show();
                            progressBar.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            shouldDisplayProgressBar();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toast = Toast.makeText(context, "Usuário e/ou Senha inválidos!", Toast.LENGTH_SHORT);
                        toast.show();
                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        shouldDisplayProgressBar();
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                // POST parameters:
                params.put("chapa", login);
                params.put("senha", senha);

                return params;
            }
        };

        Volley.newRequestQueue(this).add(postRequest);
    }

    /**
     * This method is used to create the currentUser which will persist until the logout
     */
    private void createCurrentUser(String login, String senha, String nome, String lojaId) {
        ApplicationConstants.currentUser = new User(login, senha, nome, lojaId);
        callSelectFunctionActivity();
    }

    /**
     * This method is used to call the SelectFunctionActivity
     */
    private void callSelectFunctionActivity() {
        shouldDisplayProgressBar();

        Intent intent = new Intent(LoginActivity.this, SelectFunctionActivity.class);
        startActivity(intent);
    }

    private void shouldDisplayProgressBar() {
        isProgressBarShowing = !isProgressBarShowing;

        if (isProgressBarShowing) {
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    View.OnClickListener showLoginDialog = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_login_screen);

            Button btnDialogLink = (Button) dialog.findViewById(R.id.btnDialogLink);
            Button btnDialogFechar = (Button) dialog.findViewById(R.id.btnDialogFechar);

            btnDialogLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(getResources().getString(R.string.str_login_dialog_link)));

                    startActivity(intent);
                }
            });

            btnDialogFechar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    };
}