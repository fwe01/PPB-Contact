package com.example.ppbcontact;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.ppbcontact.adapter.ContactAdapter;
import com.example.ppbcontact.databinding.ActivityMainBinding;
import com.example.ppbcontact.model.Contact;
import com.example.ppbcontact.repository.ContactRepository;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ContactRepository contactRepository;

    private TextView alert_success, alert_danger;

    private void saveContact(String nama, String nomor_telp) {
        contactRepository.insert(Contact.create(nama, nomor_telp));
    }

    private ArrayList<Contact> searchContact(String search_string) {
        return contactRepository.searchContact(search_string);
    }

    private ArrayList<Contact> getAll() {
        return contactRepository.getAll();
    }

    private void updateList(ArrayList<Contact> contacts) {
        ContactAdapter contact_adapter = new ContactAdapter(this, R.layout.contact_list_item, contacts);

        ListView list_view = findViewById(R.id.contact_list_view);
        list_view.setAdapter(contact_adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contactRepository = ContactRepository.getInstance(this.getApplicationContext());

        alert_success = findViewById(R.id.txt_success);
        alert_danger = findViewById(R.id.txt_danger);

        EditText edt_cari_kontak = findViewById(R.id.edt_cari_kontak);

        Button btn_cari = findViewById(R.id.btn_cari_kontak);
        btn_cari.setOnClickListener(view -> {
            alert_danger.setVisibility(View.GONE);
            try {
                updateList(searchContact(edt_cari_kontak.getText().toString()));
            } catch (Exception e) {
                alert_danger.setText("Gagal mencari kontak");
                alert_danger.setVisibility(View.VISIBLE);
            }
        });

        binding.fab.setOnClickListener(view -> showAddDialog());

        //isi list untuk pertama kalinya
        updateList(getAll());
    }

    private void showAddDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View add_contact_dialog_view = inflater.inflate(R.layout.add_contact_dialog, null);

        AlertDialog.Builder add_contact_dialog = new AlertDialog.Builder(this);

        add_contact_dialog.setView(add_contact_dialog_view);

        EditText edt_nama = add_contact_dialog_view.findViewById(R.id.edt_nama);
        EditText edt_nomor_telp = add_contact_dialog_view.findViewById(R.id.edt_nomor_telp);

        add_contact_dialog
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alert_success.setVisibility(View.GONE);
                        alert_danger.setVisibility(View.GONE);
                        try {
                            saveContact(edt_nama.getText().toString(), edt_nomor_telp.getText().toString());
                            alert_success.setText("Kontak berhasil disimpan");
                            alert_success.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            alert_danger.setText("Gagal menyimpan kontak");
                            alert_danger.setVisibility(View.VISIBLE);
                        } finally {
                            updateList(getAll());
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
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