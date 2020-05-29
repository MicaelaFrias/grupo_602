package com.example.midiendodistanciasmobile.WebService;

import android.os.AsyncTask;
import android.util.Log;

import com.example.midiendodistanciasmobile.Utilities.Constants;

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

public class PeticionAPIRest extends AsyncTask {
    String uri;
    String env = Constants.ENV;
    Integer commission = Constants.NUM_COMMISSION;
    Integer group = Constants.NUM_GROUP;
    JSONObject body;

    AsyncResponse callback;

    public PeticionAPIRest(String name, String lastname, Integer dni, String email, String password, String uri, AsyncResponse callback) throws JSONException {

        this.callback = callback;
        this.uri = uri;

        body = new JSONObject();
        body.put("env", this.env);
        body.put("name", name);
        body.put("lastname", lastname);
        body.put("dni", dni);
        body.put("email", email);
        body.put("password", password);
        body.put("commission", this.commission);
        body.put("group", this.group);

    }


    @Override
    protected Object doInBackground(Object[] objects) {

        try{
            Log.i(TAG, "Before:: " + this.uri + " __ " + this.body.toString());

            URL url=new URL(this.uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
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
                Log.i(TAG, "Response: " + dta.toString());
                return dta.toString();
            } else {
                Log.i(TAG, "doInBackground:  StatusCode" + statusCode + "  Message: " + statusMsg );
            }

        }catch (Exception e ){
            Log.e(TAG, "doInBackground: error Thread " + e.getMessage(),e );
        }

        return null;
    }

     @Override
     protected void onPostExecute(Object response) {

        if (response == null) {
            Log.e(TAG, "onPostExecute: Error de llamada");
            this.callback.processFinish("error", this.env, "error");
            return;
        }

         try {

             JSONObject obj = new JSONObject( (String)response);
             String state = obj.getString("state");
             String env = this.uri == Constants.URI_REGISTER ? obj.getString("env"): "none" ;
             String token = obj.getString("token");

             this.callback.processFinish(state, env, token);

         } catch (JSONException e) {
             e.printStackTrace();
         }

     }
}
