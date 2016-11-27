package project.jeonghoon.com.nooncoaching;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by han on 2015-11-24.
 */
public class DBHandler {

    private static final String LOG_TAG = "DBHandler";
    private NoonDatabase helper;
    private SQLiteDatabase db;
    public static Item item;
    private static int[] pattern = new int[7];
    //"select * from stored_data order by weight desc;";
    public static final String SELECTED_CATEGORY_BY_CONTEXT = "select distinct category, max(weight) from stored_data group by category order by max(weight) desc";
    String weather,address;
    List<String> foodList;
    /***
     * SELECT DISTINCT Category, MAX(CreationDate)
     FROM MonitoringJob
     GROUP BY Category
     ORDER BY MAX(CreationDate) DESC, Category
     */
    // 삽입의 경우  현재 날씨,위치,카테고리가 일치하는 튜플이 존재하는가 ? 없다 -> 인서트
    // 있을경우 해당하는 seq를 가져와서 해당하는 weight+1로 업데이트를 해준다

    // 해당하는 튜플을 검색 날씨와 위치와 카테고리로 검색

    // 가중치의 경우 삭제 -->  ?? ㅎ ㅓ???? 이럴경우에는 튜플이 존재하는지 검색 --> 없다 ? 인서트 -1로 해야할듯

    // 맞춤추천을 한다 생각하면 위치와 날씨로 조회했을때  해당하는 카테고리 정보가 있다 --> add
    // 없다면 다시 위치로만 조회  현재 위치에 해당하는 다양한 날씨의 중복 카테고리 발생  --> 현재 해당하는 위치에 해당하는 튜플들만 카테고리 기준으로 묶어처리
    // 현재위치에 해당하는 맛집 데이터가 없다 --> 날씨 데이터만 가지고 조합 --> 날씨데이터에 해당하는 튜플을 가져온다 여러가지 위치에 해당하는 중복 카테고리 하지만 카테고리 기준으로 묶어 가중치순으로 나열
    // 1번 생성자를 만든다.
    // 생성자를 통해 건너받은 날씨정보와 위치정보를 통해 삽입하는 튜플부터 시작해서 차례대로 생성해간다. 중간중간 검사하는 로그태그를 통해 확인
    private DBHandler(Context ctx) {
        this.helper = new NoonDatabase(ctx);
        this.db = helper.getWritableDatabase();
    }

    public static DBHandler open(Context ctx, Item tem) throws SQLException {
        DBHandler handler = new DBHandler(ctx);
        item = tem;
        return handler;
    }
    public static DBHandler open(Context ctx) throws SQLException {
        DBHandler handler = new DBHandler(ctx);
        return handler;
    }


    public void close() {
        helper.close();
        db.close();
    }

