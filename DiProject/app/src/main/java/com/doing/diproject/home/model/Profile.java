package com.doing.diproject.home.model;

import java.util.List;

public class Profile {
    public boolean isLogin;
    public int favoriteCount;
    public int browseCount;
    public int learnMinutes;
    public List<BannerNoticeListDTO> bannerNoticeList;
    public String userName;
    public String userIcon;
    public String userId;

    public static class BannerNoticeListDTO {
        public String id;
        public int sticky;
        public String type;
        public String title;
        public String subtitle;
        public String url;
        public String cover;
        public String createTime;
    }
}
