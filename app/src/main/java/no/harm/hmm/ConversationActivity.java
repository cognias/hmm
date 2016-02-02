package no.harm.hmm;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i += 1) {
            list.add("Message " + i);
        }

        BubbleListItemAdapter adapter = new BubbleListItemAdapter(this, R.layout.message_item, list);

        final ListView lw = (ListView) findViewById(R.id.conversation);
        lw.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class BubbleListItemAdapter extends ArrayAdapter<String> {

        private int resource;

        public BubbleListItemAdapter(Context ctx, int resource, List<String> items) {
            super(ctx, resource, items);
            this.resource = resource;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            LinearLayout bubbleView;
            String item = getItem(pos);
            if (convertView == null) {
                bubbleView = new LinearLayout(getContext());
                LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi.inflate(resource, bubbleView, true);
            } else {
                bubbleView = (LinearLayout)convertView;
            }
            TextView tv = (TextView)bubbleView.findViewById(R.id.message);
            tv.setText(item);
            return bubbleView;
        }

    }

}
