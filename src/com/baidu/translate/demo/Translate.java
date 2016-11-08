package com.baidu.translate.demo;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by ABC on 16/11/8.
 */
public class Translate {
    public String src;
    public String dst;

    public Translate(JSONObject jsonObject) {
        src = jsonObject.getString("src");
        dst = jsonObject.getString("dst");
    }
}
