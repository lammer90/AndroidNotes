package com.test.androidnotes;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.test.androidnotes.adapter.CursorRecyclerAdapter;
import com.test.androidnotes.db.NotesContract;

public class MainActivity extends AppCompatActivity{
    private CursorRecyclerAdapter cursorRecyclerAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private LoaderManager.LoaderCallbacks<Cursor> callback = new LoaderManager.LoaderCallbacks<Cursor>() {
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
            return new CursorLoader(getApplicationContext(),
                    Uri.parse(NotesContract.Notes.URI_NOTES),
                    NotesContract.Notes.LIST_PROJECTION,
                    null,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
            cursorRecyclerAdapter.setCursor(cursor);
            cursor.setNotificationUri(getContentResolver(), Uri.parse(NotesContract.Notes.URI_NOTES));
            cursorRecyclerAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.sw_id);
        //swipeRefreshLayout.setOnRefreshListener();

        recyclerView = findViewById(R.id.rec_id);
        cursorRecyclerAdapter = new CursorRecyclerAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());

        recyclerView.setAdapter(cursorRecyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(itemDecoration);

        getSupportLoaderManager().initLoader(0, null, callback);
    }

    private void insert() {
        ContentResolver contentResolver = getContentResolver();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NotesContract.Notes.COLUMN_TITLE, "77777777777777");
        contentValues.put(NotesContract.Notes.COLUMN_NOTE, "7777777777777");
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