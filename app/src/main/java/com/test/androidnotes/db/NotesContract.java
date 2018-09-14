package com.test.androidnotes.db;

import android.net.Uri;
import android.provider.BaseColumns;

import java.util.Arrays;
import java.util.List;

public class NotesContract implements BaseColumns{
    public static final String DB_NAME = "notes.db";
    public static final int DB_VERSION = 1;
    public static final String AUTHORITIES = "my.content.provider.note";
    public static final String URI = "content://" + AUTHORITIES;


    public static final List<String> CREATE_DATABASE_QUERIES = Arrays.asList(Notes.CREATE_TABLE,
            Notes.CREATE_UPDATED_TS_INDEX);

    private NotesContract() {
    }

    public static class Notes implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String TABLE_PATH = "notes";
        public static final String URI_NOTES = URI + "/" + TABLE_PATH;

        public static final String ALL_NOTES_TYPE = "vnd.android.cursor.dir/.my.content.provider.note";
        public static final String ONE_NOTES_TYPE = "vnd.android.cursor.item.my.content.provider.note";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_NOTE = "note";
        public static final String COLUMN_CREATED_TS = "created_ts";
        public static final String COLUMN_UPDATED_TS = "updated_ts";

        public static final String[] LIST_PROJECTION = {
                _ID,
                COLUMN_TITLE,
                COLUMN_NOTE,
                COLUMN_CREATED_TS,
                COLUMN_UPDATED_TS
        };

        static final String CREATE_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s INTEGER NOT NULL);",
                TABLE_NAME,
                _ID,
                COLUMN_TITLE,
                COLUMN_NOTE,
                COLUMN_CREATED_TS,
                COLUMN_UPDATED_TS);

        static final String CREATE_UPDATED_TS_INDEX = String.format("CREATE INDEX updated_ts_index " +
                        "ON %s (%s);",
                TABLE_NAME,
                COLUMN_UPDATED_TS);
    }
}
