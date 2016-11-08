package com.baidu.translate.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABC on 16/11/8.
 */
public class Result {
    public String error_code;
    public String error_msg;
    public String from;
    public String to;
    public List<Translate> trans_result = new ArrayList<>();

    public Result(JSONObject jsonObject) {
        from = jsonObject.getString("from");
        to = jsonObject.getString("to");

        JSONArray jsonArray = jsonObject.getJSONArray("trans_result");
        for (int index = 0; jsonArray != null && index < jsonArray.size(); index++) {
            trans_result.add(new Translate(jsonArray.getJSONObject(index)));
        }
    }
}
