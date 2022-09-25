package com.example.ppbcontact;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.ppbcontact.model.Contact;
import com.example.ppbcontact.repository.ContactRepository;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class DetailContact extends AppCompatActivity {
    private ContactRepository contactRepository;

    private TextView alert_danger;
    private Button btn_simpan, btn_kembali, btn_hapus;
    private EditText edt_nama, edt_nomor_telp;

    private int id;
    private Contact contact;

    private void initData() {
        contact = contactRepository.get(id);

        if (contact != null) {
            edt_nama.setText(contact.getNama());
            edt_nomor_telp.setText(contact.getNomorTelp());
        }
    }

    private void updateContact() {
        String nama = edt_nama.getText().toString();
        String nomor_telp = edt_nomor_telp.getText().toString();

        contactRepository.update(new Contact(contact.getId(), nama, nomor_telp));
    }

    private void deleteContact() {
        contactRepository.delete(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.id = getIntent().getIntExtra("id", 0);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_contact);

        alert_danger = findViewById(R.id.txt_danger);

        edt_nama = findViewById(R.id.edt_nama);
        edt_nomor_telp = findViewById(R.id.edt_nomor_telp);

        contactRepository = ContactRepository.getInstance(this.getApplicationContext());

        initData();

        btn_simpan = findViewById(R.id.btn_simpan);
        initSimpanListener();

        btn_kembali = findViewById(R.id.btn_kembali);
        initKembaliListener();

        btn_hapus = findViewById(R.id.btn_hapus);
        iniHapusListener();
    }

    private void initKembaliListener() {
        btn_kembali.setOnClickListener(view -> finish());
    }

    private void initSimpanListener() {
        btn_simpan.setOnClickListener(view -> {
            alert_danger.setVisibility(View.GONE);
            try {
                updateContact();
                new AlertDialog.Builder(this)
                        .setTitle("Success")
                        .setMessage("Kontak berhasil di update")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
            } catch (Exception e) {
                alert_danger.setText("Gagal menyimpan kontak");
                alert_danger.setVisibility(View.VISIBLE);
            }
        });
    }

    private void iniHapusListener() {
        btn_hapus.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete entry")
                    .setMessage("Apakah anda yakin untuk menghapus kontak ini ?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alert_danger.setVisibility(View.GONE);
                            try {
                                deleteContact();
                                new AlertDialog.Builder(DetailContact.this)
                                        .setTitle("Success")
                                        .setMessage("Kontak berhasil di hapus")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        })
                                        .show();
                            } catch (Exception ignored) {
                                alert_danger.setText("Gagal menghapus kontak");
                                alert_danger.setVisibility(View.VISIBLE);
                            }
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        });
    }

}