    public void stored_data_insert(String weather,String address){
        //응용할 수 있지 않을까 해서 날씨가 흐리거나 맑거나 어떤 음식을 먹는다 , 어떤 카테고리의 음식을 먹는것
        //시간이 지나면 어떤 날씨엔 어떤 카테고리의 음식을 많이 먹더라라는 정보가 쌓인다
        //날씨와 시간이 다른 속성으로 정리 돼 있기 때문에 단지 카테고리로만 검색하는 지금은 쓰기 어렵다.
        //이런 날씨엔 이런 카테고리의 음식을 많이 먹더라 ---> 이게 주 목적
        //이런 날씨가 이벤트 이런 날씨가 됐을때 --> 이런 날씨로 검색하고 이런 날씨에 해당하는 카테고리로 분류
        //이런 날씨에 해당하는 카테고리의 종류 중에서 카테고리 weighting을 순차적으로 정렬해서 넘겨준다, sqlite는 seq로 구분하기 때문에 상관없다.
        //같은 카테고리 여러 날씨 여러 시간 플래그들

        this.weather = weather;
        this.address = address;
        String endOfCategory = endCategory(item.category);

        int weight = 1;

        int beforeWeight = selectFavorDataWithWhere(endOfCategory, weather, address);

        Log.d("DBHandler", "beforeWeight : " + beforeWeight);
        //저장되는 favorItem객체 내부에  하... 이것좀 고민해보자 ... 지울 때
        if ( beforeWeight != Integer.MAX_VALUE ){

            updateStoredData(endOfCategory, weather, address, beforeWeight + 1 );

        }else{
            //"category TEXT, weather TEXT, address TEXT, weight INTEGER);";
            try{
                db.execSQL("INSERT INTO stored_data VALUES(null, '" + endOfCategory
                        + "','" + weather
                        + "','" + address
                        + "'," + weight
                        + ");");

            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    public int selectFavorDataWithWhere(String category, String weather, String address){



        Cursor cursor= null;
        cursor = db.rawQuery("select * from stored_data where category ='"+ category
                + "' and weather ='" + weather
                + "' and address ='" + address
                + "';", null);

        cursor.moveToFirst();

        if ( cursor.getCount() <= 0 ){
            return Integer.MAX_VALUE;
        }

        //category TEXT, weather TEXT, weight INTEGER
        int categoryIndex = cursor.getColumnIndex("category");
        int weatherIndex = cursor.getColumnIndex("weather");
        int weightIndex = cursor.getColumnIndex("weight");

        int beforeweight = cursor.getInt(weightIndex);

        Log.d( LOG_TAG , " selectFavorDataWithWhere");

        return beforeweight;

    }

    public boolean updateStoredData(String category, String weather, String address, int afterweight){

        try{
            db.execSQL("UPDATE stored_data SET " +
                    "weight="+afterweight+" WHERE category='" + category
                    + "' and weather ='" + weather
                    + "' and address ='" + address
                    + "';");

        }catch (Exception e){
            return false;
        }
        return true;

    }


    public void stored_data_delete(){

        String endOfCategory = endCategory(item.getCategory());

        int weight = 1;

        int beforeWeight = selectGetMaxCategory(endOfCategory);
        Log.d("DBHandler", "beforeWeight : " + beforeWeight);

        if( beforeWeight == 0){
            beforeWeight = weight;
        }

        updateStoredData(endOfCategory, weather, address, beforeWeight - 1 );

    }

    public int selectGetMaxCategory(String category){

        Cursor cursor= null;
        //"select distinct category, max(weight) from stored_data group by category order by max(weight) desc"
        cursor = db.rawQuery("select category, weather, address, weight from stored_data where category ='"+ category
                + "' order by weight desc;", null);

        cursor.moveToFirst();

        if ( cursor.getCount() <= 0 ){
            return 0;
        }

        //category TEXT, weather TEXT, weight INTEGER
        int categoryIndex = cursor.getColumnIndex("category");
        int weatherIndex = cursor.getColumnIndex("weather");
        int weightIndex = cursor.getColumnIndex("weight");
        int addressIndex = cursor.getColumnIndex("address");

        int beforeweight = cursor.getInt(weightIndex);
        weather = cursor.getString(weatherIndex);
        address = cursor.getString(addressIndex);

        Log.d( LOG_TAG , " selectGetMaxCategory");

        return beforeweight;

    }


    public List<String> selectFood(String weather, String address){

        String recommendList[] = new String[10];
        String sql = "select * from stored_data where weather ='" + weather
        + "' and address ='" + address + "' order by weight desc;";
        String defaultSql = "select distinct category, max(weight) from stored_data group by category order by max(weight) desc";
        List<String> foodList = new ArrayList<String>();


        Cursor cursor1,cursor2 = null;
        cursor1 = db.rawQuery(sql, null);

        cursor1.moveToFirst();


        int categoryIndex = cursor1.getColumnIndex("category");
        int weightIndex = cursor1.getColumnIndex("weight");
        int weatherIndex = cursor1.getColumnIndex("weather");


        if(cursor1.getCount() <= 0){
            cursor1 = null;
        }else {
            foodList.add(cursor1.getString(categoryIndex));
            while (cursor1.moveToNext()) {

                foodList.add(cursor1.getString(categoryIndex));
                Log.d("DBHandler", "테이블 갯수 :" + cursor1.getCount() + " 카테고리" + cursor1.getString(categoryIndex) + " 가중치 " + cursor1.getInt(weightIndex));

            }

            return foodList;
        }

        cursor2 = db.rawQuery(defaultSql, null);

        cursor2.moveToFirst();

        int categoryIndex1 = cursor2.getColumnIndex("category");
        int weightIndex1 = cursor2.getColumnIndex("max(weight)");

            if(cursor2.getCount() <= 0){
                    return null;
            }else{
                foodList.add(cursor2.getString(categoryIndex1));
                while (cursor2.moveToNext()) {

                    foodList.add(cursor2.getString(categoryIndex1));
                    Log.d("DBHandler", "테이블 갯수 :" + cursor2.getCount() + " 카테고리" + cursor2.getString(categoryIndex1) + " 가중치 " + cursor2.getInt(weightIndex1));

                }
            }

            return foodList;
    }


    public boolean insertAnni(Anni ani){

        try{
            db.execSQL("INSERT INTO anni_profile (subject, year, month, day, cate) " +
                    "VALUES ('" + ani.getSubject() + "', " + ani.getYear() + ", " + ani.getMonth() + ", " + ani.getDay() + ",'"+ ani.getCate() +"');");
        }catch (Exception e){
            return false;
        }

        return true;
    }

    public boolean deleteAnni(Anni ani){
        try{
            db.execSQL("DELETE FROM anni_profile WHERE _id="+ani.getSeq());

        }catch (Exception e){
            return false;
        }
        return true;
    }

    public ArrayList<Anni> selectAnniWithWhere(int month,int day){
        ArrayList<Anni> Annis = new ArrayList<>();
        Cursor cursor= null;
        cursor = db.rawQuery("select * from anni_profile where month ="+ month+" and day = "+day+";", null);
        Log.i("cccc", "" + cursor.getCount());
        if(cursor.getCount() <= 0)
            return null;
        int col1 = cursor.getColumnIndex("_id");
        int col2 = cursor.getColumnIndex("subject");
        int col3 = cursor.getColumnIndex("year");
        int col4 = cursor.getColumnIndex("month");
        int col5 = cursor.getColumnIndex("day");
        int col6 = cursor.getColumnIndex("cate");
        while(cursor.moveToNext()){
            Anni item = new Anni();
            item.setSeq(cursor.getInt(col1));
            item.setSubject(cursor.getString(col2));
            item.setYear(cursor.getInt(col3));
            item.setMonth(cursor.getInt(col4));
            item.setDay(cursor.getInt(col5));
            item.setCate(cursor.getString(col6));
            Annis.add(item);
        }
        return Annis;

    }

    public boolean updateAnni(Anni ani){
        try{
            db.execSQL("UPDATE anni_profile SET " +
                    "subject='"+ani.getSubject()+"', cate='"+ani.getCate()+"', year="+ani.getYear()+", month="+ani.getMonth()+", day="+ani.getDay()+" WHERE _id="+ani.getSeq()+";");

        }catch (Exception e){
            return false;
        }

        return true;

    }

    public ArrayList<Anni> selectAnni(){
        ArrayList<Anni> Annis = new ArrayList<>();
        Cursor cursor= null;
        cursor = db.rawQuery("select * from anni_profile;", null);
        Log.i("aaaa",""+cursor.getCount());
        if(cursor.getCount() <= 0)
            return null;
        int col1 = cursor.getColumnIndex("_id");
        int col2 = cursor.getColumnIndex("subject");
        int col3 = cursor.getColumnIndex("year");
        int col4 = cursor.getColumnIndex("month");
        int col5 = cursor.getColumnIndex("day");
        int col6 = cursor.getColumnIndex("cate");
        while(cursor.moveToNext()){
            Anni item = new Anni();
            item.setSeq(cursor.getInt(col1));
            item.setSubject(cursor.getString(col2));
            item.setYear(cursor.getInt(col3));
            item.setMonth(cursor.getInt(col4));
            item.setDay(cursor.getInt(col5));
            item.setCate(cursor.getString(col6));
            Annis.add(item);
        }
        return Annis;

    }
////"title TEXT, category TEXT, imageUrl TEXT, phone TEXT, address TEXT
    public boolean insertFavorItem(FavorItem item){

        try{
            db.execSQL("INSERT INTO food_favor (title, category, imageUrl, phone, address) " +
                    "VALUES ('" + item.getTitle() + "', " + item.getCategory() + ", " + item.getImageUrl() + ", " + item.getPhone() + ",'"+ item.getAddress() +"');");
        }catch (Exception e){
            return false;
        }

        return true;
    }

    public boolean insertFavorItem(){
        if (item.getCategory().equals("") || item.getCategory() == null){
            item.setCategory("http://222.116.135.79:8080/Noon/images/noon.png");
        }
        try{
            if(selectDataWithWhere(item.getTitle())) {
                db.execSQL("INSERT INTO food_favor (title, category, imageUrl, phone, address, latitude, longitude) " +
                        "VALUES ('" + item.getTitle() + "', '" + item.getCategory() + "', '" + item.getImageUrl() + "', '" + item.getPhone() + "','" + item.getAddress() + "','" + item.getLatitude() + "','" + item.getLongitude() + "');");
            }else {
                return true;
            }
        }catch (Exception e){
            Log.d("DBHandler","오류난다 친구야")
;            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean selectDataWithWhere(String title){

        Cursor cursor= null;
        cursor = db.rawQuery("select * from food_favor where title ='"+ title + "';", null);


        if ( cursor.getCount() <= 0 ){
            return true;
        }

        return false;
    }




    public boolean deleteFavorItem(FavorItem far){
        try{
            db.execSQL("DELETE FROM food_favor WHERE _id="+far.getSeq());

        }catch (Exception e){
            return false;
        }
        return true;
    }

    public ArrayList<FavorItem> selectFavorItem(){
        ArrayList<FavorItem> FavorItems = new ArrayList<>();
        Cursor cursor= null;
        cursor = db.rawQuery("select * from food_favor;", null);
        Log.i("DBHandler",""+cursor.getCount());

        if(cursor.getCount() <= 0)
            return null;

        int _id = cursor.getColumnIndex("_id");
        int title = cursor.getColumnIndex("title");
        int category = cursor.getColumnIndex("category");
        int imageUrl = cursor.getColumnIndex("imageUrl");
        int phone = cursor.getColumnIndex("phone");
        int address = cursor.getColumnIndex("address");
        int latitude = cursor.getColumnIndex("latitude");
        int longitude = cursor.getColumnIndex("longitude");

        while(cursor.moveToNext()){

            FavorItem item = new FavorItem();
            item.setSeq(cursor.getInt(_id));
            item.setTitle(cursor.getString(title));
            item.setCategory(cursor.getString(category));
            item.setImageUrl(cursor.getString(imageUrl));
            item.setPhone(cursor.getString(phone));
            item.setAddress(cursor.getString(address));
            item.setLatitude(cursor.getDouble(latitude));
            item.setLongitude(cursor.getDouble(longitude));

            FavorItems.add(item);
        }
        return FavorItems;

    }


    public String selectfood() throws SQLException {
        Cursor cursor= null;
        String local = "무의미한 로컬";
        boolean flag = true;
        ArrayList<weigt_foodname> foodname = new ArrayList<>();
        foodname.clear();
        Log.i("aaaa","1"+staticMerge.food.size());
        String food = staticMerge.food.get(0);
        Log.i("aaaa", "2" + "select * from food_favorite where local_name = '" + local + "' and food = '" + food + "';");
        for(int i=0; i<staticMerge.food.size();i++){
            food = staticMerge.food.get(i);
            cursor = db.rawQuery("select * from food_favorite where local_name = '" + local + "' and food = '" + food + "';", null);
            if (cursor.getCount() > 0 ) {
                cursor.moveToFirst();
            }else{
                cursor = db.rawQuery("select * from food_favorite where food = '" + food + "';", null);
                if(cursor.getCount() > 0 ){
                    cursor.moveToFirst();
                }else{
                    weigt_foodname temp = new weigt_foodname();
                    temp.foodname = staticMerge.food.get(i);
                    temp.weight = 0;
                    foodname.add(temp);
                    flag = false;
                }
            }
            Log.i("aaaa", "3" + cursor.getCount());
            Log.i("aaaa", "4" + cursor.getPosition());
            if(flag){
                int temp = cursor.getColumnIndex("weight");
                int temp2 = cursor.getColumnIndex("food");
                Log.i("aaaa", "5" + temp);
                weigt_foodname tempN = new weigt_foodname();
                tempN.foodname = cursor.getString(temp2);
                tempN.weight = cursor.getInt(temp);
                foodname.add(tempN);
                flag=true;
            }


        }

        Collections.sort(foodname, new Comparator<weigt_foodname>() {
            @Override
            public int compare(weigt_foodname lhs, weigt_foodname rhs) {
                return (lhs.weight > rhs.weight) ? -1 : (lhs.weight < rhs.weight) ? 1 : 0;
            }
        });
        switch (staticMerge.food.size()){
            case 0:
                staticMerge.finish_food[0] = "empty";
                staticMerge.finish_food[1] = "empty";
                staticMerge.finish_food[2] = "empty";
                break;
            case 1:
                staticMerge.finish_food[0] = foodname.get(0).foodname;
                staticMerge.finish_food[1] = "empty";
                staticMerge.finish_food[2] = "empty";
                break;
            case 2:
                staticMerge.finish_food[0] = foodname.get(0).foodname;
                staticMerge.finish_food[1] = foodname.get(1).foodname;
                staticMerge.finish_food[2] = "empty";
                break;
            default:
                staticMerge.finish_food[0] = foodname.get(0).foodname;
                staticMerge.finish_food[1] = foodname.get(1).foodname;
                staticMerge.finish_food[2] = foodname.get(2).foodname;
                break;
        }

        return null;
    }


    public String endCategory(String inputString){


        String afterSplit[] = inputString.split(">");

        String endOfCategory = afterSplit[afterSplit.length - 1];

        String lastSplit[] = endOfCategory.split(",");

        Log.d("DBhandler","lastSplit"  + lastSplit[0]);

        return lastSplit[0];

    }


    public void food_favorite_insert() {
        String local = item.address;
        String lc[] = local.split(" ");
        Calendar c = Calendar.getInstance();
        String today = String.valueOf(c.get(Calendar.YEAR));
        today += "-" + String.valueOf(c.get(Calendar.MONTH)+1);
        today += "-" + String.valueOf(c.get(Calendar.DATE));

        try {
            db.execSQL("INSERT INTO food_favorite VALUES (null, '" + lc[2] + "','" + item.title + "','" + staticMerge.temp + "','" + staticMerge.what + "',1,'" + today + "');");
            Log.i("db", "food_favorite insert succeessed");
        }catch(Exception e) {
            Log.i("db", "widget_insert Failed.");
        }
    }



    public Cursor food_favorite_select() {
        Cursor cursor = null;
        // cursor = db.rawQuery("select * from food_favorite where local_name = ? AND local_name = ?", new String[] {"David","2"});
        cursor = db.rawQuery("SELECT * FROM food_favorite;",null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if(cursor.getCount() >0) {
            cursor.moveToFirst();
            return cursor;
        }

        return null;
    }


    public void food_search_insert() {
        Calendar c = Calendar.getInstance();
        String today = String.valueOf(c.get(Calendar.YEAR));
        today += "-" + String.valueOf(c.get(Calendar.MONTH)+1);
        today += "-" + String.valueOf(c.get(Calendar.DATE));

        try {
            db.execSQL("INSERT INTO food_search VALUES (null, '" + item.title + "','" + item.imageUrl + "','" + item.phone + "','"  + today + "');");
            Log.i("db", "food_search insert succeessed");
        }catch(Exception e) {
            Log.i("db", "widget_insert Failed.");
        }
    }

    public Cursor food_search_select() {
        Cursor cursor = null;
        // cursor = db.rawQuery("select * from food_favorite where local_name = ? AND local_name = ?", new String[] {"David","2"});
        cursor = db.rawQuery("SELECT * FROM food_search;",null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if(cursor.getCount() >0) {
            cursor.moveToFirst();
            return cursor;
        }

        return null;
    }


    public void food_pattern_insert() {
        try {
            Log.i("db", "food_pattern_insert called");
            db.execSQL("INSERT INTO food_pattern VALUES (null, " + pattern[0] + ", " + pattern[1] + ", " + pattern[2] + ", " + pattern[3] + ", " + pattern[4] + ", " + pattern[5] + ", " + pattern[6] + ");");
        }catch (Exception e) {
            Log.i("db","food_pattern_insert Failed");
        }
    }

    public void food_pattern_clean2() {
        // 패턴 일주일에 한번씩 정리해주는 메서드

        Cursor cursor= null;
        String clean_sql1 = "SELECT SUM(a),SUM(b),SUM(c),SUM(d),SUM(e),SUM(f),SUM(g) FROM food_pattern;";


        cursor = db.rawQuery(clean_sql1, null);
        if(cursor.getCount() >0) {
            cursor.moveToFirst();
            int maxP = 0;
            int minP = 0;
            int P = 0;
            for(int i=0; i<cursor.getColumnCount(); i++) {
                P = cursor.getInt(i);
                pattern[i] = P;
                Log.i("db",""+pattern[i]);
                maxP = (maxP<P?P:maxP);
                minP = (P>minP?minP:P);
            }
            int avgP = (maxP+minP)/2;

            SharedInit si = new SharedInit(MainActivity.mContext);

            for(int i=0; i<cursor.getColumnCount();i++) {
                pattern[i] = (avgP<=pattern[i]?1:0);
                si.setSharedTrue(String.valueOf(i),(pattern[i]>0?true:false));
            }
        }
    }
/*    public void abode_insert() {
        loadAddr(MainActivity.mContext);
            try {
                if(!(staticMerge.dong.equals("") || staticMerge.bunji.equals(""))) {
                    db.execSQL("INSERT INTO abode VALUES (null, '" + staticMerge.dong + "', '" + staticMerge.bunji + "', 1, 0);");
                }
                Log.i("db","abode_insert Successe" + staticMerge.dong +"," + staticMerge.bunji);
            }catch (Exception e) {
                Log.i("db","abode_insert Failed");
            }
    }
    */
    public void abode_clean() {
        //거주지 하루에 한 번 클린해주는 메서드
        Cursor cursor= null;
        int maxP=0;
        int weight = 0;
        int id = 0;
        String loc1 = "";
        String loc2 = "";
        int count = 0;
        String clean_sql1 = "SELECT _id, COUNT(_id), local_name, addr, count FROM abode GROUP BY local_name, addr HAVING COUNT(*)>1;";
        String clean_sql2 = "";
        String clean_sql3 = "";
        String clean_sql4 = "";
        String clean_sql5 = "";

        cursor = db.rawQuery(clean_sql1, null);
        if(cursor.getCount() >0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                id = cursor.getInt(0);
                weight = cursor.getInt(1);
                loc1 = cursor.getString(2);
                loc2 = cursor.getString(3);
                count = cursor.getInt(4);
                maxP = (maxP > weight ? maxP : weight);

                clean_sql2 = "UPDATE abode SET weight=" + weight + " WHERE local_name='" + loc1 + "' AND addr='" + loc2 + "';";
            }

            clean_sql3 = "DELETE FROM abode " +
                    "WHERE count NOT IN " +
                    "(SELECT MAX(count) FROM abode GROUP BY addr, weight);";
            clean_sql4 = "DELETE FROM abode " +
                    "WHERE EXISTS (SELECT _id FROM abode WHERE count=0 " +
                    "AND EXISTS (SELECT _id FROM abode WHERE count>0));";
            clean_sql5 = "UPDATE abode SET count = (count+1), weight=0;";
            String clean_sql6 = "DELETE FROM abode WHERE _id NOT IN " +
                    "(SELECT * FROM (SELECT MIN(_id) FROM abode GROUP BY local_name, addr) AS T);";
            try {
                db.execSQL(clean_sql2);
                db.execSQL(clean_sql3);
                db.execSQL(clean_sql4);
                db.execSQL(clean_sql5);
                db.execSQL(clean_sql6);
                Log.i("db","abode_clean success");
            } catch (Exception e) {
                Log.i("db", "abode_clean Failed");
            }
        }
    }

    public void click_time() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        SharedInit si = new SharedInit(MainActivity.mContext);
        String timeValue = "";
        switch(staticMerge.what) {
            case "아침":
                timeValue="0";
                pattern[0]=1;
                break;
            case "아점":
                timeValue="1";
                pattern[1]=1;
                break;
            case "점심":
                timeValue="2";
                pattern[2]=1;
                break;
            case "점저":
                timeValue="3";
                pattern[3]=1;
                break;
            case "저녁":
                timeValue="4";
                pattern[4]=1;
                break;
            case "야식":
                timeValue="5";
                pattern[5]=1;
                break;
            case "후식":
                timeValue="6";
                pattern[6]=1;
                break;
            default:
                return;
        }

        Long tt = si.getSharedTime(timeValue);
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String str = df.format(tt);
        String[] click_str = str.split(":");
        int h,m;
        h = Integer.valueOf(click_str[0]);
        m = Integer.valueOf(click_str[1]);

        hour = (h+hour)/2;
        min = (m+min)/2;
        if( (min-60)>=0 ) {
            hour += 1;
            min = min%60;
        }
        Log.i("widget","new hour : " + hour +",minute:"+min);
        si.setSharedTime(timeValue, hour, min);
    }

    public void dummy_insert() {
        try {
            db.execSQL("INSERT INTO food_favorite (local_name, food, wea, time, weight) VALUES ('단월동','파전','rain','아침',1);");
            db.execSQL("INSERT INTO food_favorite (local_name, food, wea, time, weight) VALUES ('단월동','피자','broken clouds','아침',1);");
            db.execSQL("INSERT INTO food_pattern VALUES (null, 1,1,1,1,1,1,1);");
            db.execSQL("INSERT INTO food_pattern VALUES (null, 1,0,0,1,0,0,0);");
            db.execSQL("INSERT INTO food_pattern VALUES (null, 0,1,1,1,0,0,0);");
            db.execSQL("INSERT INTO food_pattern VALUES (null, 1,0,0,1,0,0,1);");
            db.execSQL("INSERT INTO food_pattern VALUES (null, 0,0,1,1,1,1,1);");
            db.execSQL("INSERT INTO food_pattern VALUES (null, 1,0,0,1,0,0,0);");
            db.execSQL("INSERT INTO food_pattern VALUES (null, 1,0,0,1,0,0,0);");
            db.execSQL("INSERT INTO abode VALUES (null, '단월동','458-101', 1, 0);");
            db.execSQL("INSERT INTO food_favorite (local_name, food, wea, time, weight, f_date) " +
                    "VALUES ('단월동','파전','rain', null, 1, '2015-11-10');");
        }catch (Exception e) {
            Log.i("db","dummy_insert Failed");
        }
    }

    public Cursor select_food_pattern() {
        Cursor cursor = null;
        // cursor = db.rawQuery("select * from food_favorite where local_name = ? AND local_name = ?", new String[] {"David","2"});
        cursor = db.rawQuery("SELECT a,b,c,d,e,f,g FROM food_pattern",null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if(cursor.getCount() >0) {
            cursor.moveToFirst();
            return cursor;
        }

        return null;
    }




    public Cursor select_abode() {
        Cursor cursor =null;
        cursor = db.rawQuery("SELECT * from abode",null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if(cursor.getCount() >0) {
            cursor.moveToFirst();
            return cursor;
        }

        return null;
    }

}

