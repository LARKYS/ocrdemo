package com.chinasofti.ocrdemo.util;

import com.chinasofti.ocrdemo.bean.Items;
import com.chinasofti.ocrdemo.bean.Words;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ReadUtil {


    static int[] getIndex(List<Items> itemss, String line1, String line2){

        int[] tt = new int[2];
        for(Items i : itemss){
            if(i.getItemstring().equals(line1)){
                tt[0] = itemss.indexOf(i);
            }
            if(i.getItemstring().equals(line2)){
                tt[1] = itemss.indexOf(i);
            }
        }
        return tt;
    }

    static int getIndexOne(List<Items> itemss, String line1){
        int index = 0;
        for(Items i : itemss){
            if(i.getItemstring().equals(line1)){
                index = itemss.indexOf(i);
            }
        }
        return index;
    }

    public  List<Items>  getAllInfo(List<Items> itemlines, List<Items> itemss) {



        boolean invoice = false;
        for (Items items : itemss){
            if(items.getItemstring().contains("发票")
                    ||items.getItemstring().contains("支票")
                    ||items.getItemstring().contains("保险")){
                invoice = true;
            }
        }
        if(invoice){
            for (Items items : itemss){
                items.setCategory("invoice");
            }
            return itemss;
        }

        boolean page1 = false;
        for (Items items : itemss){
            if(items.getItemstring().equals("最近工作")){
                page1 = true;
            }
            items.setCategory("resume");
        }
        if(page1){
            for (Items items : itemss){
                items.setCategory("resume");
                System.out.println("+++++++++++++"+items.getItemstring());
            }
            getBaseInfo(itemlines,itemss);
        }
        //工作年限 workYears
        //开始工作时间


        //婚姻状态
        //所在城市
        //地址

        getPerInfo(itemlines, itemss);

        //到岗时间

        //期望月薪
        //工作性质
        //自我评价

        getJobInten(itemlines, itemss);

        //项目经历
        getProjecEx(itemlines, itemss);
        //工作经历
        getJobExp(itemlines, itemss);

        //教育经历
        getEducation(itemlines, itemss);

        //语言
        //技能
        getSkills(itemlines, itemss);
        //培训经历
        getTrain(itemlines, itemss);
        return itemlines;
    }

    private void getBaseInfo(List<Items> itemlines,List<Items> itemss) {
            //姓名
            itemss.get(1).setKey("name");
            itemlines.add(itemss.get(1));
            //求职状态
            itemss.get(2).setKey("jobStatus");
            itemlines.add( itemss.get(2));
            //电话
            itemss.get(3).setKey("cellphone");
            itemlines.add( itemss.get(3));
            //email
            itemss.get(4).setKey("email");
            itemlines.add( itemss.get(4));
            //学历
            if(itemss.get(19).getItemstring().equals("个人信息")){

                itemss.get(18).setKey("education");
                itemss.get(18).setItemstring(itemss.get(18).getItemstring().split(":")[1]);
                itemlines.add(itemss.get(18));
            }else{
                itemss.get(19).setKey("education");
                itemlines.add(itemss.get(19));
            }
            //公司职位
            itemss.get(9).setKey("position");
            itemlines.add(itemss.get(9));
            //专业
            itemss.get(11).setKey("major");
            itemlines.add(itemss.get(11));
            //公司名称
            itemss.get(13).setKey("company");
            itemlines.add( itemss.get(13));
            //毕业学校
            itemss.get(15).setKey("graduateSchool");
            itemlines.add( itemss.get(15));
            //行业
            itemss.get(17).setKey("profession");
            itemlines.add( itemss.get(17));
            //性别
            getGener(itemlines,itemss.get(5));
            //生日
            getBirth(itemlines, itemss.get(5));
    }

    private static void getGener(List<Items> itemlines, Items itemss) {
        if(itemss.getItemstring().contains("男")
                ||itemss.getItemstring().contains("女")) {
            Items gender = new Items();
            gender.setKey("gender");
            gender.setItemcoord(itemss.getItemcoord());
            if (itemss.getItemstring().contains("男")) {
                gender.setItemstring("男");
            } else {
                gender.setItemstring("女");
            }
            List<Words> words = new ArrayList<>();
            for (Words word : itemss.getWords()) {
                if (word.getCharacter().equals("男")
                        || word.getCharacter().equals("女")) {
                    words.add(word);
                    gender.setWords(words);
                }
            }
            gender.setPageNum(itemss.getPageNum());
            itemlines.add(gender);

        }
    }

    private static void getBirth(List<Items> itemlines, Items itemss) {
        System.out.println("=====birth=====start=====");
        String birthDay  = itemss.getItemstring().replaceAll("[^(0-9)\\/(0-9)\\/(0-9)]", "");
        System.out.println("=====birth====birthDay2:"+birthDay);
        String birthDay2 =null;
        if(birthDay.length()<12){
            birthDay2 = "";
        }else {
            birthDay.substring(2,12);
        }
        System.out.println("=====birth====birthDay2:"+birthDay2);
        Items birthdays = new Items();
            birthdays.setKey("birthday");
            birthdays.setItemstring(birthDay2);
            birthdays.setItemcoord(itemss.getItemcoord());
        birthdays.setPageNum(itemss.getPageNum());
            List<Words> birthWords = new ArrayList<>();
            List<String> words = Arrays.asList(birthDay2);
            for(String s : words){
                Words words1 = new Words();
                words1.setCharacter(s);
                words1.setConfidence("100");
                birthWords.add(words1);
            }
            birthdays.setWords(birthWords);
        itemlines.add(birthdays);
    }

    private static void getPerInfo(List<Items> itemlines, List<Items> itemss) {

        int[] tt = new int[2];
        for(Items i : itemss){
            if(i.getItemstring().equals("个人信息")){
                tt[0] = itemss.indexOf(i);
            }
            if(i.getItemstring().contains("目前年收入")){
                tt[1] = itemss.indexOf(i);
            }else if(i.getItemstring().contains("求职意向")){
                tt[1] = itemss.indexOf(i);
            }
        }
        if(tt[0]==-1||tt[0]==0){
            return;
        }


        List<Items> status = itemss.subList(tt[0]+1,tt[1]);
        for(Items rr : status){
            if(rr.getItemstring().equals("婚姻状况:")){
                status.get(status.indexOf(rr)+1).setKey("maritalStatus");
                itemlines.add( status.get(status.indexOf(rr)+1));
            }
            if(rr.getItemstring().equals("户口/国籍:")){
                status.get(status.indexOf(rr)+1).setKey("city");
                itemlines.add( status.get(status.indexOf(rr)+1));
            }
            if(rr.getItemstring().equals("家庭地址:")){
                status.get(status.indexOf(rr)+1).setKey("address");
                itemlines.add( status.get(status.indexOf(rr)+1));
            }
        }
    }

    private static void getJobInten(List<Items> itemlines, List<Items> itemss) {
        int[] works = getIndex(itemss,"求职意向","工作经验");
        if(works[0]==-1||works[0]==0){
            return;
        }
        if(works[1]==-1||works[1]==0){
            works[1] = getIndexOne(itemss,"项目经验");
            if(works[1]==-1||works[1]==0){
                works[1] = getIndexOne(itemss,"教育经历");
                if(works[1]==-1||works[1]==0){
                    works[1] = getIndexOne(itemss,"在校情况");
                    if(works[1]==-1||works[1]==0){
                        getIndex2(itemss, works);
                    }
                }
            }
        }
        List<Items> items1 = itemss.subList(works[0]+1,works[1]);
        for(Items rr : items1){
            if(rr.getItemstring().equals("期望薪资:")){
                items1.get(items1.indexOf(rr)+1).setKey("expectedSalary");
                itemlines.add( items1.get(items1.indexOf(rr)+1));
            }
            if(rr.getItemstring().equals("工作类型:")){
                items1.get(items1.indexOf(rr)+1).setKey("jobNature");
                itemlines.add( items1.get(items1.indexOf(rr)+1));
            }
            if(rr.getItemstring().equals("自我评价:")){
                int[] er = getIndex(itemss,"自我评价:","工作经验");
                if(er[1]==-1||er[1]==0){
                    if(getIndexOne(itemss,"项目经验")==-1||getIndexOne(itemss,"项目经验")==0){
                        getMorInfo(itemss, er);
                    }
                }
                for(int i = er[0]+1;i<er[1]-1;i++){
                    itemss.get(i).setKey("evaluation");
                }
                itemlines.addAll( itemss.subList(er[0]+1,er[1]));
            }
            if(rr.getItemstring().contains("自我评价:")){
                rr.setKey("evaluation");
                rr.setItemstring(rr.getItemstring().split(":")[1]);
                itemlines.add(rr);
            }
        }
    }

    private static void getMorInfo(List<Items> itemss, int[] er) {
        if(er[1]==-1||er[1]==0){
            er[1] = getIndexOne(itemss,"教育经历");
            if(er[1]==-1||er[1]==0){
                er[1] = getIndexOne(itemss,"在校情况");
                if(er[1]==-1||er[1]==0){
                    er[1] = getIndexOne(itemss,"技能特长");
                    if(er[1]==-1||er[1]==0){
                        er[1] = getIndexOne(itemss,"培训经历");
                        if(er[1]==-1||er[1]==0){
                            er[1] = itemss.size()-1;
                        }
                    }
                }
            }
        }
    }

    private static void getProjecEx(List<Items> itemlines, List<Items> itemss) {
        int[] pro = getIndex(itemss,"项目经验","教育经历");
        if(pro[0]==-1||pro[0]==0){
            return;
        }
        if(pro[1]==-1||pro[1]==0){
            System.out.println("11111======index2"+pro[1]);
            pro[1]=getIndexOne(itemss,"在校情况");
            if( pro[1]==-1||pro[1]==0){
                getIndex2(itemss, pro);
            }
        }
        System.out.println("========================index2"+pro[1]);
        List<Items> projects = itemss.subList(pro[0]+1,pro[1]);
        for(Items items : projects){
            items.setKey("projectExperience");
        }
        itemlines.addAll(projects);
    }

    private static void getJobExp(List<Items> itemlines, List<Items> itemss) {
        int[] job = getIndex(itemss,"工作经验","项目经验");

        if(job[0]==-1||job[0]==0||job[1]==0||job[1]==-1){
            return;
        }
        getMorInfo(itemss, job);
        List<Items> jobs = itemss.subList(job[0]+1,job[1]);
        for(Items items : jobs){
            items.setKey("workExperience");
        }
        itemlines.addAll(jobs);
    }

    private static void getTrain(List<Items> itemlines, List<Items> itemss) {
        int pric = getIndexOne(itemss,"培训经历");
        if(pric==-1||pric==0){
            return;
        }
        System.out.println("Train"+pric);
        List<Items> prics = itemss.subList(pric+1,itemss.size());
        for(Items items : prics ){
            items.setKey("trainingExperience");
        }
        itemlines.addAll(prics);
    }

    private static void getSkills(List<Items> itemlines, List<Items> itemss) {
        int[] skill = getIndex(itemss,"技能特长","培训经历");
        if(skill[0]==-1||skill[0]==0){
            return;
        }
        if(skill[1]==-1){
            skill[1] = itemss.size()-1;
        }
        List<Items> skills = itemss.subList(skill[0]+1,skill[1]);
        System.out.println(skills.size());
        for(Items items : skills ){
            items.setKey("workSkills");
        }
        itemlines.addAll(skills);
    }

    private static void getEducation(List<Items> itemlines, List<Items> itemss) {
        int[] edu = getIndex(itemss,"教育经历","在校情况");

        System.out.println("deu0:"+edu[0]+"  edu1:"+edu[1]);
        if(edu[0]==-1||edu[0]==0){
            return;
        }
        if(edu[1]==-1||edu[1]==0){
            getIndex2(itemss, edu);
        }
        List<Items> education = itemss.subList(edu[0]+1,edu[1]);
        for(Items items : education ){
            items.setKey("educationExperience");
        }
        itemlines.addAll(education);
    }

    private static void getIndex2(List<Items> itemss, int[] edu) {
        edu[1] = getIndexOne(itemss,"技能特长");
        if(edu[1]==-1||edu[1]==0){
            edu[1] = getIndexOne(itemss,"培训经历");
            if(edu[1]==-1||edu[1]==0){
                edu[1] = itemss.size();
                System.out.println("===============56556565"+edu[1]);
            }
        }
    }

    public List<Items>  getMicResult(List<Items> itemlines, List<Items> itemss){

        boolean invoice = false;
        boolean insurance = false;
        for (Items items : itemss){
            if(items.getItemstring().contains("发票")
                    ||items.getItemstring().contains("支票")
                    ){
                invoice = true;
            }
            if(items.getItemstring().contains("保险")){
                insurance = true;
            }
        }

        if(insurance){
            for (Items items : itemss){
                items.setCategory("insurance");
            }
            //分支机构
            itemss.get(4).setKey(itemss.get(4).getItemstring().split("：")[0]);
            itemss.get(4).setItemstring(itemss.get(4).getItemstring().split("：")[1]);
            itemss.get(4).setWords(getWords(itemss.get(4).getWords()));
            itemlines.add(itemss.get(4));

            String start = "被保险人";
            //被保险人保险合同编号
            itemss.get(6).setKey(start+itemss.get(6).getItemstring());
            itemss.get(6).setItemstring(itemss.get(7).getItemstring());
            itemss.get(6).setWords(itemss.get(7).getWords());
            itemlines.add(itemss.get(6));

            //被保险人姓名
            itemss.get(11).setKey(start+"姓名");
            itemss.get(11).setItemstring(itemss.get(11).getItemstring().split("：")[1]);
            itemss.get(11).setWords(getWords(itemss.get(11).getWords()));
            itemlines.add(itemss.get(11));

            //被保险人性别
            itemss.get(9).setKey(start+"性别");
            itemss.get(9).setItemstring(itemss.get(9).getItemstring().split("：")[1]);
            itemss.get(9).setWords(getWords(itemss.get(9).getWords()));
            itemlines.add(itemss.get(9));
            //被保险人年龄
            itemss.get(10).setKey(start+"年龄");
            itemss.get(10).setItemstring(itemss.get(10).getItemstring().split("：")[1]);
            itemss.get(10).setWords(getWords(itemss.get(10).getWords()));
            itemlines.add(itemss.get(10));

            //被保险人联系地址
            itemss.get(15).setKey(start+itemss.get(15).getItemstring().split("：")[0]);
            itemss.get(15).setWords(getWords(itemss.get(15).getWords()));
            itemss.get(15).setItemstring(itemss.get(15).getItemstring().split("：")[1]);
            itemlines.add(itemss.get(15));

            //被保险人联系电话
            String[] person = itemss.get(8).getItemstring().split("：");
            Items itemsTel = new Items();
            itemsTel.setKey(start+person[0]);
            itemsTel.setItemcoord(itemss.get(8).getItemcoord());
            itemsTel.setPageNum(itemss.get(8).getPageNum());
            List<Words> telP = new ArrayList<>();
            List<Words> contP = itemss.get(8).getWords();
            for (Words words : contP){
                if(words.getCharacter().matches("^[0-9]*$")){
                    telP.add(words);
                }
            }
            itemsTel.setWords(telP);
            itemsTel.setItemstring(person[1].replaceAll("[^(0-9)\\/(0-9)\\/(0-9)]",""));
            itemsTel.setCategory("insurance");
            itemlines.add(itemsTel);

            Items itemJob = new Items();
            itemJob.setKey("被保险人职业");
            itemJob.setItemcoord(itemss.get(8).getItemcoord());
            itemJob.setPageNum(itemss.get(8).getPageNum());
            List<Words> jobs = itemss.get(8).getWords();
            int endTag = 0;
            for(Words words : jobs){
                if(words.getCharacter().equals("业")){
                    endTag = jobs.indexOf(words);
                }
            }

            System.out.println("-----------------"+endTag);
            List<Words> jobsEnd = jobs.subList(endTag+2,jobs.size());
            itemJob.setWords(jobsEnd);
            itemJob.setItemstring(person[2]);
            itemJob.setCategory("insurance");
            itemlines.add(itemJob);

            //申请人信息

            String applicant = "申请人";

            //申请人性别
            itemss.get(18).setKey(applicant+"性别");
            if(itemss.get(18).getItemstring().split("：").length>1){
                itemss.get(18).setItemstring(itemss.get(18).getItemstring().split("：")[1]);
                itemss.get(18).setWords(getWords(itemss.get(18).getWords()));
            }else{
                itemss.get(18).setItemstring("");
                itemss.get(18).setWords(null);
            }

            itemlines.add(itemss.get(18));
            //申请人年龄
            itemss.get(19).setKey(applicant+"年龄");
            if(itemss.get(19).getItemstring().split("：").length>1){
                itemss.get(19).setItemstring(itemss.get(19).getItemstring().split("：")[1]);
                itemss.get(19).setWords(getWords(itemss.get(19).getWords()));
            }else{
                itemss.get(19).setItemstring("");
                itemss.get(19).setWords(null);
            }
            itemlines.add(itemss.get(19));
            //申请人联系电话
            itemss.get(20).setKey(applicant+"联系电话");
            if(itemss.get(20).getItemstring().split("：").length>1){
                itemss.get(20).setItemstring(itemss.get(20).getItemstring().split("：")[1]);
                itemss.get(20).setWords(getWords(itemss.get(20).getWords()));
            }else{
                itemss.get(20).setItemstring("");
                itemss.get(20).setWords(null);
            }
            itemlines.add(itemss.get(20));
            //申请人姓名
            itemss.get(21).setKey(applicant+"姓名");
            if(itemss.get(21).getItemstring().split("：").length>1){
                itemss.get(21).setItemstring(itemss.get(21).getItemstring().split("：")[1]);
                itemss.get(21).setWords(getWords(itemss.get(21).getWords()));
            }else{
                itemss.get(21).setItemstring("");
                itemss.get(21).setWords(null);
            }

            itemlines.add(itemss.get(21));
            //申请人联系地址
            itemss.get(23).setKey(applicant+"联系地址");
            if(itemss.get(23).getItemstring().split("：").length>1){
                itemss.get(23).setItemstring(itemss.get(23).getItemstring().split("：")[1]);
                itemss.get(23).setWords(getWords(itemss.get(23).getWords()));
            }else{
                itemss.get(23).setItemstring("");
                itemss.get(23).setWords(null);
            }

            itemlines.add(itemss.get(23));
            //申请人与出险被保险人关系

            itemss.get(29).setKey("申请人与出险被保险人关系");
            if(itemss.get(29).getItemstring().split("：").length>1){
                itemss.get(29).setItemstring(itemss.get(29).getItemstring().split("：")[1]);
                List<Words> contat = getWords(itemss.get(29).getWords());
                int count2 = 0;
                for(Words words: contat){
                    if(words.getCharacter().equals("J")||words.getCharacter().equals("囝")){
                        count2 = contat.indexOf(words);
                    }
                }

                if(count2==0){
                    itemss.get(29).setWords(null);
                }else{
                    List<Words> contact1 = new ArrayList<>();
                    for(int i=count2;i<contat.size();i++){
                        if(contat.get(i).getCharacter().equals("囗")){
                            break;
                        }
                        contact1.add(contat.get(i));
                    }

                    itemss.get(29).setWords(contact1);
                }

            }else{
                itemss.get(29).setItemstring("");
                itemss.get(29).setWords(null);
            }

            itemlines.add(itemss.get(29));
            //索赔类型

            itemss.get(31).setKey("索赔类型");
            itemss.get(31).setItemstring(itemss.get(31).getItemstring().split("囗")[0]);
            List<Words> wordsLists = new ArrayList<>();
            wordsLists.addAll(itemss.get(31).getWords());
            wordsLists.addAll(itemss.get(32).getWords());
            wordsLists.addAll(itemss.get(33).getWords());
            wordsLists.addAll(itemss.get(34).getWords());
            wordsLists.addAll(itemss.get(35).getWords());

            int value = 0;
            for(Words words : wordsLists){
                if(words.getText().equals("J")){
                    value = wordsLists.indexOf(words);
                }
            }
            List<Words> wordsList1 = wordsLists.subList(value,wordsLists.size()-1);
            List<Words> wordsList2 = new ArrayList<>();
            for(Words words : wordsList1){
                if(words.getText().equals("囗")){
                    break;
                }
                wordsList2.add(words);
            }
            itemss.get(31).setWords(wordsList2);
            itemlines.add(itemss.get(31));
            //如为投资类产品（万能/投资连结等）
            itemss.get(38).setKey("如为投资类产品（万能/投资连结等）");
            List<Words>wordsList4 = itemss.get(38).getWords();
            List<Words> wordsPro = new ArrayList<>();
            for(Words words : wordsList4){
                if(words.getText().equals("口")){
                    words.setCharacter("");
                    wordsPro.add(words);
                }
                if(words.getText().equals("J")){
                    words.setCharacter("是");
                    wordsPro.add(words);
                }
            }
            itemss.get(38).setWords(wordsPro);
            itemlines.add(itemss.get(38));

            //付款方式

            List<Words> wordsList5  = itemss.get(41).getWords();
            List<Words> wordsList6  = itemss.get(43).getWords();
            for(Words words : wordsList5){
                if(words.getText().equals("J")){
                    itemss.get(41).setKey("付款方式");
                    itemlines.add(itemss.get(41));
                }
            }
            for(Words words : wordsList6){
                if(words.getText().equals("J")){
                    itemss.get(43).setKey("付款方式");
                    itemlines.add(itemss.get(43));
                }
            }

            //事故发生时间

            itemss.get(46).setKey("事故发生时间");
            itemss.get(46).setWords(null);
            itemlines.add(itemss.get(46));
            //事故发生地点

            itemss.get(52).setKey("事故发生地点");
            itemlines.add(itemss.get(52));
            //事故及简要经过
            itemss.get(50).setKey("原因及简要经过");
            itemlines.add(itemss.get(50));
            //诊断结果

            Items itemsResu = new Items();
            itemsResu.setKey("诊断结果");
            itemsResu.setPageNum(itemss.get(50).getPageNum());
            itemsResu.setItemstring(itemss.get(50).getItemstring());
            List<Words> wordsList9 = itemss.get(50).getWords();
            List<Words> wordsList8 = new ArrayList<>();
            for(int i=wordsList9.size()-4;i<wordsList9.size();i++){
                wordsList8.add(wordsList9.get(i));
            }
            itemsResu.setWords(wordsList8);
            itemsResu.setItemcoord(itemss.get(50).getItemcoord());
            itemsResu.setCategory("insurance");
            itemlines.add(itemsResu);
            //索赔金额
            itemss.get(51).setKey("索赔金额");
            itemlines.add(itemss.get(51));

            //事故是否经公安、交警、劳动、卫生部门或其他部门处理
            itemss.get(58).setKey("事故是否经公安、交警、劳动、卫生部门或其他部门处理");

            if(itemss.get(58).getItemstring().contains("J")){
                itemss.get(58).setItemstring(itemss.get(58).getItemstring().split("：")[1]);
                itemss.get(58).setWords(getWords(itemss.get(58).getWords()));
            }
            if(itemss.get(59).getItemstring().contains("J")||itemss.get(59).getItemstring().contains("囝")){
                itemss.get(58).setItemstring(itemss.get(59).getItemstring());
                itemss.get(58).setWords(itemss.get(59).getWords());
                itemss.get(58).setItemcoord(itemss.get(59).getItemcoord());
            }
            itemlines.add(itemss.get(58));

            //是否向其它保险公司投保
            List<Words> wordsList33 = itemss.get(61).getWords();
            int value1 = 0;
            for(Words words : wordsList33){
                if(words.getText().equals("）")){
                    value1 = wordsList33.indexOf(words);
                }
            }
            List<Words> wordsList34 = wordsList33.subList(value1,wordsList33.size());
            itemss.get(61).setKey("是否向其它保险公司投保");
            itemss.get(61).setWords(wordsList34);
            itemlines.add(itemss.get(61));
            //客户备注
            itemss.get(77).setKey("客户备注");
            itemss.get(77).setWords(null);
            itemlines.add(itemss.get(77));

            //保险营销员/见证人签名

            itemss.get(78).setKey("保险营销员/见证人签名");
            itemss.get(78).setItemstring(itemss.get(78).getItemstring().split("：")[1]);
            itemss.get(78).setWords(getWords(itemss.get(78).getWords()));
            itemlines.add(itemss.get(78));

            //被保险人/申请人签名

            itemss.get(79).setKey("被保险人/申请人签名");
            itemss.get(79).setItemstring(itemss.get(79).getItemstring().split("：")[1]);
            itemss.get(79).setWords(getWords(itemss.get(79).getWords()));
            itemlines.add(itemss.get(79));

            //日期

            itemss.get(80).setKey("日期");
            itemss.get(80).setItemstring(itemss.get(80).getItemstring().split("：")[1]);
            itemss.get(80).setWords(getWords(itemss.get(80).getWords()));
            itemlines.add(itemss.get(80));

            //保险营销员姓名

            List<Words> wordsList111 = itemss.get(82).getWords();
            List<Words> name = new ArrayList<>();
            int nameValue = 0;
            int numverVaule = 0;
            for (Words words : wordsList111){
                if(words.getCharacter().equals("名")){
                    nameValue = wordsList111.indexOf(words);
                }
                if(words.getCharacter().equals("编")){
                    numverVaule = wordsList111.indexOf(words);
                }
            }
            List<Words> nameList = wordsList111.subList(nameValue+1,numverVaule);
            List<Words> nameEndList = new ArrayList<>();
            for(Words words : nameList){
                if(!words.getCharacter().equals("一")){
                    nameEndList.add(words);
                }
            }
            itemss.get(82).setKey("保险营销员姓名");
            itemss.get(82).setWords(nameEndList);
            itemlines.add(itemss.get(82));

            //编号

            Items number = new Items();
            number.setItemstring("编号");
            number.setKey("编号");
            number.setItemcoord(itemss.get(82).getItemcoord());
            number.setPageNum(itemss.get(82).getPageNum());
            List<Words> numWord = new ArrayList<>();
            for (Words words : wordsList111){
                if(words.getCharacter().matches("^[0-9]*$")){
                    numWord.add(words);
                }
            }
            number.setWords(numWord);
            number.setCategory("insurance");
            itemlines.add(number);

            //营销服务部

            itemss.get(85).setKey("营销服务部");
            itemlines.add(itemss.get(85));
            //组别
            itemss.get(87).setKey("组别");
            itemlines.add(itemss.get(87));
            //联系电话
            itemss.get(84).setKey("联系电话");
            itemss.get(84).setItemstring(itemss.get(84).getItemstring().replaceAll("^[0-9]*$",""));
            List<Words> tel = itemss.get(84).getWords();
            List<Words> telEnd = new ArrayList<>();
            for(Words words : tel){
                if(words.getCharacter().matches("^[0-9]*$")){
                    telEnd.add(words);
                }
            }
            itemss.get(84).setWords(telEnd);
            itemlines.add(itemss.get(84));

            //保险合同编号
            itemss.get(88).setKey("保险合同编号");
            itemss.get(88).setItemstring(itemss.get(88).getItemstring().replaceAll("^[a-zA-Z]+[0-9]*$",""));
            int tag = 0;
            int per = 0;
            List<Words> contractNumber = itemss.get(88).getWords();
            for(Words words : contractNumber){
                if(words.getCharacter().equals("：")){
                    tag = contractNumber.indexOf(words);
                }
                if(words.getCharacter().equals("被")){
                    per = contractNumber.indexOf(words);
                }
            }
            List<Words> contracNumEnd = contractNumber.subList(tag,per);

            itemss.get(88).setWords(contracNumEnd);

            //被保险人姓名

            itemss.get(90).setKey("被保险人姓名");
            itemlines.add(itemss.get(90));

            return itemlines;
        }

        if(invoice){
            for (Items items : itemss){
                items.setCategory("invoice");
            }
            return itemss;
        }

            boolean page1 = false;
            for (Items items : itemss){
                if(items.getItemstring().equals("最近工作")){
                    page1 = true;
                }
            }
            if(page1){

                if(invoice){
                    for (Items items : itemss){
                        items.setCategory("resume");
                    }
                }

         getBaseInfo(itemlines,itemss);

            }
        System.out.println("+++++++++++address");
        //婚姻状态
        //所在城市
        //地址
        getPerInfo(itemlines, itemss);

        //到岗时间

        //期望月薪
        //工作性质
        //自我评价

        System.out.println("++++++++++job");
        getJobInten(itemlines, itemss);

        //项目经历
        getProjecEx(itemlines, itemss);
        //工作经历
        getJobExp(itemlines, itemss);

        //教育经历
        getEducation(itemlines, itemss);

        //语言
        //技能
        getSkills(itemlines, itemss);
        //培训经历
        getTrain(itemlines, itemss);
        return itemlines;
    }


    public String getStandarData(List<Items> items){

        System.out.println(items.size());
        JSONArray json = new JSONArray();
        JSONObject jo = new JSONObject();
        //StringBuffer sefeEv = new StringBuffer();
        StringBuffer workExpe = new StringBuffer();
        StringBuffer projectExp = new StringBuffer();
        StringBuffer educationExp = new StringBuffer();
        StringBuffer trainingEx = new StringBuffer();
        StringBuffer evalu = new StringBuffer();
        for(Items items1 : items){

            if(items1.getKey()==null){
                continue;
            }
            if(items1.getKey().equals("evaluation")){
                evalu.append(items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("name")){
                jo.put("name",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("cellphone")){
                jo.put("cellphone",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("avatar")){
                jo.put("avatar",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("gender")){
                jo.put("gender",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("workYears")){
                jo.put("workYears",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("workStartTime")){
                jo.put("workStartTime",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("education")){
                jo.put("education",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("major")){
                jo.put("major",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("graduateSchool")){
                jo.put("graduateSchool",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("maritalStatus")){
                jo.put("maritalStatus",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("email")){
                jo.put("email",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("birthday")){
                jo.put("birthday",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("city")){
                jo.put("city",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("address")){
                jo.put("address",items1.getItemstring());
                continue;
            }
//            if(items1.getKey().equals("selfEvaluation")){
//                jo.put("selfEvaluation",sefeEv.append(items1.getItemstring()));
//            }
            if(items1.getKey().equals("profession")){
                jo.put("profession",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("company")){
                jo.put("company",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("position")){
                jo.put("position",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("jobStatus")){
                jo.put("jobStatus",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("dutyTime")){
                jo.put("dutyTime",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("jobNature")){
                jo.put("jobNature",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("targetWorkPlace")){
                jo.put("targetWorkPlace",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("expectedSalary")){
                jo.put("expectedSalary",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("workExperience")){
                System.out.println("workExperience"+items1.getItemstring());
                workExpe.append(items1.getItemstring());

                continue;
            }

            if(items1.getKey().equals("projectExperience")){


                projectExp.append(items1.getItemstring());

                continue;
            }

            if(items1.getKey().equals("educationExperience")){
                educationExp.append(items1.getItemstring());
                continue;
            }

            if(items1.getKey().equals("trainingExperience")){
                trainingEx.append(items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("languageAbility")){
                jo.put("languageAbility",items1.getItemstring());
                continue;
            }
            if(items1.getKey().equals("workSkills")){
                jo.put("workSkills",items1.getItemstring());
                continue;
            }
        }

        jo.put("evaluation",evalu.toString());
        jo.put("workExperience",workExpe.toString());
        jo.put("projectExperience",projectExp.toString());
        jo.put("educationExperience",educationExp.toString());
        jo.put("trainingExperience",trainingEx.toString());
        //Start fixing by Lin Yuan Yuan
       // System.out.println("jo: " + jo.toString());
        json.add(jo);
       // System.out.println("json2: " + json.toString());
        //End fixing by Lin Yuan Yuan

        System.out.println("++++++++++++++++"+json.toString());
        return json.toString();
    }


    public List<Words> getWords(List<Words> wordsList){
        List<Words> wordsList1 = new ArrayList<>();
        int count = 0;
        for(Words words : wordsList){
            if(words.getCharacter().equals("：")){
                count = wordsList.indexOf(words);
            }
        }

        for(int i=count+1;i<wordsList.size();i++){
            wordsList1.add(wordsList.get(i));
        }
        return wordsList1;
    }
}
