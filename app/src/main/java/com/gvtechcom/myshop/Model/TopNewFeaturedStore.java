package com.gvtechcom.myshop.Model;

import java.util.List;

public class TopNewFeaturedStore {
    public List<TopSelection> top_selections;
    public List<NewForYou> news_for_you;
    public List<FeatureBrands> feature_brands;
    public List<StoreYouLove> stores_you_love;

    public class TopSelection{
        public String titleTopselect;
        public int iconTopSelect;
        public String product_id;
        public String product_image;
    }

    public class NewForYou{
        public String titleNewForYou;
        public int iconNewForYou;
        public String product_id;
        public String product_image;
    }

    public class FeatureBrands{
        public String titleFeatureBrands;
        public int iconFeatureBrands;
        public String brand_id;
        public String user_id;
        public String image;
    }

    public class StoreYouLove{
        public String titleStoreYouLove;
        public int iconStoreYouLove;
        public String store_id;
        public String address_id;
        public String image;
    }

}
