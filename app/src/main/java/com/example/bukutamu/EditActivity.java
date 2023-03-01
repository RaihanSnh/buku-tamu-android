package com.example.bukutamu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bukutamu.ui.listtamu.ListTamuFragment;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    private Button Terima, Delete;
    private String id;
    private TextView iddata, tvNama, tvAlamat, tvPetugas, tvKeterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        id = intent.getStringExtra(Konfigurasi.KEY_ID);

        Terima = findViewById(R.id.btnTerima);
        Delete = findViewById(R.id.btnDelete);

        iddata = findViewById(R.id.iddata);
        tvNama = findViewById(R.id.nama);
        tvAlamat = findViewById(R.id.alamat);
        tvPetugas = findViewById(R.id.hp);
        tvKeterangan = findViewById(R.id.keperluan);

        Terima.setOnClickListener(this);
        Delete.setOnClickListener(this);

        iddata.setText(id);

        getSiswa();
    }

    private void getSiswa(){
        class GetSiswa extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditActivity.this,"...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showSiswa(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_GET_TAMPIL,id);
                return s;
            }
        }
        GetSiswa ge = new GetSiswa();
        ge.execute();
    }

    private void showSiswa(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String nama = c.getString(Konfigurasi.TAG_NAMA);
            String alamat = c.getString(Konfigurasi.TAG_ALAMAT);
            String petugas = c.getString(Konfigurasi.TAG_HP);
            String keterangan = c.getString(Konfigurasi.TAG_KEPERLUAN);

            tvNama.setText(nama);
            tvAlamat.setText(alamat);
            tvPetugas.setText(petugas);
            tvKeterangan.setText(keterangan);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateSiswa(){
        final String lainnya = "DIterima";

        class UpdateSiswa extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditActivity.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(EditActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Konfigurasi.TAG_ID,id);
                hashMap.put(Konfigurasi.TAG_LAINNYA, lainnya);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Konfigurasi.URL_UPDATE,hashMap);

                return s;
            }
        }

        UpdateSiswa ue = new UpdateSiswa();
        ue.execute();
    }

    private void deleteSiswa(){
        class DeleteSiswa extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditActivity.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(EditActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_DELETE, id);
                return s;
            }
        }

        DeleteSiswa de = new DeleteSiswa();
        de.execute();
    }

    private void confirmDeleteSiswa(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Data ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteSiswa();
                        startActivity(new Intent(EditActivity.this,     ListTamuFragment.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v == Terima){
            updateSiswa();

        }

        if(v == Delete){
            confirmDeleteSiswa();
        }
    }

    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), ListTamuFragment.class);
        startActivity(i);
        finish();
    }
}