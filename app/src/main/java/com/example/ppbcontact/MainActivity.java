package com.example.ppbcontact;

import android.content.Intent;
import android.os.Bundle;

import com.example.ppbcontact.model.Contact;
import com.example.ppbcontact.repository.ContactRepository;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ContactRepository contactRepository;

    private Button btn_simpan, btn_cari;
    private TextView alert_success, alert_danger;
    private EditText edt_nama, edt_nomor_telp, edt_cari_kontak;
    private LinearLayout scroll_contact_linear;

    private void saveContact(View v) {
        String nama = edt_nama.getText().toString();
        String nomor_telp = edt_nomor_telp.getText().toString();

        contactRepository.insert(Contact.create(nama, nomor_telp));
    }

    private ArrayList<Contact> searchContact(String nama) {
        return contactRepository.searchByName(nama);
    }

    private ArrayList<Contact> getAll() {
        return contactRepository.getAll();
    }

    private void updateList(ArrayList<Contact> contacts) {
        scroll_contact_linear.removeAllViews();

        for (Contact contact : contacts) {
            LinearLayout contact_item = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 0);
            contact_item.setLayoutParams(params);
            contact_item.setOrientation(LinearLayout.VERTICAL);
            contact_item.setPadding(50, 50, 50, 50);
            contact_item.setBackgroundResource(R.drawable.contact_item_background);

            TextView contact_item_nama = new TextView(this);
            contact_item_nama.setText("Nama : " + contact.getNama());
            contact_item_nama.setTextColor(getResources().getColor(R.color.white));

            TextView contact_item_nomor_telp = new TextView(this);
            contact_item_nomor_telp.setText("Nomor : " + contact.getNomorTelp());
            contact_item_nomor_telp.setTextColor(getResources().getColor(R.color.white));

            contact_item.addView(contact_item_nama);
            contact_item.addView(contact_item_nomor_telp);

            contact_item.setOnClickListener(view -> {
                Intent intent = new Intent(getBaseContext(), DetailContact.class);
                intent.putExtra("id", contact.getId());
                startActivity(intent);
            });

            scroll_contact_linear.addView(contact_item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        contactRepository = ContactRepository.getInstance(this.getApplicationContext());

        alert_success = findViewById(R.id.txt_success);
        alert_danger = findViewById(R.id.txt_danger);

        edt_nama = findViewById(R.id.edt_nama);
        edt_nomor_telp = findViewById(R.id.edt_nomor_telp);
        edt_cari_kontak = findViewById(R.id.edt_cari_kontak);

        scroll_contact_linear = findViewById(R.id.scroll_contact_linear);

        btn_simpan = findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(view -> {
            alert_success.setVisibility(View.GONE);
            alert_danger.setVisibility(View.GONE);
            try {
                saveContact(view);
                alert_success.setText("Kontak berhasil disimpan");
                alert_success.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                alert_danger.setText("Gagal menyimpan kontak");
                alert_danger.setVisibility(View.VISIBLE);
            } finally {
                updateList(getAll());
            }
        });

        btn_cari = findViewById(R.id.btn_cari_kontak);
        btn_cari.setOnClickListener(view -> {
            alert_danger.setVisibility(View.GONE);
            try {
                updateList(searchContact(edt_cari_kontak.getText().toString()));
            } catch (Exception e) {
                alert_danger.setText("Gagal menyimpan kontak");
                alert_danger.setVisibility(View.VISIBLE);
            }
        });

        //isi list untuk pertama kalinya
        updateList(getAll());
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateList(getAll());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}