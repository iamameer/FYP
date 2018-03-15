/*
 * MainActivity :
 *
 * Methods          :  init()           : initialize variable
 *                     setEvents()      : setting up listeners
 *                     emergency_call() :
 */

package fyp.medcare;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //Global Variables
    private ImageView imgMainList, imgMainCheck, imgMainCall;

    private final static String TAG = "MEDCARE";

    //Initialising variables
    private void init(){
        imgMainList = (ImageView) findViewById(R.id.imgMainList);
        imgMainCheck = (ImageView) findViewById(R.id.imgMainCheck);
        imgMainCall = (ImageView) findViewById(R.id.imgMainCall);
    }

    //setting up methods
    private void setEvents(){
        //starting list_activity
        imgMainList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"=MainActivity imgMainList onClick()");
                Intent intent = new Intent(MainActivity.this,list_activity.class);
                startActivity(intent);
            }
        });

        //starting check_activity
        imgMainCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"=MainActivity imgMainCheck onClick()");
                Intent intent = new Intent(MainActivity.this,check_activity.class);
                startActivity(intent);
            }
        });

        //starting call()
        imgMainCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"=MainActivity imgMainCall onClick()");
                emergency_call();
            }
        });
    }

    private void emergency_call(){
        Log.d(TAG,"=MainActivity: Emergency call started");
    }

    //Activity Lifecycle onCreate()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setEvents();
        Log.d(TAG,"=MainActivity onCreate()");
    }

    //Activity Lifecycle onRestart()
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"=MainActivity onRestart()");
    }

    //Activity Lifecycle onResume()
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"=MainActivity onResume()");
    }

    //Activity Lifecycle onPause()
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"=MainActivity onPause()");
    }

    //Activity Lifecycle onStop()
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"=MainActivity onStop()");
    }

    //Activity Lifecycle onDestroy()
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"=MainActivity onDestroy()");
    }

}
