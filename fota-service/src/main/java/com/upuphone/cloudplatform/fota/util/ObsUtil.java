package com.upuphone.cloudplatform.fota.util;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.*;
import com.upuphone.cloudplatform.common.exception.BusinessException;
import com.upuphone.cloudplatform.fota.FotaErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ObsUtil {


    public static ObsClient getObsClient(String ak, String sk, String endPoint) {
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        return obsClient;
    }

    public static String temporarySignatureUrl(String ak, String sk, String endPoint, String bucketName, String objectName, long expireSeconds) {
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);

        request.setBucketName(bucketName);
        request.setObjectKey(objectName);
        
        ObsClient obsClient = getObsClient(ak,sk,endPoint);

        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        return response.getSignedUrl();
    }

    /**
     * 下载到本地文件
     * @param ak    obs密钥
     * @param sk    obs密钥
     * @param endPoint  obs地址
     * @param bucketName    桶名
     * @param objectName    对象名
     * @param file  文件对象
     * @throws IOException
     */
    public static void downloadByStream(String ak, String sk, String endPoint,
                                        String bucketName, String objectName, File file) throws IOException {
        ObsClient obsClient = getObsClient(ak,sk,endPoint);
        ObsObject obsObject = obsClient.getObject(bucketName,objectName);
        InputStream is = obsObject.getObjectContent();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int ch =0;
        while((ch = is.read(buf)) != -1){
            fos.write(buf,0,ch);
        }
        fos.close();
        is.close();
    }

    /**
     *  通过输入流上传
     * @param ak    obs密钥
     * @param sk    obs密钥
     * @param endPoint  obs地址
     * @param bucketName    桶名
     * @param objectName    对象名
     * @param inputStream   输入流
     * @return
     */
    public PutObjectResult putFilebyInstream(String ak, String sk, String endPoint,
                                             String bucketName, String objectName, InputStream inputStream) {
        ObsClient obsClient = getObsClient(ak,sk,endPoint);
        PutObjectResult result = obsClient.putObject(bucketName, objectName, inputStream);
        try {
            obsClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 上传本地文件
     * @param ak
     * @param sk
     * @param endPoint
     * @param bucketName
     * @param objectName
     * @param file  本地文件
     * @return
     */
    public PutObjectResult putLocalFile(String ak, String sk, String endPoint,
                                   String bucketName, String objectName, File file) {
        ObsClient obsClient = getObsClient(ak,sk,endPoint);
        PutObjectResult result = obsClient.putObject(bucketName, objectName, file);
        try {
            obsClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void copyObject(String ak, String sk, String endPoint,
                           String sourceBucket, String sourceObject, String destBucket, String destObject) {
        ObsClient obsClient = getObsClient(ak, sk, endPoint);
        try {
            ObjectMetadata metadata = obsClient.getObjectMetadata(destBucket, destObject);
        } catch(ObsException e) {
            if (e.getResponseCode() == 404) {
                log.info("公开桶中不存在对象{}", destObject);
                try{
                    CopyObjectResult result = obsClient.copyObject(sourceBucket, sourceObject, destBucket, destObject);
                } catch (ObsException copyException) {
                    log.error("obs拷贝文件{}错误", sourceObject);
                    throw new BusinessException(FotaErrorCode.COPY_OBJECT_ERROR);
                }
            }
        }
    }

}
