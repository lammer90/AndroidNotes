package com.test.androidnotes.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class NotesProvider extends ContentProvider{
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int ALL_NOTES = 0;
    private static final int ONE_NOTES = 1;
    private NotesDBHelper dbHelper;
    private SQLiteDatabase db;

    static {
        matcher.addURI(NotesContract.AUTHORITIES, NotesContract.Notes.TABLE_PATH, ALL_NOTES);
        matcher.addURI(NotesContract.AUTHORITIES, NotesContract.Notes.TABLE_PATH + "/#", ONE_NOTES);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new NotesDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        db = dbHelper.getReadableDatabase();
        if (matcher.match(uri) == ALL_NOTES){
            if (sortOrder == null){
                sortOrder = NotesContract.Notes.COLUMN_UPDATED_TS + " DESC";
            }
            else{
                sortOrder = sortOrder + NotesContract.Notes.COLUMN_UPDATED_TS + " DESC";
            }
            return db.query(NotesContract.Notes.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        }
        else if (matcher.match(uri) == ONE_NOTES){
            if (selection == null){
                selection = "WHERE _id=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                db.query(NotesContract.Notes.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            }
            else{
                selection =  selection + "WHERE _id=?";
                assert selectionArgs != null;
                List<String> list = Arrays.asList(selectionArgs);
                list.add(uri.getLastPathSegment());
                String[] newSel = new String[list.size()];
                list.toArray(newSel);
                return db.query(NotesContract.Notes.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        if (matcher.match(uri) == ALL_NOTES){
            return NotesContract.Notes.ALL_NOTES_TYPE;
        }
        else if (matcher.match(uri) == ONE_NOTES){
            return NotesContract.Notes.ONE_NOTES_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        db = dbHelper.getWritableDatabase();
        if (matcher.match(uri) == ONE_NOTES){
            return null;
        }
        else if (matcher.match(uri) == ALL_NOTES){
            long l = db.insert(NotesContract.Notes.TABLE_NAME, null, values);
            Uri result = ContentUris.withAppendedId(Uri.parse(NotesContract.Notes.URI_NOTES), l);
            getContext().getContentResolver().notifyChange(result, null);
            return result;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
