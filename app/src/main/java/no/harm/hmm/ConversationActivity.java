package no.harm.hmm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import no.harm.hmm.no.harm.hmm.db.MessageContract;
import no.harm.hmm.no.harm.hmm.db.ConversationDbAdapter;
import no.harm.hmm.no.harm.hmm.db.HmmDbHelper;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportToolbar = getSupportActionBar();
        if (supportToolbar != null) {
            supportToolbar.setDisplayHomeAsUpEnabled(true);
        }
        // Fetch all messages
        HmmDbHelper mDbHelper = new HmmDbHelper(this);
        SQLiteDatabase rDb = mDbHelper.getReadableDatabase();
        String[] cols = {
                MessageContract.MessageEntry._ID,
                MessageContract.MessageEntry.COLUMN_NAME_SENDER_ID,
                MessageContract.MessageEntry.COLUMN_NAME_MESSAGE,
                MessageContract.MessageEntry.COLUMN_NAME_LOCAL_TIMESTAMP
        };
        Cursor cursor = rDb.query(MessageContract.MessageEntry.TABLE_NAME,
                cols, null, null, null, null,
                MessageContract.MessageEntry.COLUMN_NAME_LOCAL_TIMESTAMP + " ASC ", // ORDER BY
                null);
        ConversationDbAdapter adapter = new ConversationDbAdapter(this, cursor, 0);
        final ListView lw = (ListView) findViewById(R.id.conversation);
        lw.setAdapter(adapter);

    }
}
