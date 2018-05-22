package com.chinasofti.ocrdemo.controller;


import com.chinasofti.ocrdemo.service.OcrReconService;
import com.chinasofti.ocrdemo.util.CsharpUtils;
import com.chinasofti.ocrdemo.util.Json2String;
import com.chinasofti.ocrdemo.util.MicUitls;
import com.chinasofti.ocrdemo.util.UploadHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@CrossOrigin
@Controller
public class MicOcrController {

    Logger logger = LoggerFactory.getLogger(MicOcrController.class);
    @ResponseBody
    @RequestMapping(value = "/ocr/mic/picture/recognition", method = RequestMethod.POST)
    public String ocrRecon(MultipartHttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        OcrReconService ocrReconService = null;
        List<String> filePath = new ArrayList<String>();
        Collection<MultipartFile> multipartFiles = request.getFileMap().values();
        String path = "C:\\temp" + File.separator;
        System.out.println("====start upload=="+multipartFiles.size());
        if (multipartFiles.size() == 0) {
            // TODO 给出提示,不允许没选择文件点击上传
            logger.info("没有上传文件");
            return null;
        }
        for (MultipartFile multipartFile : multipartFiles) {
            try {
                filePath.add(UploadHelper.uploadFile(multipartFile, path));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 拿到的imgPath就是图片的相对于contextPath的存储路径了
        }
        //2 开始识别并返回结果
        ocrReconService = new OcrReconService();
        System.out.println("======file1 path:"+filePath.get(0));
        List<String> result = ocrReconService.micReconAll(filePath);
        //3 识别完成后删除临时文件
        UploadHelper.deleteDir("C://Temp");
        Json2String json2String = new Json2String();
        return json2String.getMicString(result);

    }

    @ResponseBody
    @RequestMapping(value="/ocr/resume/recognition", method = RequestMethod.POST)
    public String ocrResume(MultipartHttpServletRequest request,
                            HttpServletResponse response) {
        OcrReconService ocrReconService = null;
        List<String> filePath = new ArrayList<String>();
        //1 上传文件至服务器临时目录
        Iterator iterator = request.getFileNames();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        Collection<MultipartFile> multipartFiles = request.getFileMap().values();
        String path = "C:\\temp" + File.separator;
        System.out.println("====start upload=="+multipartFiles.size());
        if (multipartFiles.size() == 0) {
            // TODO 给出提示,不允许没选择文件点击上传
            logger.info("上传文件失败");
            return null;
        }
        List<MultipartFile> files = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            try {
                files.add(multipartFile);
                filePath.add(UploadHelper.uploadFile(multipartFile, path));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 拿到的imgPath就是图片的相对于contextPath的存储路径了
        }

        //如果是word pdf 和 html文件调用c#接口
        if(files.get(0).getOriginalFilename().split("\\.")[1].equals("doc")
                ||files.get(0).getOriginalFilename().split("\\.")[1].equals("pdf")
                ||files.get(0).getOriginalFilename().split("\\.")[1].equals("html")){

            String endResult = CsharpUtils.converResult2Joson("C:\\temp");
            logger.info(endResult);
            UploadHelper.deleteDir("C://Temp");
            return endResult;

        }else{
            //2 开始识别图片并返回结果

            for(String ss : filePath){
                System.out.println("======file1 path:"+ss);
            }

            System.out.println(ocrReconService);
            ocrReconService = new OcrReconService();
            List<String> result = ocrReconService.micReconAll(filePath);
            //3 识别完成后删除临时文件
            UploadHelper.deleteDir("C://Temp");
            MicUitls micUitls = new MicUitls();
            String data = micUitls.getMicResumeData(result);
            return data;
        }

    }
}
