package com.school.lenovo.bounter.Bean;

/**
 * Created by lenovo on 2016/11/5.
 */
//登陆信息的保存
public class LoginMessage {
    private MyData data;
    public MyData getData() {
        return data;
    }

    public void setData(MyData data) {
        this.data = data;
    }

    public class MyData{
        private String token;
        private int expires_time;
        private User user;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getExpires_time() {
            return expires_time;
        }

        public void setExpires_time(int expires_time) {
            this.expires_time = expires_time;
        }
    }
}
