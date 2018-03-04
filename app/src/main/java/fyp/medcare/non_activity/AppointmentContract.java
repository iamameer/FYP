/*
 * AppointmentContract.java : Basic abstract class serving as Contract Class
 * Methods               : none
 * Sub-class             : ActivityEntry - Main database of history table
 */

package fyp.medcare.non_activity;

import android.provider.BaseColumns;

public final class AppointmentContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private AppointmentContract(){}

    /* Inner class that defines the HISTORY table contents */
    public static class AppointmentEntry implements BaseColumns{
        public static final String DATABASE_NAME = "medcare.db";
        public static final String TABLE_NAME = "appointment";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_VENUE = "venue";
    }
}
