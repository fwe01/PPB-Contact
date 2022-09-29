package com.example.ppbcontact.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ppbcontact.DetailContact;
import com.example.ppbcontact.R;
import com.example.ppbcontact.model.Contact;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contact contact = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_item, parent, false);
        }

        TextView txt_nama = convertView.findViewById(R.id.txt_nama);
        TextView txt_nomor_hp = convertView.findViewById(R.id.txt_nomor_hp);

        txt_nama.setText("Nama : " + contact.getNama());
        txt_nomor_hp.setText("Nomor HP : " + contact.getNomorTelp());

        LinearLayout linear_layout = convertView.findViewById(R.id.contact_list_item_linear_layout);
        linear_layout.setOnClickListener(
                view -> {
                    Intent intent = new Intent(getContext(), DetailContact.class);
                    intent.putExtra("id", contact.getId());
                    getContext().startActivity(intent);
                }
        );

        return convertView;
    }
}
