package com.zjc.blog.service.impl;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.zjc.blog.service.QiniuService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
public class QiniuServiceImpl implements QiniuService {
    /**
     * 超时时间，单位s
     */
    public static final long EXPIRE_SECONDS = 3600;

    @Value("${qiniu.access-key}")
    private String accessKey;

    @Value("${qiniu.secret-key}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    /**
     * 构造文件上传token，如覆盖上传，则需要传递key
     * @return
     */
    @Override
    public String createToken(String key) {
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucket, key);
    }
}
