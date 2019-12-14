package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KeywordsModel {
    @SerializedName("keywords_hot")
    public List<String> lsKeywordsHot;

    @SerializedName("keywords_searched")
    public List<String> lsKeywordsSearched;

    @SerializedName("keywords_popular")
    public List<String> lsKeywordsPopular;

    public class KeywordParser{
        @SerializedName("status")
        public Integer status;

        @SerializedName("content")
        public String content;

        @SerializedName("data")
        public KeywordsModel dataKeywordsModel;
    }
}
