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

import static project.jeonghoon.com.nooncoaching.staticMerge.idTotemp;
import static project.jeonghoon.com.nooncoaching.staticMerge.saveAddr;
import static project.jeonghoon.com.nooncoaching.staticMerge.temp;

/**
 * Created by kku on 2016-11-13.
 */
public class GetDataTask extends AsyncTask<Double,Void,String[]> {

    // 추천 액티비티의 내용이 길어질 것을 예상하고
    private Context mContext;

    ProgressDialog asyncDialog = new ProgressDialog(mContext);

    public GetDataTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
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
            int id = json.getInt("id");
            idTotemp(id);

            String UUU2 = "https://apis.daum.net/local/geo/coord2detailaddr?apikey=704c8f7bbfab6a07aa2fd61bf544d9f7&x="+lon+"&y="+lati+"&inputCoordSystem=WGS84&output=json";
            URL url2 = new URL(UUU2);
            HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
            conn2.setReadTimeout(4000 /* milliseconds */);
            conn2.setConnectTimeout(7000 /* milliseconds */);
            conn2.setRequestMethod("GET");
            conn2.setDoInput(true);
            conn2.connect();

            InputStream is2 = conn.getInputStream();
            Scanner s2 = new Scanner(is2);
            String str2 = "";
            while (s2.hasNext())
                str2 += s.nextLine();
            is.close();
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
            saveAddr(MainActivity.mContext, si, dong, bunji);

            return str2;
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
    protected void onPostExecute(String s) {

        try{
            JSONObject searchresult = new JSONObject(s);
            JSONArray results = searchresult.getJSONArray("weather");
            JSONObject json = new JSONObject();
            json = results.getJSONObject(0);
            int id = json.getInt("id");
            idTotemp(id);
        }catch (Exception e){
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsStrting = sw.toString();
            Log.e("aaaa", exceptionAsStrting);
        }
        Log.i("aaaa", temp + "- now weather");

        String temp[];
        String temp1;
        String si="";
        String dong="";
        String bunji="";

        try{
            JSONObject searchresult = new JSONObject(s);

            JSONObject json = new JSONObject();
            temp1 = searchresult.getString("region");
            json = searchresult.getJSONObject("old");
            bunji = json.getString("bunji");
            if(!json.getString("ho").equals("")) {
                bunji += "-"+json.getString("ho");
            }
            temp = temp1.split(" ");
            si = temp[1];
            dong = temp[2];

        }catch (Exception e){
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsStrting = sw.toString();
            Log.e("aaaa", exceptionAsStrting);
        }
        Log.i("aaaa","si,dong,bunji:"+si+","+dong+","+bunji);
        saveAddr(MainActivity.mContext, si, dong, bunji);

        //staticMerge.dong = dong;
        //staticMerge.bunji = bunji;
        //동 이름 result

        asyncDialog.dismiss();

        super.onPostExecute(s);
    }
}