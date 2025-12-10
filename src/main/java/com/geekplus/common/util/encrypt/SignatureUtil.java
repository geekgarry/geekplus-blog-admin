package com.geekplus.common.util.encrypt;

import com.geekplus.common.util.string.StringUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.websocket.DecodeException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * author     : geekplus
 * email      :
 * date       : 12/4/25 1:27 PM
 * description: 负责生成和验证签名
 *
 * 注意：SECRET_KEY 不应硬编码到代码，示例为便于演示。
 * 生产环境请使用配置中心 / KMS / 环境变量注入，并支持秘钥轮换机制（见后面）。
 */
@Component
public class SignatureUtil {
    // 生产环境请放 Nacos / Apollo，而不是代码里
    private final String SECRET = "geek-plus-admin-123456";
    private final String keyId = "v1"; // 用于秘钥版本管理，可为空

//    public SignatureUtil(String secret, String keyId) {
//        this.SECRET = secret;
//        this.keyId = keyId;
//    }
//
//    public String getKeyId() {
//        return keyId;
//    }

    public String signedUrl(String path) {
        if(StringUtils.isNotEmpty(path)) {
            long ts = System.currentTimeMillis() / 1000;
            String url = path + "?ts=" + ts + "&sign=" + genSignature(path, ts);
            return url;
        }else {
            return path;
        }
    }

    public String genSignature(String path, long ts) {
        try {
            String data = path + ts;// + exp;

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));

            byte[] digest = mac.doFinal(data.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (Exception e) {
            throw new RuntimeException("Sign error", e);
        }
    }

    public boolean verify(String path, long ts, String sign) {
        return genSignature(path, ts).equals(sign);
    }
}
