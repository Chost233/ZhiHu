package com.pers.myc.zhihu.untils;

import com.google.gson.Gson;

/**
 * gson相关类
 */

public class GsonUtil {
    //gson解析
    public static Object analysis(String message, Class Parsing) {
        Object export = null;
        if (!message.equals("")) {
            Gson gson = new Gson();
            export = gson.fromJson(message, Parsing);
        }
        return export;
    }
}
