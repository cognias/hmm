package no.harm.hmm.no.harm.hmm.db;

import android.provider.BaseColumns;

public final class MessageContract {

    public MessageContract() {}

    public static abstract class MessageEntry implements BaseColumns {
        public static final String TABLE_NAME = "message";
        public static final String COLUMN_NAME_SENDER_ID = "senderid";
        public static final String COLUMN_NAME_MESSAGE = "message";
        public static final String COLUMN_NAME_LOCAL_TIMESTAMP = "localtimestamp";
    }
}
