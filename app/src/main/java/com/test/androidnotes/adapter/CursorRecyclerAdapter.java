package com.test.androidnotes.adapter;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.androidnotes.R;
import com.test.androidnotes.db.NotesContract;

public class CursorRecyclerAdapter extends RecyclerView.Adapter<CursorRecyclerAdapter.CursorViewHolder>{
    private Cursor cursor;
    private boolean isDataValid = false;

    public CursorRecyclerAdapter() {
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public CursorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.note_element, viewGroup, false);
        return new CursorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CursorViewHolder cursorViewHolder, int i) {
        if (!isDataValid) {
            throw new IllegalStateException("Cursor is not valid!");
        }
        if (!cursor.moveToPosition(i)){
            throw new IllegalStateException("Can not move to position " + i);
        }
        cursorViewHolder.titleView.setText(cursor.getString(cursor.getColumnIndex(NotesContract.Notes.COLUMN_TITLE)));
        cursorViewHolder.textView.setText(cursor.getString(cursor.getColumnIndex(NotesContract.Notes.COLUMN_NOTE)));
    }

    @Override
    public int getItemCount() {
        if (!isDataValid) {
            return 0;
        }
        return cursor.getCount();
    }

    @Override
    public long getItemId(int position) {
        if (isDataValid) {
            if (cursor.moveToPosition(position)) {
                return cursor.getLong(cursor.getColumnIndex(NotesContract.Notes._ID));
            }
        }
        return RecyclerView.NO_ID;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        this.isDataValid = cursor != null;
    }

    public class CursorViewHolder extends RecyclerView.ViewHolder{
        public final TextView titleView;
        public final TextView textView;
        public CursorViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title_id);
            textView = itemView.findViewById(R.id.text_id);
        }
    }
}
