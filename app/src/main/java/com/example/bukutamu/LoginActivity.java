package com.example.bukutamu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    Button login;

    TextInputEditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_admin);

        login = findViewById(R.id.btnLogin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                class Login extends AsyncTask<Void, Void, String> {
                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(LoginActivity.this, "Login..", "Mohon Tunggu", false, false);
                    }

                    @Override
                    protected void onPostExecute(String response) {
                        super.onPostExecute(response);
                        loading.dismiss();
                        if (response.equals("Proceed")) {
                            Intent intent = new Intent(LoginActivity.this, LihatDataTamu.class);
                            intent.putExtra("username", username.getText().toString());
                            intent.putExtra("password", password.getText().toString());
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
                            password.setText("");
                        }
                    }

                    @Override
                    protected String doInBackground(Void... voids) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("username", username.getText().toString());
                        params.put("password", password.getText().toString());

                        RequestHandler requestHandler = new RequestHandler();
                        String response = requestHandler.sendPostRequest(Konfigurasi.URL_LOGIN, params);
                        return response;
                    }
                }

                Login loginAdmin = new Login();
                loginAdmin.execute();
            }
        });

    }
}
