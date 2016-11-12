package com.school.lenovo.bounter.Util;
import android.util.Log;

import com.google.gson.Gson;
import com.school.lenovo.bounter.Bean.LoginMessage;
import com.school.lenovo.bounter.Bean.Task;
import com.school.lenovo.bounter.Bean.TaskListContainer;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2016/11/4.
 */
//http://api.weafung.com/docs/index.php（接口文档）
public class HttpUtil {
    private static final String TAG = "HttpUtil";
    public static final String LOGIN= "http://api.weafung.com/index.php/Auth/login";//登陆用接口
    public static final String REGISTER = "http://api.weafung.com/index.php/Auth/register";//注册用接口
    public static final String TASKLIST ="http://api.weafung.com/index.php/Hall/getTaskList";//任务列表
    public static final String VERIFY="http://api.weafung.com/index.php/User/verify";//身份认证
    public static final String PROFILE="http://api.weafung.com/index.php/User/getProfile";//获取用户信息
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //页面的登陆
    public static String Login(String username,String password){
        String jsonString = StringToJson.LoginToJson(username,password);
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody =RequestBody.create(JSON,jsonString);
        Request request = new Request.Builder()
                .url(LOGIN)
                .method("Post",null)
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.message().equals("OK")){
                String result = new String(response.body().bytes());
                if (!result.contains('"'+"error_code"+'"'+":0")){
                    Log.i(TAG,"密码出错");
                    return "密码出错";
                }else{
                    Log.i(TAG,"登陆成功");
                    Gson gson = new Gson();
                    LoginMessage loginMessage = gson.fromJson(result,LoginMessage.class);
                    Token.Token = StringToJson.TokenToJson(loginMessage.getData().getToken());
                    Log.i(TAG,Token.Token);
                    return "登陆成功";
                }
            }else{
                return "网络出错";
            }
        } catch (IOException e) {
            Log.i(TAG,"网络出错");
            e.printStackTrace();
            return "网络出错";
        }
    }
    //注册
    public static String Register(String username,String password,String password_2){
        String jsonString = StringToJson.RegisterToJson(username, password, password_2);
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody =RequestBody.create(JSON,jsonString);
        Request request = new Request.Builder()
                .url(REGISTER)
                .method("Post",null)
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.message().equals("OK")){
                String result = new String(response.body().bytes());
                if (result.contains('"'+"error_code"+'"'+":0")){
                    return "注册成功";
                }else{
                    return "用户名已存在";
                }
            }else{
                return "网络出错";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "网络出错";
        }
    }
    //任务获取，参数为：page:页数，size:每页的数量,order_by:排序方法,state:任务状态（详情看文档），未完成
    public static List<Task> getTaskList(int page, int size, String order_by, int state){
        String jsonString = StringToJson.TaskToJson(page, size, order_by, state);
        Log.d(TAG,jsonString.toString());
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody =RequestBody.create(JSON,jsonString);
        Request request = new Request.Builder()
                .url(TASKLIST)
                .method("Post",null)
                .post(requestBody)
                .build();
        //返回为空表示无数据（网络问题等。。。）
        try {
            Response response = okHttpClient.newCall(request).execute();
            String result = new String(response.body().bytes());
            Log.d(TAG,result.toString());
            if(response.message().equals("OK")){
                if (result.contains('"'+"error_code"+'"'+":0")){
                    Gson gson = new Gson();
                    TaskListContainer taskListContainer = gson.fromJson(result,TaskListContainer.class);
                    Log.d(TAG,taskListContainer.getData().getList().get(1).getReward());
                    return taskListContainer.getData().getList();
                }else{
                    return null;
                }
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"WRONG");
            return null;
        }
    }
}
