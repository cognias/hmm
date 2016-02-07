package no.harm.hmm.no.harm.hmm.db;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import no.harm.hmm.R;

public class ConversationListDbAdapter extends CursorAdapter {

    public ConversationListDbAdapter(Context ctx, Cursor csr, int flags) {
        super(ctx, csr, 0);
    }

    @Override
    public View newView (Context ctx, Cursor csr, ViewGroup parent) {
        return LayoutInflater.from(ctx).inflate(android.R.layout.simple_list_item_1, parent, false);
    }

    @Override
    public void bindView (View v, Context ctx, Cursor csr) {
        ((TextView)v.findViewById(android.R.id.text1))
                .setText(csr.getString(csr.getColumnIndexOrThrow(
                        MessageContract.MessageEntry.COLUMN_NAME_SENDER_ID)));
    }
}
