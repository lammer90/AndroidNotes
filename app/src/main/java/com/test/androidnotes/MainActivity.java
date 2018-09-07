package com.test.androidnotes;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.test.androidnotes.db.NotesContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insert();
        select();
    }

    private void insert() {
        ContentResolver contentResolver = getContentResolver();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NotesContract.Notes.COLUMN_TITLE, "Заголовок заметки");
        contentValues.put(NotesContract.Notes.COLUMN_NOTE, "Текст заметки");
        contentValues.put(NotesContract.Notes.COLUMN_CREATED_TS, System.currentTimeMillis());
        contentValues.put(NotesContract.Notes.COLUMN_UPDATED_TS, System.currentTimeMillis());

        Uri uri = contentResolver.insert(Uri.parse(NotesContract.Notes.URI_NOTES), contentValues);
        Log.i("Test", "URI: " + uri);
    }

    private void select() {
        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(Uri.parse(NotesContract.Notes.URI_NOTES), NotesContract.Notes.LIST_PROJECTION, null, null, null);
        Log.i("Test2", "Count: " + cursor.getCount());
        cursor.close();
    }
}