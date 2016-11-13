package project.jeonghoon.com.nooncoaching;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by han on 2015-11-25.
 */
/*
public class SaveData {
    Context context;
    SharedPreferences SharedData;

    public SaveData(Context c){
        context = c;
    }

    public void save(String name){
        SharedData = context.getSharedPreferences(name, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SharedData.edit();
        if(name.equals("SharedFood")) {
            if(MainActivity.ThemaItem.size()>0)
            {
                List<Item> food = MainActivity.ThemaItem;
                Gson gson = new Gson();
                String jsonFood = gson.toJson(food);
                editor.putString("FoodData", jsonFood);
                editor.commit();
            }

        }
    }


    public ArrayList<Item> getFood(String name){
        SharedData = context.getSharedPreferences(name, context.MODE_PRIVATE);
        String jsonfood = SharedData.getString("FoodData", null);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Item>>(){}.getType();
        List<Item> foodList = gson.fromJson(jsonfood, type);
        return (ArrayList<Item>)foodList;
    }
    public boolean isNews(){
        SharedData = context.getSharedPreferences("SharedNews", context.MODE_PRIVATE);
        if(SharedData.contains("NewsData")){
            return true;
        }else{
            return false;
        }
    }
    public boolean isFood(){
        SharedData = context.getSharedPreferences("SharedFood", context.MODE_PRIVATE);
        if(SharedData.contains("FoodData")){
            return true;
        }else{
            return false;
        }
    }

}
*/