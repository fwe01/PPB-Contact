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
import androidx.recyclerview.widget.RecyclerView;

import com.example.ppbcontact.DetailContact;
import com.example.ppbcontact.R;
import com.example.ppbcontact.model.Contact;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    static class ViewHolder {
        TextView txt_nama;
        TextView txt_nomor_hp;
        LinearLayout linear_layout;
    }

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contact contact = getItem(position);

        ViewHolder viewContact;
        if (convertView == null) {
            viewContact = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_item, parent, false);

            viewContact.txt_nama = convertView.findViewById(R.id.txt_nama);
            viewContact.txt_nomor_hp = convertView.findViewById(R.id.txt_nomor_hp);
            viewContact.linear_layout = convertView.findViewById(R.id.contact_list_item_linear_layout);

            convertView.setTag(viewContact);
        } else {
            viewContact = (ViewHolder) convertView.getTag();
        }

        viewContact.txt_nama.setText("Nama : " + contact.getNama());
        viewContact.txt_nomor_hp.setText("Nomor HP : " + contact.getNomorTelp());
        viewContact.linear_layout.setOnClickListener(
                view -> {
                    Intent intent = new Intent(getContext(), DetailContact.class);
                    intent.putExtra("id", contact.getId());
                    getContext().startActivity(intent);
                }
        );

        return convertView;
    }
}
