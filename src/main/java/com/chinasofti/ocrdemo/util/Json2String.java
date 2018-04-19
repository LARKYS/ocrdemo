package com.chinasofti.ocrdemo.util;

import com.chinasofti.ocrdemo.bean.*;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Json2String {

    public  String toString(List<String> json){

        LineItems lineItems = new LineItems();
        List<Items> itemss = new ArrayList<>();
        Gson gsone = new Gson();
        int num = 1;
        for (String ss : json){
            TenJsonBean jsonBean = gsone.fromJson(ss, TenJsonBean.class);
            List<Items> rever = jsonBean.getData().getItems();
            for(Items items : rever) {
                items.setPageNum(num);
            }
            num++;
            itemss.addAll(rever);
        }

        List<Items> itemlines = new ArrayList<>();
        ReadUtil readUtil = new ReadUtil();
        List<Items> endResult = readUtil.getAllInfo(itemlines, itemss);
        lineItems.setLines(endResult);

        for(Items items : endResult) {
            System.out.println("_________++++++"+items.getItemstring());
        }


        JSONObject json1 = JSONObject.fromObject(lineItems);//将java对象转换为json对象

        System.out.println(json1.toString());
        return json1.toString();//将json对象转换为字符串
    }

    public  String getResumeData(List<String> json){

        List<Items> itemss = new ArrayList<>();
        Gson gsone = new Gson();
        int num = 1;
        for (String ss : json){
            TenJsonBean jsonBean = gsone.fromJson(ss, TenJsonBean.class);
            List<Items> rever = jsonBean.getData().getItems();
            for(Items items : rever) {
                items.setPageNum(num);
            }
            num++;
            itemss.addAll(rever);
        }
        List<Items> itemlines = new ArrayList<>();
        ReadUtil readUtil = new ReadUtil();
        List<Items> endResult = readUtil.getAllInfo(itemlines, itemss);
        return   readUtil.getStandarData(endResult);//将json对象转换为字符串
    }

    public String getMicString(List<String> result){

        List<List<Regions>> regions =  micCovert(result);
        List<List<Items>> items = regions2Items(regions);

        System.out.println("++++size:"+items.size());

        List<Items> itemsEnd = new ArrayList<>();
        for(List<Items> list : items){
            getSortList(list);
            itemsEnd.addAll(list);
        }

        for (Items i : itemsEnd) {
            System.out.println("=======end"+i.getItemstring()+"  sort:"+i.getSort());
        }

        List<Items> itemlines = new ArrayList<>();
        ReadUtil readUtil = new ReadUtil();
        List<Items> endResult = readUtil.getMicResult(itemlines, itemsEnd);
        LineItems lineItems = new LineItems();
        lineItems.setLines(endResult);
        JSONObject json1 = JSONObject.fromObject(lineItems);//将java对象转换为json对象
        return json1.toString();//将json对象转换为字符串
    }


    public List<List<Regions>> micCovert(List<String> result){

        List<List<Regions>> regions = new ArrayList<>();
        Gson gsone = new Gson();
        int count = 1;
        for(String re : result){
            if(re==null){
                continue;
            }
            List<Regions> regionsOne = new ArrayList<Regions>();
            MicJsonBean micJsonBean = gsone.fromJson(re,MicJsonBean.class);
            List<Regions> region = micJsonBean.getRegions();
            if(region == null || region.size()==0){
                continue;
            }
            for(Regions regions1 : region){
                if(regions1==null){
                    continue;
                }
                List<Lines> lines = regions1.getLines();
                for(Lines lines1 : lines){
                    lines1.setPageNume(count);
                    StringBuffer stringBuffer = new StringBuffer();
                    List<Words> words = lines1.getWords();
                    for (Words words1 : words){
                        words1.setConfidence("100");
                        words1.setCharacter(words1.getText());
                        stringBuffer.append(words1.getText());
                    }
                    lines1.setItemstring(stringBuffer.toString());
                    //set 位置
                    String [] cood =  lines1.getBoundingBox().split(",");
                    Integer [] coord  = new Integer[cood.length];
                    for (int i=0 ;i<cood.length;i++){
                        coord[i] = Integer.parseInt(cood[i]);
                    }
                    ItemCoord itemCoord = new ItemCoord();
                    itemCoord.setX(coord[0]);
                    itemCoord.setY(coord[1]);
                    itemCoord.setWidth(coord[2]);
                    itemCoord.setHeight(coord[3]);
                    lines1.setItemcoord(itemCoord);
                }
            }
            count++;
            regionsOne.addAll(region);
            regions.add(regionsOne);
        }
        return regions;

    }
    public List<List<Items>> regions2Items(List<List<Regions>> regions){
        List<List<Items>> lists = new ArrayList<>();
        for(List<Regions> regions1 : regions ){
            List<Lines> lines  = new ArrayList<>();
            for(Regions regions2 : regions1){
                lines.addAll(regions2.getLines());
            }
            List<Items> itemsr = getItemsList(lines);
            lists.add(itemsr);
        }
        return lists;
    }

    private List<Items> getItemsList(List<Lines> lines) {
        List<Items> items = new ArrayList<>();
        for (Lines lines1 : lines){
            Items items1 = new Items();
            items1.setPageNum(lines1.getPageNume());
            items1.setItemstring(lines1.getItemstring());
            items1.setWords(lines1.getWords());
            items1.setItemcoord(lines1.getItemcoord());
            items1.setSort(lines1.getItemcoord().getY());
            items.add(items1);
           // System.out.println("====MicSof===Itemstring:"+lines1.getItemstring()+"imcoord:"+items1.getSort());
        }
        //getSortList(items);
        return items;
    }

    public static List<Items> getSortList(List<Items> list){
        Collections.sort(list, (o1, o2) -> {
            if(o1.getSort()>o2.getSort()){
                return 1;
            }
            if(o1.getSort()==o2.getSort()){
                return 0;
            }
            return -1;
        });
        return list;
    }

}
