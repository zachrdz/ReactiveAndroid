package com.csandroid.myfirstapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.csandroid.myfirstapp.db.ContactDBHandler;
import com.csandroid.myfirstapp.models.Contact;

public class ComposeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText et = (EditText) findViewById(R.id.msg_receiver);
        Button sendBtn = (Button) findViewById(R.id.button);
        ImageButton deleteBtn = (ImageButton) findViewById(R.id.imageButton4);
        ImageButton ttlBtn = (ImageButton) findViewById(R.id.imageButton5);
        final EditText composedMsg = (EditText) findViewById(R.id.msg_body);


        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent composeIntent = new Intent(ComposeActivity.this, ContactsActivity.class);
                startActivity(composeIntent);
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent composeIntent = new Intent(ComposeActivity.this, MainActivity.class);
                Toast.makeText(v.getContext(), "Encrypted Msg: " + composedMsg.getText().toString(),
                        Toast.LENGTH_LONG).show();
                startActivity(composeIntent);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent composeIntent = new Intent(ComposeActivity.this, MainActivity.class);
                startActivity(composeIntent);
            }
        });

        ttlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence colors[] = new CharSequence[] {"5s", "15s", "60s"};

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Pick a TTL for this message:");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        String replyToUsername = bundle.getString("reply_to_username");
        String replyToId = bundle.getString("contact_id");
        if(null != replyToUsername){
            ContactDBHandler db = new ContactDBHandler(this);
            Contact contact = db.getContactByUsername(replyToUsername);
            EditText toField = (EditText) findViewById(R.id.msg_receiver);
            toField.setText(contact.getUsername());
        }
        if(null != replyToId){
            ContactDBHandler db = new ContactDBHandler(this);
            Contact contact = db.getContact(Integer.parseInt(replyToId));
            EditText toField = (EditText) findViewById(R.id.msg_receiver);
            toField.setText(contact.getUsername());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
