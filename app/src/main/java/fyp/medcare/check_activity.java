/*
 * check_activity :
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

import org.w3c.dom.Text;

public class check_activity extends AppCompatActivity {

    //Global Variables
    private ImageView imgCheckBMI, imgCheckHBP, imgCheckBack;
    private TextView txtCheckBMILast, txtCheckHBPLast;

    private final static String TAG = "MEDCARE";

    //Initialising variables
    private void init(){
        imgCheckBMI = (ImageView) findViewById(R.id.imgCheckBMI);
        imgCheckHBP = (ImageView) findViewById(R.id.imgCheckHBP);
        imgCheckBack = (ImageView) findViewById(R.id.imgCheckBack);

        txtCheckBMILast = (TextView) findViewById(R.id.txtCheckBMILast);
        txtCheckHBPLast = (TextView) findViewById(R.id.txtCheckHBPLast);

    }

    //setting up methods
    private void setEvents(){
        imgCheckBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(check_activity.this,checkBmi_activity.class);
                    startActivity(intent);
                }catch (Exception e){
                    Log.d(TAG,"=check_activity: "+e.toString());
                }
            }
        });

        imgCheckHBP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(check_activity.this,checkHbp_activity.class);
                startActivity(intent);
            }
        });

        imgCheckBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //Activity Lifecycle onCreate()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_activity);

        init();
        setEvents();
        Log.d(TAG,"=check_activity onCreate()");
    }

    //Activity Lifecycle onStart()
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"=check_activity onStart()");
    }

    //Activity Lifecycle onRestart()
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"=check_activity onRestart()");
    }

    //Activity Lifecycle onResume()
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"=check_activity onResume()");
    }

    //Activity Lifecycle onPause()
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"=check_activity onPause()");
    }

    //Activity Lifecycle onStop()
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"=check_activity onStop()");
    }

    //Activity Lifecycle onDestroy()
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"=check_activity onDestroy()");
    }


}
