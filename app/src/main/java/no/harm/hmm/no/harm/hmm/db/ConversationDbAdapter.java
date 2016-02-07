package no.harm.hmm.no.harm.hmm.db;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import no.harm.hmm.R;

public class ConversationDbAdapter extends CursorAdapter {

    private DateFormat date = DateFormat.getDateInstance(DateFormat.SHORT);
    private DateFormat time = new SimpleDateFormat("HH:MM");

    public ConversationDbAdapter(Context ctx, Cursor csr, int flags) {
        super(ctx, csr, 0);
    }

    @Override
    public View newView(Context ctx, Cursor csr, ViewGroup parent) {
        return LayoutInflater.from(ctx).inflate(R.layout.message_item, parent, false);
    }

    @Override
    public void bindView(View v, Context ctx, Cursor csr) {
        String message = csr.getString(
                csr.getColumnIndexOrThrow(MessageContract.MessageEntry.COLUMN_NAME_MESSAGE));
        Date timestamp = new Date(new java.lang.Long(csr.getString(csr.getColumnIndexOrThrow(
                MessageContract.MessageEntry.COLUMN_NAME_LOCAL_TIMESTAMP))));
        ((TextView) v.findViewById(R.id.message)).setText(message);
        ((TextView) v.findViewById(R.id.localtimestamp))
                .setText(date.format(timestamp) + " " + time.format(timestamp));
    }
}
