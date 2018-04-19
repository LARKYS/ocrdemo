package com.chinasofti.ocrdemo.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;

public class SaveHelper {


    public boolean save(String json){
        Logger logger = Logger.getLogger(SaveHelper.class);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String,String>> newMap = null;
            try {
                newMap = objectMapper.readValue(json, List.class);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            List exportData = new ArrayList();
            LinkedHashMap map = new LinkedHashMap();
            if(newMap.get(0).get("category").equals("resume")){
                 exportData = getResumeRowList(newMap);
                 map = getResumeCsvMap();
            }else if(newMap.get(0).get("category").equals("invoice")){
                map = getInvoiceCsvMap();
                exportData = getInvoiceRowList(newMap);
            }else if(newMap.get(0).get("category").equals("insurance")){
                map = getInsuranceMap();
                exportData = getInsuranceRowList(newMap);
            }

            String path = "C:/export/";
            String fileName = "文件导出";
            File file = CSVUtils.createCSVFile(exportData, map, path, fileName);
            String fileName2 = file.getName();
            logger.info("文件名称：" + fileName2);
            return true;
        }catch (Exception e){
            logger.info("保存失败");
            return false;
        }

    }
    private List getInsuranceRowList(List<Map<String, String>> newMap){
        Map data = new LinkedHashMap<String, String>();

        data.put("所属分支机构",newMap.get(0).get("itemstring"));
        data.put("被保险人保险合同编号",newMap.get(1).get("itemstring"));
        data.put("被保险人姓名",newMap.get(2).get("itemstring"));
        data.put("被保险人性别",newMap.get(3).get("itemstring"));
        data.put("被保险人年龄",newMap.get(4).get("itemstring"));
        data.put("被保险人联系地址",newMap.get(5).get("itemstring"));
        data.put("被保险人联系电话",newMap.get(6).get("itemstring"));
        data.put("被保险人职业",newMap.get(7).get("itemstring"));
        data.put("申请人性别",newMap.get(8).get("itemstring"));
        data.put("申请人年龄",newMap.get(9).get("itemstring"));
        data.put("申请人联系电话",newMap.get(10).get("itemstring"));
        data.put("申请人姓名",newMap.get(11).get("itemstring"));
        data.put("申请人联系地址",newMap.get(12).get("itemstring"));
        data.put("申请人与出险被保险人关系",newMap.get(13).get("itemstring"));

        List exportData = new ArrayList<Map>();
        Map row1 = new LinkedHashMap<String, String>();

        row1.put("1",data.get("所属分支机构"));
        row1.put("2",data.get("被保险人保险合同编号"));
        row1.put("3",data.get("被保险人姓名"));
        row1.put("4",data.get("被保险人性别"));
        row1.put("5",data.get("被保险人年龄"));
        row1.put("6",data.get("被保险人联系地址"));
        row1.put("7",data.get("被保险人联系电话"));
        row1.put("8",data.get("被保险人职业"));
        row1.put("9",data.get("申请人性别"));
        row1.put("10",data.get("申请人年龄"));
        row1.put("11",data.get("申请人联系电话"));
        row1.put("12",data.get("申请人姓名"));
        row1.put("13",data.get("申请人联系地址"));
        row1.put("14",data.get("申请人与出险被保险人关系"));

        exportData.add(row1);
        return exportData;
    }

