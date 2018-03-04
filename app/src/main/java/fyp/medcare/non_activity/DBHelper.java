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
        super(context, AppointmentContract.AppointmentEntry.DATABASE_NAME, null, DATABASE_VERSION);
        myCR = context.getContentResolver();
    }

    //this method initializes the table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE" +
                AppointmentContract.AppointmentEntry.TABLE_NAME + "(" +
                AppointmentContract.AppointmentEntry.COLUMN_NAME_ID + "INTEGER PRIMARY KEY,"+
                AppointmentContract.AppointmentEntry.COLUMN_NAME_DATE + "TEXT,"+
                AppointmentContract.AppointmentEntry.COLUMN_NAME_DESCRIPTION + "TEXT,"+
                AppointmentContract.AppointmentEntry.COLUMN_NAME_VENUE + "TEXT" + ")";
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    //this method detect any upgrade version
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ AppointmentContract.AppointmentEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //this method add a new Appointment into the database, respective column
    public void addAppointment(Appointment appointment){
        ContentValues values = new ContentValues();
        values.put(AppointmentContract.AppointmentEntry.COLUMN_NAME_DATE, appointment.getDate());
        values.put(AppointmentContract.AppointmentEntry.COLUMN_NAME_DESCRIPTION, appointment.getDescription());
        values.put(AppointmentContract.AppointmentEntry.COLUMN_NAME_VENUE, appointment.getVenue());

        myCR.insert(MyContentProvider.CONTENT_URI,values);
    }

    //this method return a Appointment as object
    public Appointment findAppointment(int _id){
        String[] projection = {
            AppointmentContract.AppointmentEntry.COLUMN_NAME_ID,
            AppointmentContract.AppointmentEntry.COLUMN_NAME_DATE,
            AppointmentContract.AppointmentEntry.COLUMN_NAME_DESCRIPTION,
            AppointmentContract.AppointmentEntry.COLUMN_NAME_VENUE};

        String selection = "_id = \"" +_id+"\"";

        Cursor cursor = myCR.query(
                MyContentProvider.CONTENT_URI,
                projection,
                selection,
                null,
                null);

        Appointment appointment = new Appointment();

        try {
            if (cursor.moveToFirst()){
                cursor.moveToFirst();
                appointment.set_id(Integer.parseInt(cursor.getString(0)));
                appointment.setDate(cursor.getString(1));
                appointment.setDescription(cursor.getString(2));
                appointment.setVenue(cursor.getString(3));
                cursor.close();
            }else{
                appointment = null;
            }
        }catch (Exception e){
            Log.d("MedCare",e.toString());
        }
        return appointment;
    }

    //this method deletes the specified record
    public boolean deletAppointment(int _id){
        boolean result = false;
        String selection = "_id = \"" +_id+ "\"";

        int rowsDeleted = myCR.delete(MyContentProvider.CONTENT_URI,selection,null);

        if (rowsDeleted >0){result = true;}
        return result;
    }

    //this method update the current Appointment
    public boolean updateAppointment(int _id,String date, String description, String venue){
        boolean result = false;
        String selection = "_id = \""+_id+" \"";
        ContentValues values = new ContentValues();
        values.put(AppointmentContract.AppointmentEntry.COLUMN_NAME_DATE,date);
        values.put(AppointmentContract.AppointmentEntry.COLUMN_NAME_DESCRIPTION,description);
        values.put(AppointmentContract.AppointmentEntry.COLUMN_NAME_VENUE,venue);

        int rowsUpdated = myCR.update(MyContentProvider.CONTENT_URI,values,selection,null);

        if(rowsUpdated>0){result=true;}
        return result;
    }

    //this method returns array of Appointment(object) to be displayed
    public ArrayList<Appointment> display(){
        String[] projection = {
                AppointmentContract.AppointmentEntry.COLUMN_NAME_ID,
                AppointmentContract.AppointmentEntry.COLUMN_NAME_DATE,
                AppointmentContract.AppointmentEntry.COLUMN_NAME_DESCRIPTION,
                AppointmentContract.AppointmentEntry.COLUMN_NAME_VENUE};

        Cursor cursor = myCR.query(
                MyContentProvider.CONTENT_URI,projection,null,null,null);

        ArrayList<Appointment> result = new ArrayList<Appointment>();
        try{
            if(cursor.moveToFirst()){
                Log.d("MedCare","cursor : inside IF");
                do{
                    Appointment appointment = new Appointment();
                    Log.d("MyTracker","cursor: inside DO");
                    appointment.set_id(Integer.parseInt(cursor.getString(0)));
                    appointment.setDate(cursor.getString(1));
                    appointment.setDescription(cursor.getString(2));
                    appointment.setVenue(cursor.getString(3));
                    result.add(appointment);
                }while(cursor.moveToNext());
            }cursor.close();
        }catch (Exception e){
            Log.d("MedCare",e.toString());
        }
        return result;
    }

}
