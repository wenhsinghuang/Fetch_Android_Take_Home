package com.example.fetch_android_take_home.models;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class FetchJSON extends AsyncTask<Void, Void, Void> {
    Boolean flag = Boolean.FALSE;
    String data = "";
    public ArrayList<HashMap<String, String>> list = new ArrayList<>();

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://fetch-hiring.s3.amazonaws.com/hiring.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONArray ja = new JSONArray(data);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                HashMap<String, String> map = new HashMap<>();
                map.put("id", jo.getString("id"));
                map.put("listId", jo.getString("listId"));
                map.put("name", jo.getString("name"));
                list.add(map);
            }
            flag = Boolean.TRUE;
            for (HashMap<String, String> item : list) {
                System.out.println("FJSON"+item.get("id"));
            }
//            System.out.println("FJSON"+list.isEmpty());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        while(!flag){
            continue;
        }
    }
}

