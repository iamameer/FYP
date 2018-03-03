/*
 * inquiry_activity :
 *
 * Methods          :  init()         : initialize variable
 *                     setEvents()    : setting up listeners
 */

package fyp.medcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class inquiry_activity extends AppCompatActivity {

    //Global Variables

    //Initialising variables
    private void init(){

    }

    //setting up methods
    private void setEvents(){

    }

    //Activity Lifecycle onCreate()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);

        init();
        setEvents();
        Log.d("MedCare","=inquiry_activity onCreate()");
    }

    //Activity Lifecycle onStart()
    @Override
    protected void onStart(){
        super.onStart();
        Log.d("MyTracker","=inquiry_activity onStart()");
    }

    //Activity Lifecycle onRestart()
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("MyTracker","=inquiry_activity onRestart()");
    }

    //Activity Lifecycle onResume()
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("MyTracker","=inquiry_activity onResume()");
    }

    //Activity Lifecycle onPause()
    @Override
    protected void onPause(){
        super.onPause();
        Log.d("MyTracker","=inquiry_activity onPause()");
    }

    //Activity Lifecycle onStop()
    @Override
    protected void onStop(){
        super.onStop();
        Log.d("MyTracker","=inquiry_activity onStop()");
    }

    //Activity Lifecycle onDestroy()
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("MyTracker","=inquiry_activity onDestroy()");
    }


}
