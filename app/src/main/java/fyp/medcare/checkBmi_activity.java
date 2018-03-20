/*
 * checkBmi_activity :
 *
 * Methods          :  init()         : initialize variable
 *                     setEvents()    : setting up listeners
 */

package fyp.medcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class checkBmi_activity extends AppCompatActivity {

    //Global Variables
    private TextView txtBMIResult;
    private EditText txtBMIHeight, txtBMIWeight;
    private Button btnBMICalc;
    private ImageView imgBMIBack;

    private final static String TAG = "MEDCARE";

    //Initialising variables
    private void init(){
        txtBMIHeight = (EditText) findViewById(R.id.txtBMIHeight);
        txtBMIWeight = (EditText) findViewById(R.id.txtBMIWeight);
        txtBMIResult = (TextView) findViewById(R.id.txtBMIResult);

        btnBMICalc = (Button) findViewById(R.id.btnBMICalc);

        imgBMIBack = (ImageView) findViewById(R.id.imgBMIBack);

    }

    //setting up methods
    private void setEvents(){
        txtBMIHeight.setText("0");
        txtBMIWeight.setText("0");

        btnBMICalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if empty value:
                if(txtBMIHeight.getText().toString().equals("")){
                    txtBMIHeight.setText(0);
                }
                if(txtBMIWeight.getText().toString().equals("")){
                    txtBMIWeight.setText(0);
                }

                //calculating BMI
                calc_BMI(Integer.parseInt(txtBMIHeight.getText().toString()), Integer.parseInt(txtBMIWeight.getText().toString()));
            }
        });

        imgBMIBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void calc_BMI(int height, int weight){
        double result, meterHeight;
        String newText;

        try{
            meterHeight = (double)height /100;
            result = Math.round(((double) weight / (meterHeight*meterHeight))*10d)/10d;
            newText = "Your BMI is: "+Double.toString(result);
            txtBMIResult.setText(newText);
            Log.d(TAG,newText);
        }catch(Exception e){
            Log.d(TAG,"=checkBmi_activity: "+e.toString());
        }
    }

    //Activity Lifecycle onCreate()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bmi_activity);

        init();
        setEvents();
        Log.d(TAG,"=checkBmi_activity onCreate()");
    }

    //Activity Lifecycle onStart()
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"=checkBmi_activity onStart()");
    }

    //Activity Lifecycle onRestart()
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"=checkBmi_activity onRestart()");
    }

    //Activity Lifecycle onResume()
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"=checkBmi_activity onResume()");
    }

    //Activity Lifecycle onPause()
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"=checkBmi_activity onPause()");
    }

    //Activity Lifecycle onStop()
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"=checkBmi_activity onStop()");
    }

    //Activity Lifecycle onDestroy()
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"=checkBmi_activity onDestroy()");
    }


}
