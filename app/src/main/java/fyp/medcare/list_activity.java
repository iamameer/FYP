/*
 * list_activity :
 *
 * Methods          :  init()         : initialize variable
 *                     setEvents()    : setting up listeners
 */

package fyp.medcare;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.LocaleDisplayNames;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.TabHost;
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

    private LocationListener listener;
    private LocationManager locationManager;

    private double initLong,initLat, newLong, newLat;
    private double distance;

    private final static String TAG = "MEDCARE";
    private static final int PERM_ID = 99;

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
                //addData();
            }
        });

        //Back ImageView clicked
        imgListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"=list_activity imgListBack onClick()");
                finish();
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
        updateDistance();
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
                //Log.d(TAG,"=list_activity >>IN THE IF...");
                List<String> hospital = new ArrayList<String>();
                for (int i= 0; i<hospitalArrayList.size(); i++){
                    //Log.d(TAG,"=list_activity >>IN THE FOR...");
                    hospital.add(hospitalArrayList.get(i).getName());
                }
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,hospital);
                Log.d(TAG,"=list_activity >>ADAPTER CREATED");
                listView.setAdapter(adapter);
                Log.d(TAG,"=list_activity >>ADAPTER SET");
            }else{
                Log.d(TAG,"=list_activity: [[[[[Empty database]]]]");
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

    @SuppressLint("MissingPermission")
    private void updateDistance(){
        try {
            check_permission();
            Log.d(TAG, "=list_activity >> UPDATING DISTANCE");

            //calculating distance
            listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d(TAG, "=list_activity: onLocationChanged");
                    Log.d(TAG, "######INIT LAT: " + initLat + " #####INIT LONG: " + initLong);
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                    Log.d(TAG, "=list_activity: onStatusChanged");
                }

                @Override
                public void onProviderEnabled(String s) {
                    Log.d(TAG, "=list_activity: onProviderEnabled");
                }

                @Override
                public void onProviderDisabled(String s) {
                    Log.d(TAG, "=list_activity: onProviderDisabled");
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            };
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, listener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            initLat = location.getLatitude();
            initLong = location.getLongitude();

            Location location1 = new Location("");
            location1.setLatitude(initLat);
            location1.setLongitude(initLong);

            Log.d(TAG, "######INIT LAT: " + initLat + " #####INIT LONG: " + initLong);

            //STARTING UPDATE
            DBHelper dbHelper = new DBHelper(getApplicationContext(),null,null,1);
            ArrayList<Hospital> hospitalArrayList = dbHelper.display();
            if (hospitalArrayList!=null){
                for (int i= 0; i<hospitalArrayList.size(); i++){
                    //hospital.add(hospitalArrayList.get(i).getName());
                    newLat = hospitalArrayList.get(i).getLatitude();
                    newLong = hospitalArrayList.get(i).getLongitude();

                    Location location2 = new Location("");
                    location2.setLatitude(newLat);
                    location2.setLongitude(newLong);

                    distance = location1.distanceTo(location2);
                    dbHelper.updateHospitalDistance(i,distance);
                    Log.d(TAG,hospitalArrayList.get(i).getName()+" UPDATED>> "+distance);
                }
            }else{
                Log.d(TAG,"=list_activity: [[[[[Empty database]]]]");
            }

        }catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }

    private void check_permission(){
        if (ContextCompat.checkSelfPermission(list_activity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(list_activity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(list_activity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(list_activity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)){
                ActivityCompat.requestPermissions(list_activity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERM_ID);
                ActivityCompat.requestPermissions(list_activity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERM_ID);
            }else{
                ActivityCompat.requestPermissions(list_activity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERM_ID);
                ActivityCompat.requestPermissions(list_activity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERM_ID);
            }
        }else{Log.d(TAG,"####ACCESS GRANTED####");}
    }

    //Method overriding - during listing and permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults){
        if(requestCode == PERM_ID){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Log.d(TAG,"####ACCESS GRANTED####");
            }else{
                check_permission();
            }
        }
    }

    private void addData(){
        DBHelper dbHelper = new DBHelper(getApplicationContext(),null,null,1);

        //Hospital hospital = new Hospital("Hospital Test","Description Test",1.234556,1.234567,5.555555);
        /*Hospital hospital0 = new Hospital("Hospital Tuanku Fauziah,Kangar","Jalan Tun Abdul Razak, Pusat Bandar Kangar, 01000 Kangar, Perlis",6.440989,100.191254,0);

        Hospital hospital1 = new Hospital("Hospital Sultanah Bahiyah, Alor Setar","Km 6, Jln Langgar, Bandar Alor Setar, 05460 Alor Setar, Kedah",6.148825,100.406373,0);
        Hospital hospital2 = new Hospital("Hospital Sultan Abdul Halim, Sg Petani","225, Bandar Amanjaya, 08000 Sungai Petani, Kedah",5.669582,100.517415,0);
        Hospital hospital3 = new Hospital("Hospital Kulim","Jalan Mahang, Kampung Kelang Lama, 09000 Kulim, Kedah",5.391951,100.574033,0);
        Hospital hospital4 = new Hospital("Hospital Baling","Baling, 09100 Baling, Kedah",5.677947,100.925653,0);
        Hospital hospital5 = new Hospital("Hospital Langkawi","Jalan Bukit, Tekuh Langkawi, Kedah",6.325912,99.796746,0);
        Hospital hospital6 = new Hospital("Hospital Yan","Kampung Sungai Udang, 06900 Yan, Kedah",5.820211,100.384938,0);
        Hospital hospital7 = new Hospital("Hospital Jitra","Pekan Jitra, 06000 Jitra, Kedah",6.277269,100.416338,0);
        Hospital hospital8 = new Hospital("Hospital Sik","Pekan Sik, 08220 Sik, Kedah",5.811985,100.727503,0);
        Hospital hospital9 = new Hospital("Hospital Kuala Nerang","Kuala Nerang, 06300 Kuala Nerang, Kedah",6.251731,100.609289,0);

        Hospital hospital10 = new Hospital("Hospital Pulau Pinang","Jalan Residensi, 10990 George Town, Pulau Pinang",5.416352,100.310686,0);
        Hospital hospital11 = new Hospital("Hospital Bukit Mertajam","Jalan Kulim, 14000 Bukit Mertajam, Pulau Pinang",5.359663,100.464219,0);
        Hospital hospital12 = new Hospital("Hospital Seberang Jaya","13700 Perai, Penang",5.394323,100.407602,0);
        Hospital hospital13 = new Hospital("Hospital Sungai Bakap","Besar, 14200 Sungai Petani, Pulau Pinang",5.219326,100.497157,0);
        Hospital hospital14 = new Hospital("Hospital Kepala Batas","Jalan Bertam 2, 13200 Kepala Batas, Pulau Pinang",5.511770,100.437404,0);
        Hospital hospital15 = new Hospital("Hospital Balik Pulau","Jalan Balik Pulau, 11000 Balik Pulau, Pulau Pinang",5.350241,100.233124,0);

        Hospital hospital16 = new Hospital("Hospital Raja Permaisuri Bainon, Ipoh","G24, Jalan Raja Ashman Shah, 31350 Ipoh, Perak",4.603799,101.090159,0);
        Hospital hospital17 = new Hospital("Hospital Taiping","Jalan Taming Sari, 34000 Taiping, Perak",4.851029,100.736838,0);
        Hospital hospital18 = new Hospital("Hospital Teluk Intan","Jalan Changkat Jong, 36000 Teluk Intan, Perak",4.003501,101.040179,0);
        Hospital hospital19 = new Hospital("Hospital Parit Buntar","Jalan Sempadan Kampung Permatang Tok Mahat 34200, 34200 Parit Buntar, Perak",5.130737,100.483573,0);
        Hospital hospital20 = new Hospital("Hospital Kuala Kangsar","1648, Jalan Sultan Idris Shah 1, Taman Mawar, 33000 Kuala Kangsar, Perak",4.773066,100.931968,0);
        Hospital hospital21 = new Hospital("Hospital Batu Gajah","Hospital Batu Gajah Jalan Changkat Batu Gajah, Jalan Perpaduan, Batu Gajah, Perak",4.479418,101.035018,0);
        Hospital hospital22 = new Hospital("Hospital Kampar","Jalan Hospital, 31900 Kampar, Perak",4.312095,101.156351,0);
        Hospital hospital23 = new Hospital("Hospital Tapah","Jalan Temoh, 35000 Tapah, Perak",4.201148,101.259472,0);
        Hospital hospital24 = new Hospital("Hospital Slim River","35800 Slim River, Perak",3.837727,101.404358,0);
        Hospital hospital25 = new Hospital("Hospital Seri Manjung","Seri Manjung 32040 Seri Manjung Perak, 32200 Seri Manjung, Perak",4.185418,100.661980,0);
        Hospital hospital26 = new Hospital("Hospital Gerik","Jalan Intan, Pekan Gerik, 33300 Gerik, Perak",5.429190,101.127543,0);
        Hospital hospital27 = new Hospital("Hospital Selama","Jalan Taiping Selama, Perak 34100 Selama Perak",5.214525,100.686790,0);
        Hospital hospital28 = new Hospital("Hospital Changkat Melintang","Hospital Changkat Melintang Lambor Kanan,Parit Perak 32900, 32600 Bota, Perak",4.312958,100.910491,0);
        Hospital hospital29 = new Hospital("Hospital Sungai Siput","Jalan Felda Lasah, 31100 Sungai Siput, Perak",4.828884,101.056061,0);

        Hospital hospital30 = new Hospital("Hospital Tengku Ampuan Rahimah, Klang","Jalan Langat, 41200 Klang, Selangor",3.019879,101.439989,0);
        Hospital hospital31 = new Hospital("Hospital Kajang","4, Jalan Semenyih, Bandar Kajang, 43000 Kajang, Selangor",2.992819,101.792903,0);
        Hospital hospital32 = new Hospital("Hospital Kuala Kubu baru","Jalan Hospital, Pekan Kuala Kubu Bharu, 44000 Kuala Kubu Baru, Selangor",3.565259,101.652811,0);
        Hospital hospital33 = new Hospital("Hospital Tanjung Karang","Jalan Lenkungan, Kampung Tanjong Karang, 45500 Tanjong Karang, Selangor",3.425066,101.178645,0);
        Hospital hospital34 = new Hospital("Hospital Banting","Jalan Sultan Alam Shah, 42700 Banting, Selangor",2.803372,101.492443,0);
        Hospital hospital35 = new Hospital("Hospital Tengku Ampuan Jemaah,Sabak Bernam","Sabak Bernam Sabak Bernam, Selangor 45200 Sabak Bernam Selangor",3.761189,100.989744,0);
        Hospital hospital36 = new Hospital("Hospital Ampang","Jalan Mewah Utara, Pandan Mewah, 68000 Ampang, Selangor",3.127841,101.763899,0);
        Hospital hospital37 = new Hospital("Hospital Selayang","Hospital Selayang, Lebuh Raya Selayang-kepong, 68100 Batu Caves, Selangor",3.242274,101.648912,0);
        Hospital hospital38 = new Hospital("Hospital Sg Buloh","Jalan Hospital, 47000 Sungai Buloh, Selangor",3.218909,101.583414,0);
        Hospital hospital39 = new Hospital("Hospital Serdang","Jalan Puchong, 43000 Kajang, Selangor",2.976899,101.719962,0);
        Hospital hospital40 = new Hospital("Hospital Orang Asli, Gombak","13, 13 Jalan Gombak, Jalan Gombak, Kampung Batu Dua Belas Gombak, 53100, Selangor",3.292006,101.731078,0);
        Hospital hospital41 = new Hospital("Hospital Shah Alam","Persiaran Kayangan, Seksyen 7, 40000 Shah Alam, Selangor",3.070924,101.490120,0);

        Hospital hospital42 = new Hospital("Hospital Kuala Lumpur","23, Jalan Pahang, 53000 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur",3.171592,101.702428,0);
        Hospital hospital43 = new Hospital("Hospital Putrajaya","Hospital Putrajaya, Jalan P9, Pusat Pentadbiran Kerajaan Persekutuan Presint 7, 62250 Putrajaya, Wilayah Persekutuan Putrajaya",2.929108,101.674366,0);
        Hospital hospital44 = new Hospital("Hospital Labuan","Peti Surat 81006 Labuan, WP Labuan",5.311466,115.230393,0);

        Hospital hospital45 = new Hospital("Hospital Tuanku Jaafar, Seremban","Jalan Rasah, Bukit Rasah, 70300 Seremban, Negeri Sembilan",2.709699,101.944444,0);
        Hospital hospital46 = new Hospital("Hospital Tuanku Ampuan Najihah,Kuala Pilah","KM 3, Jalan Melang, Kampung Gemelang, 72000 Kuala Pilah, Negeri Sembilan",2.733116,102.231604,0);
        Hospital hospital47 = new Hospital("Hospital Tampin","Jln Tampin-Gemas, Kampung Keru Tengah, 73000 Tampin, Negeri Sembilan",2.485500,102.233360,0);
        Hospital hospital48 = new Hospital("Hospital Port Dickson","KM 11,, Jalan Pantai, 71050 Port Dickson, Negeri Sembilan",2.478169,101.856210,0);
        Hospital hospital49 = new Hospital("Hospital Jelebu","Taman Bukit Petaling, 71600 Kuala Klawang, Negeri Sembilan",2.950440,102.058878,0);
        Hospital hospital50 = new Hospital("Hospital Jempol","Hospital Jempol, Bandar Baru Serting, 72120 Bandar Seri Jempol, Negeri Sembilan",2.902804,102.406337,0);

        Hospital hospital51 = new Hospital("Hospital Melaka","Jalan Mufti Haji Khalil, 75400 Melaka",2.218386,102.261783,0);
        Hospital hospital52 = new Hospital("Hospital Alor Gajah","Jalan Paya Datok / Simpang, 78000 Alor Gajah, Melaka",2.395570,102.200918,0);
        Hospital hospital53 = new Hospital("Hospital Jasin","Hospital Utama, 77000 Jasin, Melaka",2.304362,102.429808,0);

        Hospital hospital54 = new Hospital("Hospital Sultanah Aminah, Johor Bahru","Jalan Abu Bakar, Masjid Sultan Abu Bakar, 80000 Johor Bahru, Johor",1.458608,103.745898,0);
        Hospital hospital55 = new Hospital("Hospital Pakar Sultanah Fatimah,Muar","Hospital Pakar Sultanah Fatimah,, Jln Salleh, Taman Utama Satu, 84000 Muar, Johor",2.056388,102.577054,0);
        Hospital hospital56 = new Hospital("Hospital Sultanah Nora, Batu Pahat","Hospital Sultanah Nora Ismail Batu Pahat, Jalan Korma, Taman Soga, 83000 Batu Pahat, Johor",1.837946,102.941035,0);
        Hospital hospital57 = new Hospital("Hospital Segamat","6, Jalan Genuang, Bandar Putra, 85000 Segamat, Johor",2.493268,102.859063,0);
        Hospital hospital58 = new Hospital("Hospital Enche' Besar Hajah Khalsom, Kluang","KM5 Jalan Kota Tinggi, Kluang",2.006380,103.348073,0);
        Hospital hospital59 = new Hospital("Hospital Kota Tinggi","Jalan Tun Habab, Pekan Kota Tinggi, 81900 Kota Tinggi, Johor",1.734535,103.899533,0);
        Hospital hospital60 = new Hospital("Hospital Pontian","Jln. Alsagoff, Kampung Dalam, 82000 Pontian, Johor",1.495794,103.385887,0);
        Hospital hospital61 = new Hospital("Hospital Mersing","Jalan Ismail, Mersing Kechil, 86800 Mersing, Johor",2.429023,103.845085,0);
        Hospital hospital62 = new Hospital("Hospital Tangkak","Kampung Padang Lalang, 84900 Tangkak, Johor",2.271272,102.547372,0);
        Hospital hospital63 = new Hospital("Hospital Maharaja Tun Ibrahim, Kulai","Senai Highway, 81000 Kulai, Johor",1.639204,103.622254,0);
        Hospital hospital64 = new Hospital("Hospital Sultan Ismail, Johor Bahru","Jalan Persiaran Mutiara Emas Utama, Taman Mount Austin, 81100 Johor Bahru, Johor",1.546598,103.790830,0);

        Hospital hospital65 = new Hospital("Hospital Tengku Ampuan Afzan, Kuantan","Hospital Tengku Ampuan Afzan Pahang,, Jalan Tanah Putih, 25100 Kuantan, Pahang",3.800377,103.321635,0);
        Hospital hospital66 = new Hospital("Hospital Pekan","Jalan Batu Balik, Kampung Mengkasar, 26600 Pekan, Pahang",3.501433,103.378368,0);
        Hospital hospital67 = new Hospital("Hospital Sultan Haji Ahmad Shah, Temerloh","Jalan Maran, 28000 Temerloh, Pahang Darul Makmur",3.453226,102.453175,0);
        Hospital hospital68 = new Hospital("Hospital Bentong","Jalan Tras, Darul Makmur, 28700 Bentong, Pahang",3.525288,101.905592,0);
        Hospital hospital69 = new Hospital("Hospital Raub","Jalan Tengku Abdul Samad, 27600 Raub, Pahang",3.803392,101.854859,0);
        Hospital hospital70 = new Hospital("Hospital Kuala Lipis","27200 Kuala Lipis, Pahang",4.182834,102.054922,0);
        Hospital hospital71 = new Hospital("Hospital Jerantut","Taman Jaya, 27000 Jerantut, Pahang",3.929389,102.360166,0);
        Hospital hospital72 = new Hospital("Hospital Jengka","Hospital Jengka, Bandar Jengka Maran, Bandar Jengka, 26400 Bandar Tun Razak, Pahang",3.771722,102.553327,0);
        Hospital hospital73 = new Hospital("Hospital Muadzam Shah","Jalan Kemajuan, 26810 Muadzam Shah, Pahang",3.053906,103.090907,0);
        Hospital hospital74 = new Hospital("Hospital Sultanah Kalsom, Cameron Highland","39000, Tanah Rata, Pahang",4.466802,101.391330,0);
        Hospital hospital75 = new Hospital("Hospital Rompin","Kampung Kolam, 26800 Kuala Rompin, Pahang",2.796331,103.495926,0);

        Hospital hospital76 = new Hospital("Hospital Sultanah Nur Zahirah, Kuala Terengganu","Jalan Sultan Mahmud, 20400 Kuala Terengganu, Terengganu",5.325346,103.152033,0);
        Hospital hospital77 = new Hospital("Hospital Kemaman","Jalan Da' Omar, Cukai, 24000 Kemaman, Terengganu",4.231891,103.420143,0);
        Hospital hospital78 = new Hospital("Hospital Dungun","Jalan Paka, 23000 Kuala Dungun, Terengganu",4.751973,103.414647,0);
        Hospital hospital79 = new Hospital("Hospital Besut","T5, Kampung Tanduk, 22010 Jerteh, Terengganu",5.729940,102.491945,0);
        Hospital hospital80 = new Hospital("Hospital Hulu Terengganu","Hospital Hulu Terengganu Batu 23, Jalan Kuala Berang Kuala Berang, Terengganu 21700 Kuala Berang Terengganu",5.073716,103.044471,0);
        Hospital hospital81 = new Hospital("Hospital Setiu","22100, Permaisuri, Terengganu",5.514709,102.769451,0);

        Hospital hospital82 = new Hospital("Hospital Raja Perempuan Zainab II, Kota Bahru","15586, Jalan Hospital, Bandar Kota Bharu, 15200 Kota Bharu, Kelantan",6.124714,102.246229,0);
        Hospital hospital83 = new Hospital("Hospital Kuala Krai","D223, Pekan Kuala Krai, 18000 Kuala Krai, Kelantan",5.535338,102.199127,0);
        Hospital hospital84 = new Hospital("Hospital Machang","Jalan Pasir Puteh, Kampung Bukit Tiu, 18500 Machang, Kelantan",5.764142,102.226242,0);
        Hospital hospital85 = new Hospital("Hospital Tumpat","Jalan Kelaburan, 16200 Tumpat, Kelantan",6.189703,102.157282,0);
        Hospital hospital86 = new Hospital("Hospital Pasir Mas","Jalan Meranti Pasir Mas, Kelantan 17000 Pasir Mas Kelantan",6.013397,102.119476,0);
        Hospital hospital87 = new Hospital("Hospital Tanah Merah","17500 Tanah Merah, Kelantan",5.810588,102.153216,0);
        Hospital hospital88 = new Hospital("Hospital Tengku Anis, Pasir Puteh","Hospital Tengku Anis, Jalan Pasir Puteh, 16800 Pasir Puteh, Kelantan",5.833123,102.386977,0);
        Hospital hospital89 = new Hospital("Hospital Gua Musang","Bandar Baru Gua Musang, 18300 Gua Musang, Kelantan",4.859415,101.954644,0);
        Hospital hospital90 = new Hospital("Hospital Jeli","Bandar Jeli, 17600 Jeli, Kelantan",5.701035,101.844043,0);

        Hospital hospital91 = new Hospital("Hospital Queen Elizabeth","13a, Jalan Penampang, 88200 Kota Kinabalu, Sabah",5.956769,116.072831,0);
        Hospital hospital92 = new Hospital("Hospital Queen Elizabeth II","Lorong Bersatu, Off Luyang, Jalan Damai, 88300 Kota Kinabalu, Sabah",5.966487,116.093793,0);
        Hospital hospital93 = new Hospital("Hospital Duchess of Kent, Sandakan","KM 3.2, Jalan Utara, 90000 Sandakan, Sabah",5.858546,118.103551,0);
        Hospital hospital94 = new Hospital("Hospital Tawau","67, Peti Surat, 91007 Tawau, Sabah",4.249723,117.880683,0);
        Hospital hospital95 = new Hospital("Hospital Beaufort","Hospital Beaufort, Peti Surat 40, Beaufort, 89807, Beaufort, Sabah, Pekan Beaufort, 89800 Beaufort, Sabah",5.351414,115.740992,0);
        Hospital hospital96 = new Hospital("Hospital Beluran","Beg Berkunci 2, Beluran, 90109, Beluran, Sabah",5.893495,117.555431,0);
        Hospital hospital97 = new Hospital("Hospital Keningau","Peti Surat, 11, 89007 Keningau, Sabah",5.371987,116.180875,0);
        Hospital hospital98 = new Hospital("Hospital Kota Belud","Peti Surat 159, Kota Belud, 89158, Kota Belud, Sabah, 89150 Kota Belud, Sabah",6.346219,116.426143,0);
        Hospital hospital99 = new Hospital("Hospital Kudat","Peti Surat 22, 89057 Kudat, Sabah",6.908008,116.838063,0);
        Hospital hospital100 = new Hospital("Hospital Lahad Datu","Jln Lahad Datu - Tawau, 91100 Lahad Datu, Sabah",5.027380,118.314471,0);
        Hospital hospital101 = new Hospital("Hospital Papar","Jalan New Hospital, Papar, 89600 Kota Kinabalu, Sabah",5.726853,115.923776,0);
        Hospital hospital102 = new Hospital("Hospital Ranau","Peti Surat 65, 89300 Ranau, Sabah",5.953856,116.671086,0);
        Hospital hospital103 = new Hospital("Hospital Semporna","Pekan Semporna, 91308 Semporna, Sabah",4.483098,118.603894,0);
        Hospital hospital104 = new Hospital("Hospital Tambunan","Peti Surat 10, Tambunan, 89657, Tambunan, Sabah",5.682539,116.371030,0);
        Hospital hospital105 = new Hospital("Hospital Tenom","Hospital Tenom, Peti Surat 97, Tenom, 89907, Tenom, Sabah",5.123175,115.941286,0);
        Hospital hospital106 = new Hospital("Hospital Sipitang","Kilometer 2 Jalan Sipitang - Beaufort, Peti Surat 250, 89857 Sipitang, Sabah",5.098197,115.562151,0);
        Hospital hospital107 = new Hospital("Hospital Kota Marudu","Peti Surat, 255, 89100 Kota Marudu, Sabah",6.457121,116.774882,0);
        Hospital hospital108 = new Hospital("Hospital Kinabatangan","W.D.T 200 Kinabatangan, 90200 Kinabatangan, Sabah",5.584963,117.850418,0);
        Hospital hospital109 = new Hospital("Hospital Kuala Penyu","89740 Kuala Penyu, Sabah",5.561566,115.585158,0);
        Hospital hospital110 = new Hospital("Hospital Kunak","W.D.T 150, Pekan Kunak, 91209 Kunak, Sabah",4.677117,118.232299,0);
        Hospital hospital111 = new Hospital("Hospital Pitas","Pitas, Jalan Hospital, 89100 Kota Marudu, Sabah",6.711863,117.029060,0);
        Hospital hospital112 = new Hospital("Hospital Tuaran","Hospital Daerah Tuaran, Mile 16, Jalan Tuaran, Kampung Berungis, 89208 Kota Kinabalu, Sabah",6.143924,116.219027,0);

        Hospital hospital113 = new Hospital("Hospital Umum Sarawak","Jalan Hospital, 93586 Kuching, Sarawak",1.543628,110.339599,0);
        Hospital hospital114 = new Hospital("Pusat Jantung Sarawak","Kuching - Samarahan Expressway, 94300 Kota Samarahan, Sarawak",1.481120,110.419404,0);
        Hospital hospital115 = new Hospital("Hospital Sri Aman","95000 Sri Aman, Sarawak",1.233859,111.462212,0);
        Hospital hospital116 = new Hospital("Hospital Sibu","KM 5 1/2, Jalan Ulu Oya, Pekan Sibu, 96000 Sibu, Sarawak",2.296471,111.891889,0);
        Hospital hospital117 = new Hospital("Hospital Miri","Q353, 98000 Miri, Sarawak",4.373965,113.999715,0);
        Hospital hospital118 = new Hospital("Hospital Limbang","Jalan Pandaruan, 98700 Limbang, Sarawak",4.765021,115.018794,0);
        Hospital hospital119 = new Hospital("Hospital Sarikei","Jalan Rentap, 96100 Sarikei, Sarawak",2.132974,111.492809,0);
        Hospital hospital120 = new Hospital("Hospital Kapit","Jalan Mamora, 96800 Kapit, Sarawak",2.013865,112.942656,0);
        Hospital hospital121 = new Hospital("Hospital Serian","Bandar Serian, 94700 Serian, Sarawak",1.176266,110.563610,0);
        Hospital hospital122 = new Hospital("Hospital Lundu","94500 Lundu, Sarawak",1.675593,109.852820,0);
        Hospital hospital123 = new Hospital("Hospital Saratok","KM 1, Jalan Saratok, Saratok, 95400, Saratok, Sarawak, 95400",1.747294,111.340394,0);
        Hospital hospital124 = new Hospital("Hospital Mukah","Jln Hj Mohd Fauzi, 96400 Mukah, Sarawak",2.902252,112.077678,0);
        Hospital hospital125 = new Hospital("Hospital Kanowit","Jalan Kanowit / Durin, 96700 Kanowit, Sarawak",2.102758,112.152314,0);
        Hospital hospital126 = new Hospital("Hospital Bintulu","Jalan Bkt Nyabau, 97000 Bintulu, Sarawak",3.229696,113.101030,0);
        Hospital hospital127 = new Hospital("Hospital Marudi","Hospital Marudi, Jalan Pungor, Marudi, 98050, Baram, Sarawak",4.178408,114.325355,0);
        Hospital hospital128 = new Hospital("Hospital Lawas","115.404591",4.854395,115.404591,0);
        Hospital hospital129 = new Hospital("Hospital Bau","Batu 1 1/2, Jalan Bau-Lundu, Bau, Kuching, 94000, Bau, Sarawak",1.429501,110.142643,0);
        Hospital hospital130 = new Hospital("Hospital Simunjan","94800 Simunjan, Sarawak",1.369378,110.785667,0);
        Hospital hospital131 = new Hospital("Hospital Betong","Betong, 95700 Betong, Sarawak",1.374370,111.574001,0);
        Hospital hospital132 = new Hospital("Hospital Daro","Jalan Itol Daro, Sarawak 96200 Daro Sarawak",2.522916,111.413423,0);
        Hospital hospital133 = new Hospital("Hospital Rajah Charles Brooke Memorial","Batu 13, Jalan Puncak Borneo, Kota Padawan, 93250, Kuching, Sarawak",1.389000,110.322458,0);
        Hospital hospital134 = new Hospital("Hospital Dalat","Q229, Pekan Dalat, 96300 Dalat, Sarawak",2.745131,111.946641,0);

        Hospital hospital135 = new Hospital("Hospital Rehabilitasi, Cheras","Jalan Yaacob Latif, Bandar Tun Razak, 56000 Batu 9 Cheras, Wilayah Persekutuan Kuala Lumpur",3.105603,101.728801,0);
        Hospital hospital136 = new Hospital("Institusi Perubatan Respiratori, Kuala Lumpur","Jalan Pahang, Pekeliling, Wilayah Persekutuan, 53000 Kuala Lumpur",3.175696,101.699538,0);
        Hospital hospital137 = new Hospital("Hospital Bahagia, Ulu Kinta, Perak","Hospital Bahagia Ulu, 31250 Tanjung Rambutan, Perak",4.675619,101.150747,0);
        Hospital hospital138 = new Hospital("Hospital Permai, Tampoi, Johor","Hospital Permai, Tampoi, Johor",1.523573,103.704629,0);
        Hospital hospital139 = new Hospital("Pusat Kawatan Kusta Negara, Selangor","Jalan Hospital, 47000 Sungai Buloh, Selangor",3.221865,101.590619,0);
        Hospital hospital140 = new Hospital("Hospital Mesra Bukit Padang, Sabah","PS 11342, Bukit Padang, Kota Kinabalu, Sabah, 88815 Kota Kinabalu",5.950288,116.105129,0);
        Hospital hospital141 = new Hospital("Hospital Sentosa, Sarawak","Batu 7, Jalan Penrissen, 93250 Kuching, Sarawak",1.463725,110.328895,0);
        Hospital hospital142 = new Hospital("Hospital Wanita dan Kanak-kanak, Likas","187, Sabah Karung Berkunci, 88996 Kota Kinabalu, Sabah",6.013791,116.119535,0);
        Hospital hospital143 = new Hospital("Institut Kanser Negara","4, Jalan P7, Presint 7, 62250 Putrajaya, Wilayah Persekutuan Putrajaya",2.927055,101.673623,0);
        Hospital hospital144 = new Hospital("Pusat Darah Negara","Jalan Tun Razak, Titiwangsa, 50400 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur",3.173147,101.706340,0);
        Hospital hospital145 = new Hospital("Hospital Tuanku Fauziah","Jalan Tun Abdul Razak, Pusat Bandar Kangar, 01000 Kangar, Perlis",6.440989,100.191254,0);
        */

        try{
            /*dbHelper.addHospital(hospital0);
            dbHelper.addHospital(hospital1);
            dbHelper.addHospital(hospital2);
            dbHelper.addHospital(hospital3);
            dbHelper.addHospital(hospital4);
            dbHelper.addHospital(hospital5);
            dbHelper.addHospital(hospital6);
            dbHelper.addHospital(hospital7);
            dbHelper.addHospital(hospital8);
            dbHelper.addHospital(hospital9);

            dbHelper.addHospital(hospital10);
            dbHelper.addHospital(hospital11);
            dbHelper.addHospital(hospital12);
            dbHelper.addHospital(hospital13);
            dbHelper.addHospital(hospital14);
            dbHelper.addHospital(hospital15);
            dbHelper.addHospital(hospital16);
            dbHelper.addHospital(hospital17);
            dbHelper.addHospital(hospital18);
            dbHelper.addHospital(hospital19);

            dbHelper.addHospital(hospital20);
            dbHelper.addHospital(hospital21);
            dbHelper.addHospital(hospital22);
            dbHelper.addHospital(hospital23);
            dbHelper.addHospital(hospital24);
            dbHelper.addHospital(hospital25);
            dbHelper.addHospital(hospital26);
            dbHelper.addHospital(hospital27);
            dbHelper.addHospital(hospital28);
            dbHelper.addHospital(hospital29);

            dbHelper.addHospital(hospital30);
            dbHelper.addHospital(hospital31);
            dbHelper.addHospital(hospital32);
            dbHelper.addHospital(hospital33);
            dbHelper.addHospital(hospital34);
            dbHelper.addHospital(hospital35);
            dbHelper.addHospital(hospital36);
            dbHelper.addHospital(hospital37);
            dbHelper.addHospital(hospital38);
            dbHelper.addHospital(hospital39);

            dbHelper.addHospital(hospital40);
            dbHelper.addHospital(hospital41);
            dbHelper.addHospital(hospital42);
            dbHelper.addHospital(hospital43);
            dbHelper.addHospital(hospital44);
            dbHelper.addHospital(hospital45);
            dbHelper.addHospital(hospital46);
            dbHelper.addHospital(hospital47);
            dbHelper.addHospital(hospital48);
            dbHelper.addHospital(hospital49);

            dbHelper.addHospital(hospital50);
            dbHelper.addHospital(hospital51);
            dbHelper.addHospital(hospital52);
            dbHelper.addHospital(hospital53);
            dbHelper.addHospital(hospital54);
            dbHelper.addHospital(hospital55);
            dbHelper.addHospital(hospital56);
            dbHelper.addHospital(hospital57);
            dbHelper.addHospital(hospital58);
            dbHelper.addHospital(hospital59);

            dbHelper.addHospital(hospital60);
            dbHelper.addHospital(hospital61);
            dbHelper.addHospital(hospital62);
            dbHelper.addHospital(hospital63);
            dbHelper.addHospital(hospital64);
            dbHelper.addHospital(hospital65);
            dbHelper.addHospital(hospital66);
            dbHelper.addHospital(hospital67);
            dbHelper.addHospital(hospital68);
            dbHelper.addHospital(hospital69);

            dbHelper.addHospital(hospital70);
            dbHelper.addHospital(hospital71);
            dbHelper.addHospital(hospital72);
            dbHelper.addHospital(hospital73);
            dbHelper.addHospital(hospital74);
            dbHelper.addHospital(hospital75);
            dbHelper.addHospital(hospital76);
            dbHelper.addHospital(hospital77);
            dbHelper.addHospital(hospital78);
            dbHelper.addHospital(hospital79);

            dbHelper.addHospital(hospital80);
            dbHelper.addHospital(hospital81);
            dbHelper.addHospital(hospital82);
            dbHelper.addHospital(hospital83);
            dbHelper.addHospital(hospital84);
            dbHelper.addHospital(hospital85);
            dbHelper.addHospital(hospital86);
            dbHelper.addHospital(hospital87);
            dbHelper.addHospital(hospital88);
            dbHelper.addHospital(hospital89);

            dbHelper.addHospital(hospital90);
            dbHelper.addHospital(hospital91);
            dbHelper.addHospital(hospital92);
            dbHelper.addHospital(hospital93);
            dbHelper.addHospital(hospital94);
            dbHelper.addHospital(hospital95);
            dbHelper.addHospital(hospital96);
            dbHelper.addHospital(hospital97);
            dbHelper.addHospital(hospital98);
            dbHelper.addHospital(hospital99);

            dbHelper.addHospital(hospital100);
            dbHelper.addHospital(hospital101);
            dbHelper.addHospital(hospital102);
            dbHelper.addHospital(hospital103);
            dbHelper.addHospital(hospital104);
            dbHelper.addHospital(hospital105);
            dbHelper.addHospital(hospital106);
            dbHelper.addHospital(hospital107);
            dbHelper.addHospital(hospital108);
            dbHelper.addHospital(hospital109);

            dbHelper.addHospital(hospital110);
            dbHelper.addHospital(hospital111);
            dbHelper.addHospital(hospital112);
            dbHelper.addHospital(hospital113);
            dbHelper.addHospital(hospital114);
            dbHelper.addHospital(hospital115);
            dbHelper.addHospital(hospital116);
            dbHelper.addHospital(hospital117);
            dbHelper.addHospital(hospital118);
            dbHelper.addHospital(hospital119);

            dbHelper.addHospital(hospital120);
            dbHelper.addHospital(hospital121);
            dbHelper.addHospital(hospital122);
            dbHelper.addHospital(hospital123);
            dbHelper.addHospital(hospital124);
            dbHelper.addHospital(hospital125);
            dbHelper.addHospital(hospital126);
            dbHelper.addHospital(hospital127);
            dbHelper.addHospital(hospital128);
            dbHelper.addHospital(hospital129);

            dbHelper.addHospital(hospital130);
            dbHelper.addHospital(hospital131);
            dbHelper.addHospital(hospital132);
            dbHelper.addHospital(hospital133);
            dbHelper.addHospital(hospital134);
            dbHelper.addHospital(hospital135);
            dbHelper.addHospital(hospital136);
            dbHelper.addHospital(hospital137);
            dbHelper.addHospital(hospital138);
            dbHelper.addHospital(hospital139);

            dbHelper.addHospital(hospital140);
            dbHelper.addHospital(hospital141);
            dbHelper.addHospital(hospital142);
            dbHelper.addHospital(hospital143);
            dbHelper.addHospital(hospital144);
            dbHelper.addHospital(hospital145); */

        }catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }
}
