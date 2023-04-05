package com.zjc.blog.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.zjc.blog.enums.StateCodeMsg;
import com.zjc.blog.exception.ServiceException;
import com.zjc.blog.service.AuthCodeService;
import com.zjc.blog.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;

@Slf4j
@Service
public class AuthCodeServiceImpl implements AuthCodeService {

    public static final long EXPIRED_TIME = 300;
    public static final long RETRY_TIME = 60;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${sms.access-key-id}")
    private String accessKeyId;

    @Value("${sms.access-key-secret}")
    private String accessKeySecret;

    @Value("${sms.sign-name}")
    private String signName;

    @Value("${sms.template-code}")
    private String templateCode;

    private Client client;


    public Client createClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    @Override
    public String createAuthCode() {
        return RandomUtil.randomNumbers(6);
    }

    @Override
    public String getAuthCode(String username) {
        return (String) redisUtils.get(username + ":code");
    }

    @Override
    public boolean setAuthCode(String username, String code) {
        return redisUtils.set(username + ":code", code, EXPIRED_TIME)
                && redisUtils.set(username + ":code-retry", true, RETRY_TIME);
    }

    @Override
    public void delAuthCode(String username) {
        redisUtils.del(username + ":code");
        redisUtils.del(username + ":code-retry");
    }

    @Override
    public boolean canSend(String username) {
        return redisUtils.get(username + ":code-retry") == null;
    }

    @Override
    public boolean sendAuthCode(String phone, String code) throws Exception {
        if (client == null) {
            client = createClient();
        }
        SendSmsRequest request = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setTemplateParam("{\"code\":\"" + code + "\"}");
        SendSmsResponse response = client.sendSms(request);
        if (!"OK".equals(response.getBody().code)) {
            log.error(response.getBody().message);
            throw new ServiceException(StateCodeMsg.AUTH_CODE_SEND_ERROR);
        }
        return true;
    }
}
