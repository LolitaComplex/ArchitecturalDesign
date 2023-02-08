package com.doing.diproject.home.model;

import java.util.List;

public class HomeList {
    public List<SubcategoryList> subcategoryList;
    public List<GoodsList> goodsList;
    public List<BannerList> bannerList;

    public static class BannerList {
        public String id;
        public Integer sticky;
        public String type;
        public String title;
        public String subtitle;
        public String url;
        public String cover;
        public String createTime;
    }

    public static class SubcategoryList {
        public String subcategoryId;
        public Object groupName;
        public String categoryId;
        public String subcategoryName;
        public String subcategoryIcon;
        public String showType;
    }

    public static class GoodsList {
        public String goodsId;
        public String categoryId;
        public Boolean hot;
        public List<SliderImages> sliderImages;
        public String marketPrice;
        public String groupPrice;
        public String completedNumText;
        public String goodsName;
        public String tags;
        public Object joinedAvatars;
        public String createTime;
        public String sliderImage;

        public static class SliderImages {
            public String url;
            public Integer type;
        }
    }

}
