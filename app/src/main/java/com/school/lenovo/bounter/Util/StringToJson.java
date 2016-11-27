package com.school.lenovo.bounter.Util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

/**
 * Created by lenovo on 2016/11/5.
 */
//用于将文本数据转换成json数据
public class StringToJson {
    public static String TokenToJson(String token){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token",token);
        return jsonObject.toString();
    }
    public static String LoginToJson(String username,String password){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",username);
        jsonObject.addProperty("password",password);
        return jsonObject.toString();
    }
    public static String RegisterToJson(String username,String password,String password_2){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",username);
        jsonObject.addProperty("password",password);
        jsonObject.addProperty("password_2",password_2);
        return jsonObject.toString();
    }
    public static String TaskToJson(int page,int size,String order_by,int state){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("page",""+page);
        jsonObject.addProperty("size",""+size);
        jsonObject.addProperty("order_by",order_by);
        jsonObject.addProperty("state",""+state);
        return jsonObject.toString();
    }
    public static String ReleaseToJson(String token,String title,String address,String content,String start,String end,String number,String label,String reward,String[] image,int ImageCoount){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token",token);
        jsonObject.addProperty("title",title);
        jsonObject.addProperty("address",address);
        jsonObject.addProperty("content",content);
        jsonObject.addProperty("start",start);
        jsonObject.addProperty("end",end);
        jsonObject.addProperty("number",number);
        jsonObject.addProperty("label",label);
        jsonObject.addProperty("reward",reward);
//        jsonObject.addProperty("image",image);
        String tempString = "";
        for (int i = 0; i <= ImageCoount; i++) {
            Log.d("info",image[i]);
            if (i== image.length){
                tempString+=image[i];
            }else{
                tempString+=image[i]+",";
            }
        }
        jsonObject.addProperty("image",tempString);
        return jsonObject.toString();
    }
}
