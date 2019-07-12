package com.example.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {

    int seconds = 0; //number of seconds displayed on the stopwatch
    boolean running; //Is the stopwatch running?
    boolean wasRunning; /*records whether the stopwatch eas running before the onStop() method was called so that we know
                        whether to set it running again when the activity becomes visible again*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        //get the previous state of the stopwatch if the activity's been destroyed and re-created
        if(savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning"); //restore the state pf the wasRunning variable
                                                                            //if the activity is re-created
        }

        runTimer(); //using a separate method to update the stopwatch. We're starting it when the activity created
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running; //if the activity's paused, stop the stopwatch
        running = false;
    }

    @Override
    protected void onResume() { //if the activity's resumed, start the stopwatch again if it was running previously
        super.onResume();
        if(wasRunning) {
            running = true;
        }
    }

    //save the state of the stopwatch if it's about to be destroyed
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning); //save the state of the wasRunning variable
        super.onSaveInstanceState(outState);
    }

    //Start the stopwatch running when the Start button is clicked
    public void onClickStart(View view) {
        running = true; //start the stopwatch running
    }

    //Stop the stopwatch running when the Stop button is clicked
    public void onClickStop(View view) {
        running = false; //stop the stopwatch running
    }

    //Reset the stopwatch when the Reset button is clicked
    public void onClickReset(View view) {
        running = false;
        seconds = 0; //stop the stopwatch running and set the seconds to 0
    }

    //the runTimer() method uses a handler to increment the seconds and update the textView
    private void runTimer() {
        final TextView timeView = findViewById(R.id.time_view);

        final Handler handler = new Handler();
        handler.post(new Runnable() { /*call the post() method passing a new Runnable. The post() method processes codes
                                       without a delay, so the code in the Runnable run almost immediately.*/
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;

                //Formats the seconds into hours, minutes and seconds
                String time = String.format("%d:%02d:%02d",hours, minutes, secs); /*The Runnable Run() method contains
                                        the code we want to be run. in our case the code to update the textView.*/
                timeView.setText(time);

                if(running) {
                    seconds++; //if running is true, increment the seconds variable
                }
                handler.postDelayed(this, 1000); /*Post the code in the runnable to run again after a delay of
                                        1000 milliseconds,or 1 second. As this line of code included in the Runnable
                                        run() method, this will keep getting called.*/
            }
        });
    }
}
