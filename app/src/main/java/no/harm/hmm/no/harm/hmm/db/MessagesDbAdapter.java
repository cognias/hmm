package no.harm.hmm.no.harm.hmm.db;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import no.harm.hmm.R;

/**
 * Created by oyvinht on 04.02.16.
 */
public class MessagesDbAdapter extends CursorAdapter {

    public MessagesDbAdapter (Context ctx, Cursor csr, int flags) {
        super(ctx, csr, 0);
    }

    @Override
    public View newView (Context ctx, Cursor csr, ViewGroup parent) {
        return LayoutInflater.from(ctx).inflate(R.layout.content_conversation, parent, false);
    }

    @Override
    public void bindView (View v, Context ctx, Cursor csr) {
        ((TextView)v.findViewById(R.id.message)).setText(csr.getColumnIndexOrThrow());

    }
}
