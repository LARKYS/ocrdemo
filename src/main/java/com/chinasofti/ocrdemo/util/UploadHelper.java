package com.chinasofti.ocrdemo.util;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author xdwang
 *
 * @create 2012-11-19 下午6:24:03
 *
 * @email:xdwangiflytek@gmail.com
 *
 * @description 上传帮助类
 *
 */
public class UploadHelper {

    /**
     * @descrption 根据HttpServletRequest对象获取MultipartFile集合
     * @author xdwang
     * @create 2012-11-19下午5:11:41
     * @param request
     * @param maxLength
     *            文件最大限制
     * @param allowExtName
     *            不允许上传的文件扩展名
     * @return MultipartFile集合
     */
    public static List<MultipartFile> getFileSet(HttpServletRequest request,
                                                 long maxLength, String[] allowExtName) {
        MultipartHttpServletRequest multipartRequest = null;
        try {
            multipartRequest = (MultipartHttpServletRequest) request;

        } catch (Exception e) {
            return new LinkedList<MultipartFile>();
        }

        List<MultipartFile> files = new LinkedList<MultipartFile>();
        files = multipartRequest.getFiles("attach");
        // 移除不符合条件的
        for (int i = 0; i < files.size(); i++) {
            if (!validateFile(files.get(i), maxLength, allowExtName)) {
                files.remove(files.get(i));
                if (files.size() == 0) {
                    return files;
                }
            }
        }
        return files;
    }

    /**
     * @descrption 保存文件
     * @author xdwang
     * @create 2012-11-19下午4:17:36
     * @param file
     *            MultipartFile对象
     * @param path
     *            保存路径，如“D:\\File\\”
     * @return 保存的全路径 如“D:\\File\\2345678.txt”
     * @throws Exception
     *             文件保存失败
     */
    public static String uploadFile(MultipartFile file, String path)
            throws Exception {

        String filename = file.getOriginalFilename();
        String extName = filename.substring(filename.lastIndexOf("."))
                .toLowerCase();
        String lastFileName = UUID.randomUUID().toString() + extName;
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        File temp = new File(path);
        if (!temp.isDirectory()) {
            temp.mkdir();
        }
        // 图片存储的全路径
        String fileFullPath = path + lastFileName;
        FileCopyUtils.copy(file.getBytes(), new File(fileFullPath));
        return fileFullPath;
    }

    /**
     * @descrption 验证文件格式，这里主要验证后缀名
     * @author xdwang
     * @create 2012-11-19下午4:08:12
     * @param file
     *            MultipartFile对象
     * @param maxLength
     *            文件最大限制
     * @param allowExtName
     *            不允许上传的文件扩展名
     * @return 文件格式是否合法
     */
        private static boolean validateFile(MultipartFile file, long maxLength,
                                            String[] allowExtName) {
            if (file.getSize() < 0 || file.getSize() > maxLength)
                return false;
            String filename = file.getOriginalFilename();

            // 处理不选择文件点击上传时，也会有MultipartFile对象，在此进行过滤
            if (filename == "") {
                return false;
            }
            String extName = filename.substring(filename.lastIndexOf("."))
                    .toLowerCase();
            if (allowExtName == null || allowExtName.length == 0
                    || Arrays.binarySearch(allowExtName, extName) != -1) {
                return true;
            } else {
                return false;
            }
        }

        public static boolean deleteDir(String path){
            File file = new File(path);
            if(!file.exists()){//判断是否待删除目录是否存在
                System.err.println("The dir are not exists!");
                return false;
            }

            String[] content = file.list();//取得当前目录下所有文件和文件夹
            for(String name : content){
                File temp = new File(path, name);
                if(temp.isDirectory()){//判断是否是目录
                    deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
                    temp.delete();//删除空目录
                }else{
                    if(!temp.delete()){//直接删除文件
                        System.err.println("Failed to delete " + name);
                    }
                }
            }
            return true;
        }
}