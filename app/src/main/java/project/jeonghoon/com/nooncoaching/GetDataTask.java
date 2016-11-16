package project.jeonghoon.com.nooncoaching;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static project.jeonghoon.com.nooncoaching.staticMerge.saveAddr;

/**
 * Created by kku on 2016-11-13.
 */
public class GetDataTask extends AsyncTask<Double,Void,String[]> {

    // 추천 액티비티의 내용이 길어질 것을 예상하고
    private Context mContext;
    private static final String LOG_TAG = "GetDataTask";
    ProgressDialog asyncDialog;

    public GetDataTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {

        asyncDialog = new ProgressDialog(mContext);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("로딩중입니다..");

        asyncDialog.show();
        super.onPreExecute();
    }

    @Override
    protected String[] doInBackground(Double... params) {
        double lati , lon;
        lati = params[0];
        lon = params[1];

        String temp[];
        String temp1;
        String si="";
        String dong="";
        String bunji="";

        String acceptedData[] = new String[2];

        try {
            String UUU = "http://api.openweathermap.org/data/2.5/weather?lat="+lati+"&lon="+lon+"&appid=228771918de505e0d9cf551a5a322259";

            Log.i("weather", "" + UUU);
            URL url = new URL(UUU);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(4000 /* milliseconds */);
            conn.setConnectTimeout(7000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            Scanner s = new Scanner(is);
            String str = "";
            while (s.hasNext())
                str += s.nextLine();
            is.close();

            JSONObject searchresult = new JSONObject(str);
            JSONArray results = searchresult.getJSONArray("weather");
            JSONObject json = new JSONObject();
            json = results.getJSONObject(0);
            String weather = json.getString("main");
            //Log.d(LOG_TAG,"weather " + weather);
            //int id = json.getInt("id");
            //Log.d(LOG_TAG,"id " + id);
            acceptedData[0] = weather;

            String UUU2 = "https://apis.daum.net/local/geo/coord2detailaddr?apikey=704c8f7bbfab6a07aa2fd61bf544d9f7&x="+lon+"&y="+lati+"&inputCoordSystem=WGS84&output=json";
            URL url2 = new URL(UUU2);
            HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
            conn2.setReadTimeout(4000 /* milliseconds */);
            conn2.setConnectTimeout(7000 /* milliseconds */);
            conn2.setRequestMethod("GET");
            conn2.setDoInput(true);
            conn2.connect();

            InputStream is2 = conn2.getInputStream();
            Scanner s2 = new Scanner(is2);
            String str2 = "";
            while (s2.hasNext())
                str2 += s2.nextLine();
            is2.close();
            Log.i("json:", str2);

            JSONObject searchresult2 = new JSONObject(str2);

            JSONObject json2 = new JSONObject();
            temp1 = searchresult2.getString("region");
            json2 = searchresult2.getJSONObject("old");
            bunji = json2.getString("bunji");
            if(!json2.getString("ho").equals("")) {
                bunji += "-"+json2.getString("ho");
            }
            temp = temp1.split(" ");
            si = temp[1];
            dong = temp[2];

            Log.i("aaaa","si,dong,bunji:"+si+","+dong+","+bunji);
            //수정해야할 부분
            saveAddr(MainActivity.mContext, si, dong, bunji);

            acceptedData[1] = temp[1];

            return acceptedData;
        }
        catch(Exception t) {
            StringWriter sw = new StringWriter();
            t.printStackTrace(new PrintWriter(sw));
            String exceptionAsStrting = sw.toString();

            Log.e("aaaa", exceptionAsStrting);

        }

        return null;
    }

    @Override
    protected void onPostExecute(String[] s) {

        asyncDialog.dismiss();

        super.onPostExecute(s);
    }



    public String idTotemp (int i) {
        String temp="";

        if(200<=i && i<300) {
            temp = "thunderstorm";
        } else if(500<=i && i<600) {
            temp = "rain";
        } else if(600<=i && i<700){
            temp = "snow";
        } else if ( i==761) {
            temp = "dust";
        } else if(i == 800) {
            temp = "clear";
        } else if(800<i && i<900) {
            temp = "clouds";
        } else if(i == 903) {
            temp = "cold";
        } else if(i==904) {
            temp = "hot";
        } else if(i==905) {
            temp = "windy";
        } else if(i==902) {
            temp = "hurricane";
        } else if(i == 960) {
            temp = "storm";
        }

        return temp;
    }

}