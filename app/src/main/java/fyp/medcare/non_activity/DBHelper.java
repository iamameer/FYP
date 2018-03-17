/*
 * DBHelper.java  : Handle the DB with queries operation
 *
 * Methods        : addActivity()      : add a new activity (as object) into database
 *                  findActivity()     : retrieve a record
 *                  findActivityBest() : get the best record
 *                  deleteActivity()   : delete specified record
 *                  updateActivity()   : update specified record
 *                  display()          : display all records
 */

package fyp.medcare.non_activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{

    //Global variables
    private ContentResolver myCR;

    //The database version.
    public static final int DATABASE_VERSION = 1;

    //public constructor
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, HospitalContract.HospitalEntry.DATABASE_NAME, null, DATABASE_VERSION);
        myCR = context.getContentResolver();
    }

    //this method initializes the table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE" +
                HospitalContract.HospitalEntry.TABLE_NAME + "(" +
                HospitalContract.HospitalEntry.COLUMN_NAME_ID + "INTEGER PRIMARY KEY,"+
                HospitalContract.HospitalEntry.COLUMN_NAME_NAME + "TEXT,"+
                HospitalContract.HospitalEntry.COLUMN_NAME_DESCRIPTION + "TEXT,"+
                HospitalContract.HospitalEntry.COLUMN_NAME_LATITUDE + "FLOAT" +
                HospitalContract.HospitalEntry.COLUMN_NAME_LONGITUDE + "FLOAT" +
                HospitalContract.HospitalEntry.COLUMN_NAME_DISTANCE + "FLOAT" +")";
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    //this method detect any upgrade version
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ HospitalContract.HospitalEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //this method add a new Hospital into the database, respective column
    public void addHospital(Hospital hospital){
        ContentValues values = new ContentValues();
        values.put(HospitalContract.HospitalEntry.COLUMN_NAME_NAME, hospital.getName());
        values.put(HospitalContract.HospitalEntry.COLUMN_NAME_DESCRIPTION, hospital.getDescription());
        values.put(HospitalContract.HospitalEntry.COLUMN_NAME_LATITUDE, hospital.getLatitude());
        values.put(HospitalContract.HospitalEntry.COLUMN_NAME_LONGITUDE, hospital.getLongitude());
        values.put(HospitalContract.HospitalEntry.COLUMN_NAME_DISTANCE, hospital.getDistance());

        myCR.insert(MyContentProvider.CONTENT_URI,values);
    }

    //this method return a Hospital as object
    public Hospital findHospital(int _id){
        String[] projection = {
            HospitalContract.HospitalEntry.COLUMN_NAME_ID,
            HospitalContract.HospitalEntry.COLUMN_NAME_NAME,
            HospitalContract.HospitalEntry.COLUMN_NAME_DESCRIPTION,
            HospitalContract.HospitalEntry.COLUMN_NAME_LATITUDE,
            HospitalContract.HospitalEntry.COLUMN_NAME_LONGITUDE,
            HospitalContract.HospitalEntry.COLUMN_NAME_DISTANCE};

        String selection = "_id = \"" +_id+"\"";

        Cursor cursor = myCR.query(
                MyContentProvider.CONTENT_URI,
                projection,
                selection,
                null,
                null);

        Hospital hospital = new Hospital();

        try {
            if (cursor.moveToFirst()){
                cursor.moveToFirst();
                hospital.set_id(Integer.parseInt(cursor.getString(0)));
                hospital.setName(cursor.getString(1));
                hospital.setDescription(cursor.getString(2));
                hospital.setLatitude(cursor.getFloat(3));
                hospital.setLongitude(cursor.getFloat(4));
                hospital.setDistance(cursor.getFloat(5));
                cursor.close();
            }else{
                hospital = null;
            }
        }catch (Exception e){
            Log.d("MedCare",e.toString());
        }
        return hospital;
    }

    //this method deletes the specified record
    public boolean deleteHospital(int _id){
        boolean result = false;
        String selection = "_id = \"" +_id+ "\"";

        int rowsDeleted = myCR.delete(MyContentProvider.CONTENT_URI,selection,null);

        if (rowsDeleted >0){result = true;}
        return result;
    }

    //this method update the distance of current Hospital
    public boolean updateHospitalDistance(int _id,float distance){
        boolean result = false;
        String selection = "_id = \""+_id+" \"";
        ContentValues values = new ContentValues();
        values.put(HospitalContract.HospitalEntry.COLUMN_NAME_DISTANCE,distance);

        int rowsUpdated = myCR.update(MyContentProvider.CONTENT_URI,values,selection,null);

        if(rowsUpdated>0){result=true;}
        return result;
    }

    //this method returns array of Hospital(object) to be displayed
    public ArrayList<Hospital> display(){
        String[] projection = {
                HospitalContract.HospitalEntry.COLUMN_NAME_ID,
                HospitalContract.HospitalEntry.COLUMN_NAME_NAME,
                HospitalContract.HospitalEntry.COLUMN_NAME_DESCRIPTION,
                HospitalContract.HospitalEntry.COLUMN_NAME_LATITUDE,
                HospitalContract.HospitalEntry.COLUMN_NAME_LONGITUDE,
                HospitalContract.HospitalEntry.COLUMN_NAME_DISTANCE};

        Cursor cursor = myCR.query(
                MyContentProvider.CONTENT_URI,projection,null,null,null);

        ArrayList<Hospital> result = new ArrayList<Hospital>();
        try{
            if(cursor.moveToFirst()){
                Log.d("MedCare","cursor : inside IF");
                do{
                    Hospital hospital = new Hospital();
                    Log.d("MyTracker","cursor: inside DO");
                    hospital.set_id(Integer.parseInt(cursor.getString(0)));
                    hospital.setName(cursor.getString(1));
                    hospital.setDescription(cursor.getString(2));
                    hospital.setLatitude(cursor.getFloat(3));
                    hospital.setLongitude(cursor.getFloat(4));
                    hospital.setDistance(cursor.getFloat(5));
                    result.add(hospital);
                }while(cursor.moveToNext());
            }cursor.close();
        }catch (Exception e){
            Log.d("MedCare",e.toString());
        }
        return result;
    }

}
