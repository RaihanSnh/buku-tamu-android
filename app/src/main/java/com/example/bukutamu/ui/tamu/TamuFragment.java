package com.example.bukutamu.ui.tamu;

import android.app.ProgressDialog;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bukutamu.Konfigurasi;
import com.example.bukutamu.R;
import com.example.bukutamu.RequestHandler;
import com.example.bukutamu.databinding.FragmentTamuBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class TamuFragment extends Fragment {
    private EditText editTextNama, editTextHp, editTextAlamat, editTextKeperluan;
    Button submit;

    private FragmentTamuBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_tamu, container, false);
        editTextNama = rootView.findViewById(R.id.eTnama);
        editTextHp = rootView.findViewById(R.id.eThp);
        editTextAlamat = rootView.findViewById(R.id.eTalamat);
        editTextKeperluan = rootView.findViewById(R.id.eTkeperluan);


        TamuViewModel tamuViewModel =
                new ViewModelProvider(this).get(TamuViewModel.class);

        binding = FragmentTamuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTamu;
        tamuViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        submit = root.findViewById(R.id.submitform);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama = editTextNama.getText().toString().trim();
                final String hp = editTextHp.getText().toString().trim();
                final String alamat = editTextAlamat.getText().toString().trim();
                final String keperluan = editTextKeperluan.getText().toString().trim();


                class AddTamu extends AsyncTask<Void, Void, String> {

                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(getActivity(),
                                "Menambahkan...", "Tunggu...", false, false);

                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected String doInBackground(Void... v) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put(Konfigurasi.KEY_NAMA, nama);
                        params.put(Konfigurasi.KEY_HP, hp);
                        params.put(Konfigurasi.KEY_ALAMAT, alamat);
                        params.put(Konfigurasi.KEY_KEPERLUAN, keperluan);

                        RequestHandler rh = new RequestHandler();
                        String res = rh.sendPostRequest(Konfigurasi.URL_ADD, params);
                        return res;
                    }
                }

                AddTamu ae = new AddTamu();
                ae.execute();
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