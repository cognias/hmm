package no.harm.hmm.no.harm.hmm.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.Date;

import no.harm.hmm.ConversationActivity;
import no.harm.hmm.R;
import no.harm.hmm.no.harm.hmm.db.MessageContract;
import no.harm.hmm.no.harm.hmm.db.HmmDbHelper;

public class HmmGcmListenerService extends GcmListenerService {

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        HmmDbHelper mDbHelper = new HmmDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String message = data.getString("message");
        cv.put(MessageContract.MessageEntry.COLUMN_NAME_MESSAGE, message);
        cv.put(MessageContract.MessageEntry.COLUMN_NAME_SENDER_ID, from);
        cv.put(MessageContract.MessageEntry.COLUMN_NAME_LOCAL_TIMESTAMP, new Date().getTime());
        db.insert(MessageContract.MessageEntry.TABLE_NAME, null, cv);
        sendNotification(message);
    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     *
     */
    private void sendNotification(String message) {
        Intent intent = new Intent(this, ConversationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("GCM MessageEntry")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    
}
