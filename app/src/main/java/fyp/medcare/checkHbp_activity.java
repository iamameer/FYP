/*
 * checkHbp_activity :
 *
 * Methods          :  init()         : initialize variable
 *                     setEvents()    : setting up listeners
 */

package fyp.medcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class checkHbp_activity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener{

    //Global Variables
    private final static String TAG = "MEDCARE";
    private TextView textView;
    JavaCameraView javaCameraView;
    Mat mRGBA, imgGray, imgTres, imgCanny, imgFindContour;

    BaseLoaderCallback loaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            super.onManagerConnected(status);
            Log.i(TAG,"onManagerConnected");
            switch (status){
                case BaseLoaderCallback.SUCCESS:
                    javaCameraView.enableView();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    static{
        if (OpenCVLoader.initDebug()){
            Log.i(TAG,"succ");
        }else{
            Log.i(TAG,"fail");
        }
    }

    //Initialising variables
    private void init(){
        javaCameraView = (JavaCameraView) findViewById(R.id.javacamview);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);

        textView = (TextView) findViewById(R.id.txtCheckHBPArea);
    }

    //setting up methods
    private void setEvents(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_hbp_activity);

        init();
        setEvents();
        Log.d(TAG,"=checkBmi_activity onCreate()");
    }

    //Activity Lifecycle onStart()
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"=checkHbp_activity onStart()");
    }

    //Activity Lifecycle onRestart()
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"=checkHbp_activity onRestart()");
    }

    //Activity Lifecycle onResume()
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"=checkHbp_activity onResume()");
        if (OpenCVLoader.initDebug()){
            Log.i(TAG,"successfully Load CV");
            loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }else{
            Log.i(TAG,"failed to Load CV");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0,this,loaderCallback);
        }
    }

    //Activity Lifecycle onPause()
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"=checkHbp_activity onPause()");
        if(javaCameraView!=null){
            javaCameraView.disableView();
        }
    }

    //Activity Lifecycle onStop()
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"=checkHbp_activity onStop()");
    }

    //Activity Lifecycle onDestroy()
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"=checkHbp_activity onDestroy()");
        if(javaCameraView!=null){
            javaCameraView.disableView();
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        Log.d(TAG,"onCameraViewStarted");
        mRGBA = new Mat(height, width, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        Log.d(TAG,"onCameraViewStopped");
        mRGBA.release();
    }

    @Override
    public Mat onCameraFrame(Mat inputFrame) {
        try {
            imgGray = new Mat(inputFrame.rows(), inputFrame.cols(), inputFrame.type());
            Imgproc.cvtColor(inputFrame, imgGray, Imgproc.COLOR_RGB2GRAY);

            imgTres = new Mat(inputFrame.rows(), inputFrame.cols(), inputFrame.type());
            Imgproc.threshold(imgGray, imgTres, 0, 255, Imgproc.THRESH_BINARY);

            imgCanny = new Mat();
            Imgproc.Canny(imgTres, imgCanny, 10, 100);

            imgFindContour = new Mat();
            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(imgCanny, contours, imgFindContour, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
            Imgproc.drawContours(inputFrame,contours,-1,new Scalar(0,0,255),5);

            Double total=0.0;
            for(int i = 0 ; i < contours.size() ; i++){
                total = total+Imgproc.contourArea(contours.get(i));
            }
            Log.d(TAG,"ContourArea: "+total);

            final Double finalTotal = total;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        textView.setText(finalTotal.toString());
                    }catch (Exception e){
                        Log.d(TAG,e.toString());
                    }
                }
            });

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return inputFrame;
    }
}