    private LinkedHashMap getInsuranceMap() {
        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "所属分支机构");
        map.put("2", "被保险人保险合同编号");
        map.put("3", "被保险人姓名");
        map.put("4", "被保险人性别");
        map.put("5", "被保险人年龄");
        map.put("6", "被保险人联系地址");
        map.put("7", "被保险人联系电话");
        map.put("8", "被保险人职业");
        map.put("9",  "申请人性别");
        map.put("10", "申请人年龄");
        map.put("11", "申请人联系电话");
        map.put("12", "申请人姓名");
        map.put("13", "申请人联系地址");
        map.put("14", "申请人与出险被保险人关系");
        return map;
    }

    private LinkedHashMap getResumeCsvMap() {
        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "name");
        map.put("2", "jobStatus");
        map.put("3", "cellphone");
        map.put("4", "email");
        map.put("5","gender");
        map.put("6","birthday");
        map.put("7","position");
        map.put("8","major");
        map.put("9","company");
        map.put("10","graduateSchool");
        map.put("11","profession");
        map.put("12","education");
        map.put("13","expectedSalary");
        map.put("14","targetWorkPlace");
        map.put("15","evaluation");
        map.put("16","projectExperience");
        map.put("17","workExperience");
        map.put("18","workSkills");
        map.put("19","trainingExperience");
        return map;
    }

    private LinkedHashMap getInvoiceCsvMap() {
        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "company1");
        map.put("2", "taxpayerNum1");
        map.put("3", "addressTel1");
        map.put("4", "number");
        map.put("5", "money");
        map.put("6","price");
        map.put("7", "company2");
        map.put("8", "taxpayerNum3");
        map.put("9", "addressTel4");
        return map;
    }

    private List getInvoiceRowList(List<Map<String, String>> newMap){
        Map data = new LinkedHashMap<String, String>();

        StringBuffer evaluation = new StringBuffer();
        StringBuffer projectExperience = new StringBuffer();
        StringBuffer workExperience = new StringBuffer();
        StringBuffer workSkills = new StringBuffer();
        StringBuffer trainingExperience = new StringBuffer();
        data.put("company1",newMap.get(4).get("itemstring"));
        data.put("taxpayerNum1",newMap.get(6).get("itemstring"));
        data.put("addressTel1",newMap.get(10).get("itemstring"));
        data.put("number",newMap.get(21).get("itemstring"));
        data.put("money",newMap.get(25).get("itemstring"));
        data.put("price",newMap.get(23).get("itemstring"));
        data.put("company2",newMap.get(34).get("itemstring"));
        data.put("taxpayerNum2",newMap.get(37).get("itemstring"));
        data.put("addressTel2",newMap.get(41).get("itemstring"));

        List exportData = new ArrayList<Map>();
        Map row1 = new LinkedHashMap<String, String>();

        row1.put("1",data.get("company1"));
        row1.put("2",data.get("taxpayerNum1"));
        row1.put("3",data.get("addressTel1"));
        row1.put("4",data.get("number"));
        row1.put("5",data.get("money"));
        row1.put("6",data.get("price"));
        row1.put("7",data.get("company2"));
        row1.put("8",data.get("taxpayerNum2"));
        row1.put("9",data.get("addressTel2"));

        exportData.add(row1);
        return exportData;
    }

    private List getResumeRowList(List<Map<String, String>> newMap) {
        Map data = new LinkedHashMap<String, String>();

        StringBuffer evaluation = new StringBuffer();
        StringBuffer projectExperience = new StringBuffer();
        StringBuffer workExperience = new StringBuffer();
        StringBuffer workSkills = new StringBuffer();
        StringBuffer trainingExperience = new StringBuffer();
        for(Map<String,String> map : newMap){
            if(map.get("key").equals("name")){
                data.put("name",map.get("itemstring"));
            }
            if(map.get("key").equals("jobStatus")){
                data.put("jobStatus",map.get("itemstring"));
            }
            if(map.get("key").equals("cellphone")){
                data.put("cellphone",map.get("itemstring"));
            }
            if(map.get("key").equals("email")){
                data.put("email",map.get("itemstring"));
            }
            if(map.get("key").equals("gender")){
                data.put("gender",map.get("itemstring"));
            }
            if(map.get("key").equals("birthday")){
                data.put("birthday",map.get("itemstring"));
            }
            if(map.get("key").equals("position")){
                data.put("position",map.get("itemstring"));
            }
            if(map.get("key").equals("major")){
                data.put("major",map.get("itemstring"));
            }
            if(map.get("key").equals("company")){
                data.put("company",map.get("itemstring"));
            }
            if(map.get("key").equals("graduateSchool")){
                data.put("graduateSchool",map.get("itemstring"));
            }
            if(map.get("key").equals("profession")){
                data.put("profession",map.get("itemstring"));
            }
            if(map.get("key").equals("education")){
                data.put("education",map.get("itemstring"));
            }
            if(map.get("key").equals("expectedSalary")){
                data.put("expectedSalary",map.get("itemstring"));
            }
            if(map.get("key").equals("targetWorkPlace")){
                data.put("targetWorkPlace",map.get("itemstring"));
            }
            if(map.get("key").equals("evaluation")){
                data.put("evaluation",evaluation.append(map.get("itemstring")));
            }
            if(map.get("key").equals("projectExperience")){
                data.put("projectExperience",projectExperience.append(map.get("itemstring")));
            }
            if(map.get("key").equals("workExperience")){
                data.put("workExperience",workExperience.append(map.get("itemstring")));
            }
            if(map.get("key").equals("workSkills")){
                data.put("workSkills",workSkills.append(map.get("itemstring")));
            }
            if(map.get("key").equals("trainingExperience")){
                data.put("trainingExperience",trainingExperience.append(map.get("itemstring")));
            }
        }
        List exportData = new ArrayList<Map>();
        Map row1 = new LinkedHashMap<String, String>();

        row1.put("1",data.get("name"));
        row1.put("2",data.get("jobStatus"));
        row1.put("3",data.get("cellphone"));
        row1.put("4",data.get("email"));
        row1.put("5",data.get("gender"));
        row1.put("6",data.get("birthday"));
        row1.put("7",data.get("position"));
        row1.put("8",data.get("major"));
        row1.put("9",data.get("company"));
        row1.put("10",data.get("graduateSchool"));
        row1.put("11",data.get("profession"));
        row1.put("12",data.get("education"));
        row1.put("13",data.get("expectedSalary"));
        row1.put("14",data.get("targetWorkPlace"));
        row1.put("15",data.get("evaluation"));
        row1.put("16",data.get("projectExperience"));
        row1.put("17",data.get("workExperience"));
        row1.put("18",data.get("workSkills"));
        row1.put("19",data.get("trainingExperience"));

        exportData.add(row1);
        return exportData;
    }
}
