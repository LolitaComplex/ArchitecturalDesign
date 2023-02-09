package com.doing.diproject.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
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

    public static class GoodsList implements Parcelable {
        public String goodsId;
        public String categoryId;
        public Boolean hot;
        public List<SliderImages> sliderImages;
        public String marketPrice;
        public String groupPrice;
        public String completedNumText;
        public String goodsName;
        public String tags;
        public String joinedAvatars;
        public String createTime;
        public String sliderImage;

        public static class SliderImages {
            public String url;
            public Integer type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.goodsId);
            dest.writeString(this.categoryId);
            dest.writeValue(this.hot);
            dest.writeList(this.sliderImages);
            dest.writeString(this.marketPrice);
            dest.writeString(this.groupPrice);
            dest.writeString(this.completedNumText);
            dest.writeString(this.goodsName);
            dest.writeString(this.tags);
            dest.writeString(this.joinedAvatars);
            dest.writeString(this.createTime);
            dest.writeString(this.sliderImage);
        }

        public void readFromParcel(Parcel source) {
            this.goodsId = source.readString();
            this.categoryId = source.readString();
            this.hot = (Boolean) source.readValue(Boolean.class.getClassLoader());
            this.sliderImages = new ArrayList<SliderImages>();
            source.readList(this.sliderImages, SliderImages.class.getClassLoader());
            this.marketPrice = source.readString();
            this.groupPrice = source.readString();
            this.completedNumText = source.readString();
            this.goodsName = source.readString();
            this.tags = source.readString();
            this.joinedAvatars = source.readString();
            this.createTime = source.readString();
            this.sliderImage = source.readString();
        }

        public GoodsList() {
        }

        protected GoodsList(Parcel in) {
            this.goodsId = in.readString();
            this.categoryId = in.readString();
            this.hot = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.sliderImages = new ArrayList<SliderImages>();
            in.readList(this.sliderImages, SliderImages.class.getClassLoader());
            this.marketPrice = in.readString();
            this.groupPrice = in.readString();
            this.completedNumText = in.readString();
            this.goodsName = in.readString();
            this.tags = in.readString();
            this.joinedAvatars = in.readParcelable(Object.class.getClassLoader());
            this.createTime = in.readString();
            this.sliderImage = in.readString();
        }

        public static final Parcelable.Creator<GoodsList> CREATOR = new Parcelable.Creator<GoodsList>() {
            @Override
            public GoodsList createFromParcel(Parcel source) {
                return new GoodsList(source);
            }

            @Override
            public GoodsList[] newArray(int size) {
                return new GoodsList[size];
            }
        };
    }

}
