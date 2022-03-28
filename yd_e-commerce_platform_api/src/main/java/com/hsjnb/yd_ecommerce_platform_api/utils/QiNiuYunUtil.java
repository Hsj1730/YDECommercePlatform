package com.hsjnb.yd_ecommerce_platform_api.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.*;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 *
 * @author : Hsj1730
 * @version : 1.0
 * @date : Created in 2022/03/20 21:17
 * @description : 七牛云对象存储工具类
 */

@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "hsjnb.qiniuyun")
public class QiNiuYunUtil {

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String domain;

    private Boolean useHttps;

    private Long expireInSeconds;

    /**
     * 文件上传
     * @param multipartFile multipartFile
     * @return 文件地址
     */
    public String upload(MultipartFile multipartFile) {
        Auth auth = Auth.create(accessKey, secretKey);
        // 指定网络区域 华东
        Configuration configuration = new Configuration(Region.region0());
        UploadManager uploadManager = new UploadManager(configuration);

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        // 将文件名用UUID保存，保证文件名的唯一性
        String key = UUID.randomUUID().toString().replace("-","");

        // 上传凭证
        String upToken = auth.uploadToken(bucket);

        try {
            uploadManager.put(multipartFile.getBytes(), key, upToken);
            return key;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("文件上传失败");
        }
        return null;
    }

    /**
     * 通过资源key获取七牛云资源url
     * @param key key
     * @return url
     */
    public String getDownloadUrl(String key) {
        Auth auth = Auth.create(accessKey, secretKey);
        DownloadUrl downloadUrl = new DownloadUrl(domain, useHttps, key);
        try {
            return downloadUrl.buildURL(auth, new Date().getTime() + expireInSeconds);
        } catch (QiniuException e) {
            e.printStackTrace();
            log.error("资源地址获取失败");
        }
        return null;
    }

    /**
     * 删除文件
     * @param key key
     */
    public void delete(String key) {
        Auth auth = Auth.create(accessKey, secretKey);
        // 指定网络区域 华东
        Configuration configuration = new Configuration(Region.region0());
        BucketManager bucketManager = new BucketManager(auth, configuration);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException e) {
            e.printStackTrace();
            log.error("资源删除失败");
        }
    }

    /**
     * 批量删除
     * @param keyList keyList
     */
    public void deleteList(String[] keyList) {
        Auth auth = Auth.create(accessKey, secretKey);
        // 指定网络区域 华东
        Configuration configuration = new Configuration(Region.region0());
        BucketManager bucketManager = new BucketManager(auth, configuration);

        BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
        batchOperations.addDeleteOp(bucket, keyList);
        Response response;
        try {
            response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);

            for (int i = 0; i < keyList.length; i++) {
                BatchStatus status = batchStatusList[i];
                if (status.code == 200) {
                    log.info("资源删除成功");
                } else {
                    log.error(status.data.error);
                }
            }
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }

}
