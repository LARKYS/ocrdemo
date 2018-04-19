package com.chinasofti.ocrdemo.service;

import com.qcloud.image.ImageClient;
import com.qcloud.image.request.GeneralOcrRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class OcrReconService {

    public static final String subscriptionKey = "f240f0b770a9432f96ca9dcbe3e44f48";
    public static final String uriBase = "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/ocr";

    public static final String appId = "1251697081";
    String secretId = "AKID7EAyd7ZaaP2UlqsqiOtCKI5h9PUNIcxB";
    String secretKey = "DF5kDtSr7HF2e9VhjgXCl83tY1JTUJsN";
    String bucketName = "test1";
    public  String recon(String filePath){
        ImageClient imageClient = new ImageClient(appId, secretId, secretKey);
        GeneralOcrRequest request = new GeneralOcrRequest(bucketName, new File(filePath));
        String ret = imageClient.generalOcr(request);
        System.out.println(ret);
        imageClient.shutdown();
        return ret;
    }
    public List<String> reconAll(List<String> filePath){
       List<String> result = new ArrayList<>();
        for(String path :filePath ){
            result.add(recon(path));
        }
        return result;
    }

    public String micRecon(String urlPath){
        HttpClient httpclient = new DefaultHttpClient();
        String jsonResult = "";
        try
        {
            URIBuilder builder = new URIBuilder(uriBase);

            // Request parameters. All of them are optional.
//            builder.setParameter("visualFeatures", "Categories,Description,Color");
//            builder.setParameter("language", "zh-Hans");

            // Prepare the URI for the REST API call.
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            // Request headers.
//            request.setHeader("Content-Type", "multipart/form-data");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

            // Request body.

            //  StringEntity reqEntity = new StringEntity("{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/1/12/Broadway_and_Times_Square_by_night.jpg\"}");
            FileBody body = new FileBody(new File(urlPath));
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", body).build();

            request.setEntity(reqEntity);
            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null)
            {
                // Format and display the JSON response.
                String jsonString = EntityUtils.toString(entity);
                System.out.println("==========json:"+jsonString);
                JSONObject json = new JSONObject(jsonString);
                jsonResult = json.toString();
//                System.out.println("REST Response:\n");
//                System.out.println(json.toString(2));
            }
        }
        catch (Exception e)
        {
            // Display error message.
            System.out.println(e.getMessage());
        }
        return jsonResult;
    }
    public List<String> micReconAll(List<String> urlPath){
        List<String> result = new ArrayList<>();
        for(String path :urlPath ){
            result.add(micRecon(path));
        }
        return result;
    }
}
