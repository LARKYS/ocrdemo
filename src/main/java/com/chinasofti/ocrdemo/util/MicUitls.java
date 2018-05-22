package com.chinasofti.ocrdemo.util;

import com.chinasofti.ocrdemo.bean.Items;
import com.chinasofti.ocrdemo.bean.Regions;

import java.util.ArrayList;
import java.util.List;

public class MicUitls {

    public  String getMicResumeData(List<String> resultList){
        Json2String json2String = new Json2String();
        // json2String.getMicString(result);

        List<List<Regions>> regions = json2String.micCovert(resultList);
        List<List<Items>> items = json2String.regions2Items(regions);

        System.out.println("++++size:" + items.size());

        List<Items> itemsEnd = new ArrayList<>();
        for (List<Items> list : items) {
            json2String.getSortList(list);
            itemsEnd.addAll(list);
        }
        itemsEnd.remove(itemsEnd.size()-1);
        List<Items> itemlines = new ArrayList<>();
        ReadUtil readUtil = new ReadUtil();
        List<Items> endResult = readUtil.getMicAllInfo(itemlines, itemsEnd);
        String results = readUtil.getStandarData(endResult);
        System.out.println(results);
        return results;
    }
}
