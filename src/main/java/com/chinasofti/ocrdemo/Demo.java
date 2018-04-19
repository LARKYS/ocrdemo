package com.chinasofti.ocrdemo;

import com.chinasofti.ocrdemo.bean.Items;
import com.chinasofti.ocrdemo.bean.TenJsonBean;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {
    public static void main(String args[]){

        String ss = "联系电话：13901234567职业：公司职员";
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(ss);


        String tt = matcher.replaceAll("");

        System.out.println(tt);



    }
}
