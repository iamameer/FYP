/*
 * list_activity :
 *
 * Methods          :  init()         : initialize variable
 *                     setEvents()    : setting up listeners
 */

package fyp.medcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

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
        imgListBack = (ImageView) findViewById(R.id.imgDetailBack);
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
            }
        });

        //ListView item selected
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG,"=list_activity listView item selected: \n"+i+") "+listView.getItemAtPosition(i).toString());
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


}
