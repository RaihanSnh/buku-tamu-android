package com.syahrul.bukutamu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;

    private String JSON_STRING;

    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        id = intent.getStringExtra(Konfigurasi.TAG_ID);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        getJSON();
    }

    public String lainnya(String config){
        if(config == null){
            return "Belum Diterima";
        }
        else{
            return config;
        }
    }

    private void showData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new
                ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result =
                    jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi.TAG_ID);
                String nama = jo.getString(Konfigurasi.TAG_NAMA);
                String alamat = jo.getString(Konfigurasi.TAG_ALAMAT);
                String petugas = jo.getString(Konfigurasi.TAG_PETUGAS);
                String keterangan = jo.getString(Konfigurasi.TAG_KETERANGAN);
                String lainnya = jo.getString(Konfigurasi.TAG_LAINNYA);
                HashMap<String, String> data = new HashMap<>();
                data.put(Konfigurasi.TAG_ID, id);
                data.put(Konfigurasi.TAG_NAMA, nama);
                data.put(Konfigurasi.TAG_ALAMAT, alamat);
                data.put(Konfigurasi.TAG_PETUGAS, petugas);
                data.put(Konfigurasi.TAG_KETERANGAN, keterangan);
                data.put(Konfigurasi.TAG_LAINNYA, lainnya);
                list.add(data);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ListActivity.this, list, R.layout.card,
                new String[]{
                        Konfigurasi.TAG_ID,
                        Konfigurasi.TAG_NAMA,
                        Konfigurasi.TAG_ALAMAT,
                        Konfigurasi.TAG_PETUGAS,
                        Konfigurasi.TAG_KETERANGAN,
                        lainnya(Konfigurasi.TAG_LAINNYA)},
                new int[]{
                        R.id.id,
                        R.id.nama,
                        R.id.alamat,
                        R.id.petugas,
                        R.id.keterangan,
                        R.id.lainnya});
        listView.setAdapter(adapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ListActivity.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showData();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Konfigurasi.URL_GET_DATA);
                return s;
            }

        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    //@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, EditActivity.class);
        HashMap <String, String> map = (HashMap) parent.getItemAtPosition(position);
//        String id = map.get(Konfigurasi.TAG_ID).toString();
        intent.putExtra(Konfigurasi.KEY_NAMA, id);
        intent.putExtra(Konfigurasi.KEY_ALAMAT, id);
        intent.putExtra(Konfigurasi.KEY_PETUGAS, id);
        intent.putExtra(Konfigurasi.KEY_KETERANGAN, id);
        intent.putExtra(Konfigurasi.KEY_LAINNYA, id);
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    private void deleteSiswa(){
        class DeleteSiswa extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ListActivity.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ListActivity.this, s, Toast.LENGTH_LONG).show();
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
}