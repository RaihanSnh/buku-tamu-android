package com.example.bukutamu.ui.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bukutamu.Konfigurasi;
import com.example.bukutamu.LoginActivity;
import com.example.bukutamu.MainActivity2;
import com.example.bukutamu.R;
import com.example.bukutamu.RequestHandler;
import com.example.bukutamu.databinding.FragmentAdminBinding;
import com.example.bukutamu.ui.dashboardadmin.DashboardAdminFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;


public class AdminFragment extends Fragment {

    private FragmentAdminBinding binding;

    FloatingActionButton login;

    EditText username, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        login = root.findViewById(R.id.btnLogin);
        username = root.findViewById(R.id.username);
        password = root.findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                class Login extends AsyncTask<Void, Void, String> {
                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(getActivity(), "Login..", "Mohon Tunggu", false, false);
                    }

                    @Override
                    protected void onPostExecute(String response) {
                        super.onPostExecute(response);
                        loading.dismiss();
                        if (response.equals("Proceed")) {
                            Intent intent = new Intent(getActivity(), MainActivity2.class);
                            intent.putExtra("username", username.getText().toString());
                            intent.putExtra("password", password.getText().toString());
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
