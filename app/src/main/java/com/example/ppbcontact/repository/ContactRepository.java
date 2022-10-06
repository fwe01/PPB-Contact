package com.example.ppbcontact.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ppbcontact.model.Contact;

import java.util.ArrayList;

public class ContactRepository {
    public static ContactRepository instance = null;
    private SQLiteOpenHelper openDb;
    private final SQLiteDatabase database;

    public ContactRepository(Context context) {
        this.openDb = new SQLiteOpenHelper(context, "db.sql", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };

        database = openDb.getWritableDatabase();
        database.execSQL("create table if not exists contact (id integer primary key autoincrement, nama varchar(255) not null, nomor_telp varchar(255) not null, unique(nama, nomor_telp))");
    }

    public static ContactRepository getInstance(Context context) {
        if (instance == null)
            instance = new ContactRepository(context);

        return instance;
    }

    public void insert(Contact contact) {
        ContentValues newData = new ContentValues();

        newData.put("nama", contact.getNama());
        newData.put("nomor_telp", contact.getNomorTelp());

        database.insert("contact", null, newData);
    }

    public void update(Contact contact) {
        ContentValues updateData = new ContentValues();

        updateData.put("nama", contact.getNama());
        updateData.put("nomor_telp", contact.getNomorTelp());

        database.update("contact", updateData, "id = ?", new String[]{String.valueOf(contact.getId())});
    }

    public void delete(int id) {
        String[] whereData = {String.valueOf(id)};

        database.delete("contact", "id = ?", whereData);
    }

    public Contact get(int id) {
        Cursor cursor = buildCursor("where id = " + id);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        }

        return null;
    }

    public ArrayList<Contact> searchContact(String search_string) {
        Cursor cursor = buildCursor("where nama like '%" + search_string + "%' or nomor_telp like '%" + search_string + "%'");

        return buildContactsFromCursor(cursor);
    }

    @NonNull
    private ArrayList<Contact> buildContactsFromCursor(Cursor cursor) {
        ArrayList<Contact> result = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                result.add(new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public ArrayList<Contact> getAll() {
        Cursor cursor = buildCursor();

        return buildContactsFromCursor(cursor);
    }

    private Cursor buildCursor() {
        return buildCursor("");
    }

    private Cursor buildCursor(String where) {
        return database.rawQuery("SELECT * FROM contact " + where + " order by nama", null);
    }
}
