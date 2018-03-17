/*
 * AppointmentContract.java : Basic abstract class serving as Contract Class
 * Methods               : none
 * Sub-class             : ActivityEntry - Main database of history table
 */

package fyp.medcare.non_activity;

import android.provider.BaseColumns;

public final class HospitalContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private HospitalContract(){}

    /* Inner class that defines the HISTORY table contents */
    public static class HospitalEntry implements BaseColumns{
        public static final String DATABASE_NAME = "medcare.db";
        public static final String TABLE_NAME = "hospital";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_DISTANCE = "distance";
    }
}
