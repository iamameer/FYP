/*
 * Appointment.java : Basic abstract class to create Activity as an object
 *
 * Methods       : setter() and getter()
 */

package fyp.medcare.non_activity;


import android.support.v7.app.AppCompatActivity;

public class Appointment {

    private int _id;
    private String date;
    private String description;
    private String venue;

    //empty constructor
    public Appointment(){}

    //full pledged constructor
    public  Appointment(int id, String date, String description, String venue){
        this._id = id;
        this.date = date;
        this.description = description;
        this.venue = venue;
    }

    //constructor without ID
    public Appointment(String date, String description, String venue){
        this.date = date;
        this.description = description;
        this.venue = venue;
    }

    //return the ID
    public int get_id(){ return  _id;}

    //set the ID
    public void set_id(int _id){this._id = _id;}

    //return the date
    public String getDate() {
        return date;
    }

    //set the date
    public void setDate(String date) {
        this.date = date;
    }

    //return the description
    public String getDescription() {
        return description;
    }

    //set the description
    public void setDescription(String description) {
        this.description = description;
    }

    //return the venue
    public String getVenue() {
        return venue;
    }

    //set the date
    public void setVenue(String venue) {
        this.venue = venue;
    }
}
