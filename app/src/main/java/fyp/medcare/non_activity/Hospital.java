/*
 * Appointment.java : Basic abstract class to create Activity as an object
 *
 * Methods       : setter() and getter()
 */

package fyp.medcare.non_activity;


import android.support.v7.app.AppCompatActivity;

public class Hospital {

    private int _id;
    private String name;
    private String description;
    private float latitude;
    private float longitude;
    private float distance;

    //empty constructor
    public Hospital(){}

    //full pledged constructor
    public Hospital(int _id, String name, String description, float latitude, float longitude, float distance){
        this._id = _id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    //constructor without ID
    public Hospital(String name, String description, float latitude, float longitude, float distance){
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    //return the ID
    public int get_id(){ return  _id;}

    //set the ID
    public void set_id(int _id){this._id = _id;}

    //return the date
    public String getName() {
        return name;
    }

    //set the date
    public void setName(String name) {
        this.name = name;
    }

    //return the description
    public String getDescription() {
        return description;
    }

    //set the description
    public void setDescription(String description) {
        this.description = description;
    }

    //get the latitude
    public float getLatitude() {
        return latitude;
    }

    //set the latitude
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    //get the longitude
    public float getLongitude() {
        return longitude;
    }

    //set the longitude
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    //get the distance
    public float getDistance() {
        return distance;
    }

    //update the distance
    public void setDistance(float distance) {
        this.distance = distance;
    }
}
