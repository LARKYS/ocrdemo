package com.chinasofti.ocrdemo.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.chinasofti.ocrdemo.bean.*;
import net.sf.json.JSONArray;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class HumanJson2JavaBeanConverter {
	
	/*
	 * 将Json String的内容存储到HumanBean对象，并最终返回该HumanBean对象
	 */
	public static HumanBean getJavaBeanObject(String jsonStr) {
		//将JsonStr存到HumanInfo对象
		HumanInfo humanInfo = JSON.parseObject  (jsonStr, HumanInfo.class);
		
		/*
		 * 创建HumanBean对象，并用set方法给HumanBean对象赋值
		 */
		HumanBean humanBean = new HumanBean();
		
		//姓名
		humanBean.setName(humanInfo.getFirstName()+humanInfo.getLastName());
		//电话
		humanBean.setCellphone(humanInfo.getPhoneNumbers());
		//头像
		//humanBean.setAvatar(avatar);
		//性别
		humanBean.setGender(humanInfo.getGender());
		//工作年限
		//humanBean.setWorkYears(workYears);
		//开始工作时间
		//humanBean.setWorkStartTime(workStartTime);
		
		//学历
		List<Educations> educations = humanInfo.getEducations();
		String educationStr = "";
		for(Educations education : educations) {
			educationStr += "[" + education.getStartDate() + education.getSchool() + education.getCourse() + education.getEndDate() + "]"; 
		}
		//educationStr = educationStr.substring(0, educationStr.length()-1);
		humanBean.setEducation(educationStr);
		
		//专业
		//humanBean.setMajor(major);
		//毕业学校
		//humanBean.setGraduateSchool(graduateSchool);
		//婚姻状态
		//humanBean.setMaritalStatus(maritalStatus);
		//邮箱
		humanBean.setEmail(humanInfo.getEmailAddress());
		//生日
		humanBean.setBirthday(humanInfo.getAge());
		//所在城市
		//humanBean.setCity(humanInfo.getLocation());
		//地址
		humanBean.setAddress(humanInfo.getLocation());
		//自我评价
		humanBean.setSelfEvaluation(humanInfo.getSummaryDescription());
		//行业
		humanBean.setProfession(stringList2String(humanInfo.getSkills()));
		//公司名称
		//humanBean.setCompany(company);

		//公司职位
		List<Positions> positions = humanInfo.getPositions();
		String positionExperienceStr = "";
		for(Positions position : positions) {
			positionExperienceStr += "[" + position.getStartDate() + position.getTitle() + position.getEndDate() + position.getCompany();
			positionExperienceStr += stringList2String(position.getSummary());
			positionExperienceStr += "]";
		}
		humanBean.setPosition(positionExperienceStr);

		//求职状态（在职、找工作）
		//humanBean.setJobStatus(jobStatus);
		//到岗时间
		//humanBean.setDutyTime(dutyTime);
		//工作性质（全职、兼职）
		//humanBean.setJobNature(jobNature);
		//目标工作地点（可以多个）
		//humanBean.setTargetWorkPlace(targetWorkPlace);
		//期望月薪
		//humanBean.setExpectedSalary(expectedSalary);
		//工作经历
		//humanBean.setWorkExperience(workExperience);
		
		//项目经历
		List<Projects> projects = humanInfo.getProjects();
		String projectExperienceStr = "";
		for(Projects project : projects) {
			projectExperienceStr += "[" + project.getStartDate() + project.getTitle() + project.getEndDate() + project.getCampanyName();			
			projectExperienceStr += stringList2String(project.getSummary());
			projectExperienceStr += "]";
			//+ project.getSummary().toString() + "]"; 
		}
		humanBean.setProjectExperience(projectExperienceStr);
		
		//教育经历
		humanBean.setEducationExperience(educationStr);
		//培训经历
		//humanBean.setTrainingExperience(trainingExperience);
		//语言
		humanBean.setLanguageAbility(humanInfo.getLanguages());
		//技能
		humanBean.setWorkSkills(stringList2String(humanInfo.getSkills()));
		//自我评价
		humanBean.setEvaluation(humanInfo.getSummaryDescription());
		
		return humanBean;
		
	}
	
	private static String stringList2String(List<String> strList) {
		String finalStr = "";
		for(String str : strList) {
			finalStr += "[" + str + "]";
		}
		return finalStr;
		
	}
	public static void main(String args[]) {
		String str="{\n" +
				"\t\"first-name\": \"张\",\n" +
				"\t\"last-name\": \"三\",\n" +
				"\t\"gender\": \"男\",\n" +
				"\t\"email-address\": \"que_dong_wang@163.com\",\n" +
				"\t\"phone-numbers\": \"15691876136\",\n" +
				"\t\"languages\": null,\n" +
				"\t\"summary-description\": null,\n" +
				"\t\"age\": \"31岁(19860612)\",\n" +
				"\t\"skills\": [\"技能/语言\",\n" +
				"\t\"一般\",\n" +
				"\t\"证书\",\n" +
				"\t\"2006/6\",\n" +
				"\t\"大学英语四级 （467）\",\n" +
				"\t\"培训经历\",\n" +
				"\t\"2011/1--2012/1\",\n" +
				"\t\"代表\",\n" +
				"\t\"培训机构：\",\n" +
				"\t\"学校\",\n" +
				"\t\"培训地点：\",\n" +
				"\t\"西安\",\n" +
				"\t\"培训描述：\",\n" +
				"\t\"参加培训\"],\n" +
				"\t\"location\": \"现居住西安-高新技术产业开发区\",\n" +
				"\t\"positions\": [{\n" +
				"\t\t\"start-date\": \"2017/1\",\n" +
				"\t\t\"title\": \"Java开发工程师|开发\",\n" +
				"\t\t\"end-date\": \"至今\",\n" +
				"\t\t\"company\": null,\n" +
				"\t\t\"summary\": [\"需求调研，分析并设计；参加了广州电视大学门户系统的开发，对jetspeed，liferay有了更深的认识同时也学会了Linux、weblogic的一些简单的运用，也通过odi对相关数据进行抽取同时也了解bi的一些相关知识；参加网络考试系统的开发，\",\n" +
				"\t\t\"2015/12-2017/8\",\n" +
				"\t\t\"某某公司?[1年8个月]\",\n" +
				"\t\t\"计算机软件|民营公司\",\n" +
				"\t\t\"工作描述：\",\n" +
				"\t\t\"主要负责参与安卓半原生app软件的开发,以及负责微信服务号、企业号的开发，供银行客户使用和公司内部员工使用。\"]\n" +
				"\t}],\n" +
				"\t\"projects\": [{\n" +
				"\t\t\"start-date\": \"2016/1\",\n" +
				"\t\t\"title\": \"微信\",\n" +
				"\t\t\"end-date\": \"至今\",\n" +
				"\t\t\"campany-name\": \"西安某某\",\n" +
				"\t\t\"summary\": [\"项目描述：\",\n" +
				"\t\t\"室内定位系统主要为服务于商场定位。服务于商场内顾客与顾客之间的位置导航以及顾客与商铺之间的位置导航。\",\n" +
				"\t\t\"责任描述：\",\n" +
				"\t\t\"主要该项目中负责用户管理系统、数据分析服务系统。其中包括需求设计、数据库设计、框架设计、工作分解、代码开发。\"]\n" +
				"\t}],\n" +
				"\t\"social-profiles\": [],\n" +
				"\t\"educations\": [{\n" +
				"\t\t\"start-date\": \"2008/9\",\n" +
				"\t\t\"school\": \"西安交通大学城市学院\",\n" +
				"\t\t\"course\": \"本科|哲学（含伦理学）\",\n" +
				"\t\t\"end-date\": \"2012/7\"\n" +
				"\t},\n" +
				"\t{\n" +
				"\t\t\"start-date\": \"2007/1\",\n" +
				"\t\t\"school\": null,\n" +
				"\t\t\"course\": null,\n" +
				"\t\t\"end-date\": \"2008/1\"\n" +
				"\t}],\n" +
				"\t\"courses\": [],\n" +
				"\t\"awards\": []\n" +
				"}";
//		Document document = null;
//		try {
//			document = DocumentHelper.parseText(str);
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//		Element employees=document.getRootElement();
//		List<Element> nodes = employees.elements("string");
//		Element st=nodes.get(0);
//		String ss=st.getText();
//		JSONArray json = JSONArray.fromObject(str );
//       String SS= json.get(0).toString();

		HumanBean hh=HumanJson2JavaBeanConverter.getJavaBeanObject(str);
	}
}
