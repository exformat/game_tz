package com.exformat.tz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog pd;

    String txtJson = "";
    String link = "";
    String serverLink = "https://appstds.com/tPPh2qbs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startGameBTN = findViewById(R.id.start_game_btn_id);
        Button startWebViewBTN = findViewById(R.id.start_webview_btn_id);

        startGameBTN.setOnClickListener(this);
        startWebViewBTN.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.start_game_btn_id){
            Intent intent = new Intent(this, AndroidLauncher.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.start_webview_btn_id){
            startWebView();
        }

    }

    private void startWebView(){
        link = Prefs.loadData(this, Prefs.KEY_LINK);

        Log.i("Main","prefs data: " + link);


        if(link.equals(Prefs.DEFAULT_VALUE)){
            Log.i("Main", "prefs link = null");
            new JsonTask().execute(serverLink);

            Log.i("Main", "jsontask return: " + txtJson);

            try {
                JSONObject jo = new JSONObject(txtJson);
                boolean active = jo.getBoolean("Active");
                link = jo.getString("Link");
                Prefs.saveData(this, Prefs.KEY_LINK, link);

                if(active){
                    Intent intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("link", link);
                    startActivity(intent);
                }
                else{
                    Log.i("Main", "Active = false. launch game");
                    Intent intent = new Intent(this, AndroidLauncher.class);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            Log.i("Main", "prefs not null: " + link);
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("link", link);
            startActivity(intent);
        }
    }

    //sof...
    private class JsonTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line); //here u ll get whole response...... :-)
                }

                return buffer.toString();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (connection != null) {
                    connection.disconnect();
                }

                try {
                    if (reader != null) {
                        reader.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }

            txtJson = result;
        }
    }
}