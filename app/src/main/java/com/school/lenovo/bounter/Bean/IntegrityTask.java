package com.school.lenovo.bounter.Bean;

import android.media.Image;

/**
 * Created by lenovo on 2016/12/8.
 */

public class IntegrityTask {
    private ITData data;

    public ITData getData() {
        return data;
    }

    public void setData(ITData data) {
        this.data = data;
    }

    public class ITData{
        private String title;
        private String content;
        private String address;
        private String reward;
        private String[] image;
        private String label;
        private String rank;
        private String click;
        private ApplyUser [] apply_user;

        public String[] getImage() {
            return image;
        }

        public void setImage(String[] image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getClick() {
            return click;
        }

        public void setClick(String click) {
            this.click = click;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public ApplyUser[] getApply_user() {
            return apply_user;
        }

        public void setApply_user(ApplyUser[] apply_user) {
            this.apply_user = apply_user;
        }

        public class ApplyUser{
            private String portrait;
            private String username;
            private String level;

            public String getPortrait() {
                return portrait;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }
        }
    }
}
