package no.harm.hmm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import no.harm.hmm.no.harm.hmm.db.HmmDbHelper;
import no.harm.hmm.no.harm.hmm.db.ConversationListDbAdapter;
import no.harm.hmm.no.harm.hmm.db.MessageContract;
import no.harm.hmm.no.harm.hmm.gcm.QuickStartPreferences;
import no.harm.hmm.no.harm.hmm.gcm.RegistrationIntentService;

public class ConversationListActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;
    private int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Check for Google Play
        if (checkPlayServices()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        } else {
            Toast.makeText(ConversationListActivity.this,
                    "Google Play is required.", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }
        // Set up GCM receiver to listen for messages
        Log.i(this.getClass().toString(), "Listening for messages.");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickStartPreferences.SENT_TOKEN_TO_SERVER, false);
            }
        };

        // Set up the conversations list
        HmmDbHelper mDbHelper = new HmmDbHelper(this);
        Log.i(this.getClass().toString(), "Trying to read database.");
        SQLiteDatabase rDb = mDbHelper.getReadableDatabase();
        String[] cols = {
                MessageContract.MessageEntry._ID,
                MessageContract.MessageEntry.COLUMN_NAME_SENDER_ID
        };
        Cursor cursor = rDb.query(
                true, // distinct
                MessageContract.MessageEntry.TABLE_NAME,
                cols, null, null,
                MessageContract.MessageEntry.COLUMN_NAME_SENDER_ID, // GROUP BY
                null, null, null);
        ConversationListDbAdapter adapter = new ConversationListDbAdapter(this, cursor, 0);

        final ListView lw = (ListView) findViewById(R.id.conversationList);
        lw.setAdapter(adapter);
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                      @Override
                                      public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                                          Intent intent = new Intent(parent.getContext(), ConversationActivity.class);
                                          startActivity(intent);
                                      }
                                  }
        );

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(QuickStartPreferences.REGISTRATION_COMPLETE));
        Log.i(this.getClass().toString(), "Resuming the app.");
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(this.getClass().toString(), "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

}
