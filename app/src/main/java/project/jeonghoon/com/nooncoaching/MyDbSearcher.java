package project.jeonghoon.com.nooncoaching;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeonghoon on 2016-11-24.
 */
public class MyDbSearcher {

    private static final String LOG_TAG = "MyDbSearcher";
    public static final String MYDATABASE_JSP_ADDRESS = "http://222.116.135.79:8080/Noon/createJson.jsp?";
    public static final String MYDATABASE_ANNIVERSARY_API_FORMAT = MYDATABASE_JSP_ADDRESS + "tbName=anniv&col=food_name&SoL=%s&date=%s";
    public static final String MYDATABASE_USER_ANNIVERSARY_API_FORMAT = MYDATABASE_JSP_ADDRESS + "tbName=noon_food&col=food_name&food_wea=%s&food_ann=%s";
    //"http://222.116.135.79:8080/Noon/createJson.jsp?" + "tbName=noon_food&col=food_name&food_wea="+staticMerge.temp+"&food_ann="+query
    /**
     *         String query ="";
     String today_S,today_L,nowDate,weather;
     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
     Date date = new Date();
     nowDate = sdf.format(date);
     LunarCalendar lunar = new LunarCalendar();
     today_S = nowDate.substring(4, 8);
     today_L = lunar.toLunar(nowDate).toString().substring(4, 8);
     weather = staticMerge.temp;
     try{
     query = URLEncoder.encode(what, "utf-8");
     }catch (Exception e){}
     Log.i("aaaa", "main onCreate : foodDbJson");
     String param = "tbName=noon_food&col=food_name&food_wea="+weather+"&food_time="+query;
     String param2 = "tbName=anniv&col=food_name&SoL=S&date="+today_S;
     String param3 =  "tbName=anniv&col=food_name&SoL=L&date="+today_L;
     */

    OnFinishAnnivSearchListner onFinishAnnivSearchListner;
    SearchTask searchTask;

    private class SearchTask extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... urls) {

            String url = urls[0];
            String json = fetchData(url);
            List<String> foodList = parse(json);
            if (onFinishAnnivSearchListner != null) {
                if (foodList == null) {
                    onFinishAnnivSearchListner.onFail();
                } else {
                    onFinishAnnivSearchListner.onSuccess(foodList);
                }
            }
            return null;
        }
    }

    public void getFoodListByDate(Context applicationContext ,String typeOfDate, String dateToday, OnFinishAnnivSearchListner onFinishAnnivSearchListner){
        this.onFinishAnnivSearchListner = onFinishAnnivSearchListner;

        if (searchTask != null ){
            searchTask.cancel(true);
            searchTask = null;
        }

        String url = buildAnnivSearchUrlString(typeOfDate,dateToday);
        Log.d(LOG_TAG," "+ url);
        searchTask = new SearchTask();
        searchTask.execute(url);
    }

    public void getFoodListByUserAnniv(Context applicationContext ,String weather, String typeOfanniversary, OnFinishAnnivSearchListner onFinishAnnivSearchListner){
        this.onFinishAnnivSearchListner = onFinishAnnivSearchListner;

        if (searchTask != null ){
            searchTask.cancel(true);
            searchTask = null;
        }

        String url = buildUserAnnivSearchUrlString(weather,typeOfanniversary);
        Log.d(LOG_TAG," "+ url);
        searchTask = new SearchTask();
        searchTask.execute(url);
    }

    private String buildAnnivSearchUrlString(String typeOfdate, String dateToday){
        String encodedTypeOfDate = "";
        String encodedDateToday = "";
        try {
            encodedTypeOfDate = URLEncoder.encode(typeOfdate, "utf-8");
            encodedDateToday = URLEncoder.encode(dateToday,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return String.format(MYDATABASE_ANNIVERSARY_API_FORMAT,encodedTypeOfDate,encodedDateToday);
    }

    private String buildUserAnnivSearchUrlString(String weather, String anniversary){
        String encodedWeather = "";
        String encodedAnniversary = "";
        try {
            encodedWeather = URLEncoder.encode(weather, "utf-8");
            encodedAnniversary = URLEncoder.encode(anniversary,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return String.format(MYDATABASE_USER_ANNIVERSARY_API_FORMAT,encodedWeather,encodedAnniversary);
    }

    private String fetchData(String urlString) {

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            conn.setChunkedStreamingMode(0);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");// post와 get의 차이점에 대해 생각 구글의 엑셀러레이터 사건과 같은

            BufferedReader is = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            //InputStream in = http.getInputStream();
            StringBuffer sb = new StringBuffer();
            String myResult = "";

            int chr;
            while ((chr = is.read()) != -1) {
                sb.append((char) chr);
            }

            myResult = sb.toString();
            Log.i(LOG_TAG, myResult);

            is.close();

            return myResult;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private List<String> parse(String jsonString) {
        List<String> foodList = new ArrayList<String>();

        try {
            JSONObject root = new JSONObject(jsonString);
            JSONArray foods = root.getJSONArray("Food");
            String food_name = "";
            for (int i = 0; i < foods.length(); i++) {
                JSONObject food = new JSONObject();
                food = foods.getJSONObject(i);
                food_name = food.getString("food_name");
                foodList.add(food_name);
                Log.i("param3", food_name);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return foodList;

    }





}
