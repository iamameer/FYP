/*
 * detail_activity :
 *
 * Methods          :  init()         : initialize variable
 *                     setEvents()    : setting up listeners
 */

package fyp.medcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class detail_activity extends AppCompatActivity {

    //Global Variables
    private String description;
    private ImageView imgDetailPic, imgDetailBack;
    private TextView txtDetailDesc;

    private final static String TAG = "MEDCARE";

    //Initialising variables
    private void init(){
        imgDetailPic = (ImageView) findViewById(R.id.imgDetailPic);
        imgDetailBack = (ImageView) findViewById(R.id.imgDetailBack);
        txtDetailDesc = (TextView) findViewById(R.id.txtDetailDesc);

        txtDetailDesc.setText(getIntent().getStringExtra("description"));
    }

    //setting up methods
    private void setEvents(){
        imgDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"=detail_activity imgDetailBack onClick()");
                finish();
            }
        });
    }

    //Activity Lifecycle onCreate()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);

        init();
        setEvents();
        Log.d(TAG,"=detail_activity onCreate()");
    }

    //Activity Lifecycle onStart()
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"=detail_activity onStart()");
    }

    //Activity Lifecycle onRestart()
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"=detail_activity onRestart()");
    }

    //Activity Lifecycle onResume()
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"=detail_activity onResume()");
    }

    //Activity Lifecycle onPause()
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"=detail_activity onPause()");
    }

    //Activity Lifecycle onStop()
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"=detail_activity onStop()");
    }

    //Activity Lifecycle onDestroy()
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"=detail_activity onDestroy()");
    }


}
