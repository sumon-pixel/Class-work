package com.example.messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
    }

    //Call onSendMessage() when the button is clicked
    public void onSendMessage(View view) {

        EditText messageView = findViewById(R.id.message);
        String messageText = messageView.getText().toString();

        //if we use a second activity to pass the message
        /*Intent intent = new Intent(this, ReceiveMessageActivity.class);
        intent.putExtra("message", messageText);
        startActivity(intent);*/

        //if we want to use an external message sender like phone message option or gmail
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, messageText);

        String chooseTitle = getString(R.string.chooser); //get the chooser title
        Intent chosenIntent = Intent.createChooser(intent, chooseTitle); //Display the chooser Dialog
        startActivity(chosenIntent);
    }
}
