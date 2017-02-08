package com.datm.agota.findyourfriends.util.directions;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.datm.agota.findyourfriends.MapsActivity;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;

import java.util.Map;

/**
 * Created by Agota on 2/8/2017.
 */
public class DirectionTask extends AsyncTask<Map<String, String>, Object, DirectionResult> {

    private LatLng fromPosition;
    private LatLng toPosition;
    private String directionsMode;
    private String toPubName;
    private MapsActivity activity;
    private Exception exception;
    private ProgressDialog progressDialog;

    public DirectionTask(MapsActivity activity, final LatLng fromPosition,
                         final LatLng toPosition, final String directionsMode, final String toPubName) {
        super();
        this.activity = activity;
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
        this.directionsMode = directionsMode;
        this.toPubName = toPubName;
    }

    public void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Calculating directions...");
        progressDialog.show();
    }

    @Override
    public void onPostExecute(DirectionResult result) {
        progressDialog.dismiss();
        if (exception == null) {
            activity.handleGetDirectionsResult(result);
        } else {
            processException();
        }
    }

    @Override
    protected DirectionResult doInBackground(Map<String, String>... params) {
        try {
            GoogleMapsDirection md = new GoogleMapsDirection();
            Document doc = md.getDocument(fromPosition, toPosition, directionsMode);
            return new DirectionResult(md.getDirection(doc), md.getDurationText(doc), toPubName);
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    private void processException() {
        Toast.makeText(activity, exception.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
