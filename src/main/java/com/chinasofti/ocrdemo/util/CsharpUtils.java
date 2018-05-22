package com.chinasofti.ocrdemo.util;

import com.chinasofti.ocrdemo.bean.HumanBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import java.util.Map;

public class CsharpUtils {

    //public static String URL = "http://120.78.184.120:8086/AnalyzeResume.asmx/ExtractResume";

    public static String getPostResult(String filePath){

        String URL = "http://120.78.184.120:8086/AnalyzeResume.asmx/ExtractResume";
        // String result = HttpSend.post(filePath);
        Map<String, String> params = new HashMap<String, String>();
        params.put("filePath", filePath);
        String result = HttpXmlClient.post(URL,params);
        System.out.println("result:"+result);
        return result;
    }

    public static String converResult2Joson(String filPath)  {
        String json = getPostResult(filPath);
        try {
            json = new String(json.getBytes("ISO8859-1"), "gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONArray josnStr = JSONArray.fromObject(json );
        String jsonResult = josnStr.get(0).toString();
        HumanBean humanBean = HumanJson2JavaBeanConverter.getJavaBeanObject(jsonResult);
        return jsonResult(humanBean);
    }

    public static String jsonResult(HumanBean humanBean){

        if(humanBean==null){
            return "";
        }
        JSONObject json = JSONObject.fromObject(humanBean);
        return json.toString();
    }
}
