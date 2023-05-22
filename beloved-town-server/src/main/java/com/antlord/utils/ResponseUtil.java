package com.antlord.utils;

import com.alibaba.fastjson2.JSON;
import com.antlord.bean.Result;
import com.antlord.enums.RsultCodeEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseUtil {

    /**
     * 响应信息
     *
     * @param response 返回信息
     */
    public static void resultMsg(HttpServletResponse response, RsultCodeEnum resultCode) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out;
        try {
            Result result = Result.error(resultCode);
            out = response.getWriter();
            out.append(JSON.toJSONString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
