package com.doing.diproject.detail.model;

import java.util.List;

public class Detail {

    public String goodsId;
    public String categoryId;
    public Boolean hot;
    public List<SliderImagesDTO> sliderImages;
    public String marketPrice;
    public String groupPrice;
    public String completedNumText;
    public String goodsName;
    public String tags;
    public String createTime;
    public Boolean isFavorite;
    public String commentCountTitle;
    public String commentTags;
    public List<CommentModelsDTO> commentModels;
    public ShopDTO shop;
    public List<FlowGoodsDTO> flowGoods;
    public List<GoodAttrDTO> goodAttr;
    public String goodDescription;
    public List<GalleryDTO> gallery;
    public List<SimilarGoodsDTO> similarGoods;
    public String sliderImage;

    public static class ShopDTO {
        public String name;
        public String logo;
        public String goodsNum;
        public String completedNum;
        public String evaluation;
    }

    public static class SliderImagesDTO {
        public String url;
        public Integer type;
    }

    public static class CommentModelsDTO {
        public String avatar;
        public String nickName;
        public String content;
    }

    public static class FlowGoodsDTO {
        public String goodsId;
        public String categoryId;
        public Boolean hot;
        public List<SliderImagesDTO> sliderImages;
        public String marketPrice;
        public String groupPrice;
        public String completedNumText;
        public String goodsName;
        public String tags;
        public String joinedAvatars;
        public String createTime;
        public String sliderImage;

        public static class SliderImagesDTO {
            public String url;
            public Integer type;
        }
    }

    public static class GoodAttrDTO {
        public String 材质种类;
        public String 风格;
    }

    public static class GalleryDTO {
        public String url;
        public Integer type;
    }

    public static class SimilarGoodsDTO {
        public String goodsId;
        public String categoryId;
        public Boolean hot;
        public List<SliderImagesDTO> sliderImages;
        public String marketPrice;
        public String groupPrice;
        public String completedNumText;
        public String goodsName;
        public String tags;
        public List<JoinedAvatarsDTO> joinedAvatars;
        public String createTime;
        public String sliderImage;

        public static class SliderImagesDTO {
            public String url;
            public Integer type;
        }

        public static class JoinedAvatarsDTO {
            public String url;
            public Integer type;
        }
    }
}
