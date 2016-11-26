package com.school.lenovo.bounter.Util;

import android.os.Handler;
import android.telecom.Call;
import android.util.Log;

import com.google.gson.Gson;
import com.school.lenovo.bounter.Bean.LoginMessage;
import com.school.lenovo.bounter.Bean.Task;
import com.school.lenovo.bounter.Bean.TaskListContainer;

import java.io.IOException;
import java.util.List;

import okhttp3.Callback;
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

    public static final String BASE = "http://api.weafung.com/index.php/";
    public static final String LOGIN = "Auth/login";//登陆
    public static final String REGISTER = "Auth/register";//注册
    public static final String TASKLIST = "Hall/getTaskList";//任务列表
    public static final String MYRELEASE = "Task/getMyRelease";//我发布的任务
    public static final String MYRECEIVE = "Task/getMyReceive";//我接受的任务
    public static final String RELEASE = "Task/release";//发布任务
    public static final String VERIFY = "User/verify";//身份认证
    public static final String PROFILE = "User/getProfile";//获取用户信息
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //页面的登陆
    public static String Login(String username, String password) {
        String jsonString = StringToJson.LoginToJson(username, password);
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, jsonString);
        Request request = new Request.Builder()
                .url(BASE + LOGIN)
                .method("Post", null)
                .post(requestBody)
                .build();
        try {
            okHttpClient.newCall(request).execute();
            Response response = okHttpClient.newCall(request).execute();
            if (response.message().equals("OK")) {
                String result = new String(response.body().bytes());
                if (!result.contains('"' + "error_code" + '"' + ":0")) {
                    Log.i(TAG, "密码出错");
                    return "密码出错";
                } else {
                    Log.i(TAG, "登陆成功");
                    Gson gson = new Gson();
                    LoginMessage loginMessage = gson.fromJson(result, LoginMessage.class);
                    Token.Token = loginMessage.getData().getToken();
                    Log.i(TAG, Token.Token);
                    return "登陆成功";
                }
            } else {
                return "网络出错";
            }
        } catch (IOException e) {
            Log.i(TAG, "网络出错");
            e.printStackTrace();
            return "网络出错";
        }
    }

    //注册
    public static String Register(String username, String password, String password_2) {
        String jsonString = StringToJson.RegisterToJson(username, password, password_2);
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, jsonString);
        Request request = new Request.Builder()
                .url(BASE + REGISTER)
                .method("Post", null)
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.message().equals("OK")) {
                String result = new String(response.body().bytes());
                if (result.contains('"' + "error_code" + '"' + ":0")) {
                    return "注册成功";
                } else {
                    return "用户名已存在";
                }
            } else {
                return "网络出错";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "网络出错";
        }
    }

    //任务获取，参数为：page:页数，size:每页的数量,order_by:排序方法,state:任务状态（详情看文档），未完成
    public static List<Task> getTaskList(int page, int size, String order_by, int state) {
        String jsonString = StringToJson.TaskToJson(page, size, order_by, state);
        Log.d(TAG, jsonString.toString());
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, jsonString);
        Request request = new Request.Builder()
                .url(BASE + TASKLIST)
                .method("Post", null)
                .post(requestBody)
                .build();
        //返回为空表示无数据（网络问题等。。。）
        try {
            Response response = okHttpClient.newCall(request).execute();
            String result = new String(response.body().bytes());
            Log.d(TAG, result.toString());
            if (response.message().equals("OK")) {
                if (result.contains('"' + "error_code" + '"' + ":0")) {
                    Gson gson = new Gson();
                    TaskListContainer taskListContainer = gson.fromJson(result, TaskListContainer.class);
//                    Log.d(TAG, taskListContainer.getData().getList().get(1).getReward());
                    if (taskListContainer.getData().getList().size() == 0){
                        return null;
                    }else{
                        return taskListContainer.getData().getList();
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "WRONG");
            return null;
        }
    }

    //查看我发布的任务
    public static List<Task> getMyRelease() {
        OkHttpClient okHttpClient = new OkHttpClient();
        String jsonString = StringToJson.TokenToJson(Token.Token);
        RequestBody requestBody = RequestBody.create(JSON, jsonString);
        Request request = new Request.Builder()
                .url(BASE + MYRELEASE)
                .method("Post", null)
                .post(requestBody)
                .build();
        //返回为空表示无数据（网络问题等。。。）
        try {
            Response response = okHttpClient.newCall(request).execute();
            String result = new String(response.body().bytes());
            Log.d(TAG, "getMyRelease is " + result.toString());
            if (response.message().equals("OK")) {
                if (result.contains('"' + "error_code" + '"' + ":0")) {
                    Gson gson = new Gson();
                    TaskListContainer taskListContainer = gson.fromJson(result, TaskListContainer.class);
                    Log.d(TAG, taskListContainer.getData().getList().get(0).getReward());
                    return taskListContainer.getData().getList();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "WRONG");
            return null;
        }
    }

    public static List<Task> getMyReceive() {
        OkHttpClient okHttpClient = new OkHttpClient();
        String jsonString = StringToJson.TokenToJson(Token.Token);
        RequestBody requestBody = RequestBody.create(JSON, jsonString);
        Request request = new Request.Builder()
                .url(BASE + MYRECEIVE)
                .method("Post", null)
                .post(requestBody)
                .build();
        //返回为空表示无数据（网络问题等。。。）
        try {
            Response response = okHttpClient.newCall(request).execute();
            String result = new String(response.body().bytes());
            Log.d(TAG, "MYRECEIVE is " + result.toString());
            if (response.message().equals("OK")) {
                if (!result.contains('"' + "count" + '"' + ":0")) {
                    Gson gson = new Gson();
                    TaskListContainer taskListContainer = gson.fromJson(result, TaskListContainer.class);
//                    Log.d(TAG,taskListContainer.getData().getList().get(0).getReward());
                    return taskListContainer.getData().getList();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "WRONG");
            return null;
        }
    }

    public static String Release(String token, String title, String address, String content, String start, String end, String number, String label, String reward, String image) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String jsonString = StringToJson.ReleaseToJson(token, title, address, content, start, end, number, label, reward, image);
        RequestBody requestBody = RequestBody.create(JSON, jsonString);
        Request request = new Request.Builder()
                .url(BASE + RELEASE)
                .method("Post", null)
                .post(requestBody)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            String result = new String(response.body().bytes());
            Log.d(TAG,result);
            if (response.message().equals("OK")) {
                if (!result.contains('"' + "error_code" + '"' + ":0")) {
                    Log.d(TAG,"OK");
                    return "发布成功";
                } else {
                    return "发布失败";
                }
            } else {
                return "发布失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "发布失败";
        }
    }
}
