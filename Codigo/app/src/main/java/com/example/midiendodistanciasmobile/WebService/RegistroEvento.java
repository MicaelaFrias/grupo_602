package com.example.midiendodistanciasmobile.WebService;

import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.util.Log;

import com.example.midiendodistanciasmobile.Constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RegistroEvento extends AsyncTask {

    String env = Constants.ENV;
    String uri = Constants.URI_EVENTS;
    String token;
    String typeEvent;
    String state;
    String description;
    AsyncResponse callback;
    JSONObject body;

    public RegistroEvento(String token, String typeEvent, String state, String description) {
        this.token = token;
        this.typeEvent = typeEvent;
        this.state = state;
        this.description = description;
    }

    public RegistroEvento(String token, String typeEvent, String state, String description, AsyncResponse callback) {
        this.callback = callback;
        this.token = token;
        this.typeEvent = typeEvent;
        this.state = state;
        this.description = description;
    }

    public void setBody() throws JSONException {
        body = new JSONObject();
        body.put("env", this.env);
        body.put("type_events", this.typeEvent);
        body.put("state", this.state);
        body.put("description", this.description);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try{
            this.setBody();
            Log.i(TAG, "Before:: " + this.token + " ____ "  + this.uri + " __ " + this.body.toString());
            URL url=new URL(this.uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            String token = "" + new String(this.token);
            con.setRequestProperty("token", token);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");

            // Write Request to output stream to server.
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(this.body.toString());
            out.close();

            // Check the connection status.
            int statusCode = con.getResponseCode();
            String statusMsg = con.getResponseMessage();

            if (statusCode >= 200 && statusCode<300) {
                InputStream it = new BufferedInputStream(con.getInputStream());
                InputStreamReader read = new InputStreamReader(it);
                BufferedReader buff = new BufferedReader(read);
                StringBuilder dta = new StringBuilder();
                String chunks;
                while ((chunks = buff.readLine()) != null) {
                    dta.append(chunks);
                }
                Log.i(TAG, "******Response Events******: " + dta.toString());
                return dta.toString();
            } else {
                Log.i(TAG, "doInBackground Events:  StatusCode" + statusCode + "  Message: " + statusMsg );
            }

        }catch (Exception e ){
            Log.e(TAG, "doInBackground Events: error Thread " + e.getMessage(),e );
        }

        return null;
    }


    @Override
    protected void onPostExecute(Object response) {

    }


}
