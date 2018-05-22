package com.chinasofti.ocrdemo.controller;

import com.chinasofti.ocrdemo.service.OcrReconService;
import com.chinasofti.ocrdemo.util.CsharpUtils;
import com.chinasofti.ocrdemo.util.Json2String;
import com.chinasofti.ocrdemo.util.SaveHelper;
import com.chinasofti.ocrdemo.util.UploadHelper;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.hibernate.validator.internal.engine.groups.ValidationOrderGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@CrossOrigin
@Controller
public class TenOcrController {

    @ResponseBody
    @RequestMapping(value="/ocr/picture/recognition", method = RequestMethod.POST)
    public String ocrRecon(MultipartHttpServletRequest request,
                             HttpServletResponse response) {
        OcrReconService ocrReconService = null;
        List<String> filePath = new ArrayList<String>();
        //1 上传文件至服务器临时目录
       // System.out.println("====start upload==");
       //  List<MultipartFile> multipartFiles = UploadHelper.getFileSet(request, 1024 * 20, null);
        Collection<MultipartFile> multipartFiles = request.getFileMap().values();
        String path = "C:\\temp" + File.separator;
        System.out.println("====start upload=="+multipartFiles.size());
        if (multipartFiles.size() == 0) {
            // TODO 给出提示,不允许没选择文件点击上传
            System.out.println("没有上传文件");
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
        List<String> result = ocrReconService.reconAll(filePath);
        //3 识别完成后删除临时文件
        UploadHelper.deleteDir("C://Temp");
        Json2String json2String = new Json2String();
        return json2String.toString(result);

        }

        @ResponseBody
        @RequestMapping(value="/ocr/picture/save", method = RequestMethod.POST)
        public boolean save(HttpServletRequest request,
                            HttpServletResponse response){
            ServletInputStream is;
            String str="";
            try {
                is = request.getInputStream();
                int nRead = 1;
                int nTotalRead = 0;
                byte[] bytes = new byte[10240];
                while (nRead > 0) {
                    nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);
                    if (nRead > 0)
                        nTotalRead = nTotalRead + nRead;
                }
                str = new String(bytes, 0, nTotalRead, "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            SaveHelper saveHelper = new SaveHelper();
            return saveHelper.save(str);

        }


    }
