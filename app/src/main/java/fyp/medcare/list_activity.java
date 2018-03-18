/*
 * list_activity :
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import fyp.medcare.non_activity.DBHelper;
import fyp.medcare.non_activity.Hospital;

public class list_activity extends AppCompatActivity {

    //Global Variables
    private EditText txtListSearch;
    private ImageView imgListSearch, imgListBack;
    private ListView listView;

    private final static String TAG = "MEDCARE";

    //Initialising variables
    private void init(){
        txtListSearch = (EditText) findViewById(R.id.txtListSearch);
        imgListSearch = (ImageView) findViewById(R.id.imgListSearch);
        imgListBack = (ImageView) findViewById(R.id.imgListBack);
        listView = (ListView) findViewById(R.id.listView);

        //populating listView
        Log.d(TAG,"=list_activity Retrieving data from database");
    }

    //setting up methods
    private void setEvents(){
        //search while text change
        txtListSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Search ImageView clicked
        imgListSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"=list_activity imgListSearch onClick()");
            }
        });

        //Back ImageView clicked
        imgListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"=list_activity imgListBack onClick()");
                finish();
                /*//ADADADADADADA
                DBHelper dbHelper = new DBHelper(getApplicationContext(),null,null,1);
                String ins;
                Hospital hospital = new Hospital("Hospital Test","Description Test",1.234556,1.234567,5.555555);

                try{
                    dbHelper.addHospital(hospital);
                    Log.d(TAG,"testAdded");
                }catch (Exception e){
                    Log.d(TAG,e.toString());
                }*/
            }
        });

        //ListView item selected
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG,"=list_activity listView item selected: \n"+i+") "+listView.getItemAtPosition(i).toString());
                viewHospital(i);
            }
        });
    }

    //Activity Lifecycle onCreate()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);

        init();
        setEvents();
        Log.d(TAG,"=list_activity onCreate()");
    }

    //Activity Lifecycle onStart()
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"=list_activity onStart()");
    }

    //Activity Lifecycle onRestart()
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"=list_activity onRestart()");
    }

    //Activity Lifecycle onResume()
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"=list_activity onResume()");
        display();
        Log.d(TAG,"=DATABASE DISPLAY");
    }

    //Activity Lifecycle onPause()
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"=list_activity onPause()");
    }

    //Activity Lifecycle onStop()
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"=list_activity onStop()");
    }

    //Activity Lifecycle onDestroy()
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"=list_activity onDestroy()");
    }

    private void display(){
        Log.d(TAG,"=list_activity >>DISPLAYING...");
        DBHelper dbHelper = new DBHelper(getApplicationContext(),null,null,1);
        Log.d(TAG,"=list_activity >>DBHELPER CREATED");

        try{
            ArrayList<Hospital> hospitalArrayList = dbHelper.display();
            Log.d(TAG,"=list_activity >>ARRAY display()");

            //display if the list is not empty, else simply state empty
            if (hospitalArrayList!=null){
                Log.d(TAG,"=list_activity >>IN THE IF...");
                List<String> hospital = new ArrayList<String>();
                for (int i= 0; i<hospitalArrayList.size(); i++){
                    Log.d(TAG,"=list_activity >>IN THE FOR...");
                    hospital.add(hospitalArrayList.get(i).getName());
                }
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,hospital);
                Log.d(TAG,"=list_activity >>ADAPTER CREATED");
                listView.setAdapter(adapter);
                Log.d(TAG,"=list_activity >>ADAPTER SET");
            }else{
                Log.d(TAG,"=list_activity: Empty database");
            }
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }

    }

    private void viewHospital(int position){
        Log.d(TAG,"=list_activity: Launching HospitalDetail--> View Hospital : "+listView.getItemAtPosition(position));
        //^implement search()
        try{
            DBHelper dbHelper = new DBHelper(this,null,null,1);
            Log.d(TAG,"=list_activity: (viewHospital)DBHELPER CREATED");
            Hospital hospital = dbHelper.findHospital(listView.getItemAtPosition(position).toString());
            Log.d(TAG,"=list_activity: (viewHospital)dbHelper.findHospital()");

            if (hospital != null){
                Intent intent = new Intent(getApplicationContext(),detail_activity.class);
                /*intent.putExtra("id",hospital.get_id());
                intent.putExtra("name",hospital.getName());*/
                intent.putExtra("description",hospital.getDescription());
                Log.d(TAG,"SENDING: "+hospital.getDescription());
                startActivity(intent);
                finish();
            }else{
                Log.d(TAG,"=list_activity: Error viewing item");
                Toast.makeText(getApplicationContext(),"Error viewing item",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }

}
