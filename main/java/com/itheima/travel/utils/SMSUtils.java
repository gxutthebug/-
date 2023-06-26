package com.itheima.travel.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;

import java.util.Map;

/**
 * 短信发送工具类
 */
public class SMSUtils {

    // 模版code
    public static final String VALIDATE_CODE = "SMS_461580271"; // 发送短信验证码
//    public static final String ORDER_NOTICE = "SMS_280480092"; // 体检预约成功通知

    // 理解为阿里云的账号密码
    // 您的AccessKey ID
    private static String accessKeyId = "LTAI5tNkn6V3xUXhFQUw7Cpk";
    // 您的AccessKey Secret
    private static String accessKeySecret = "UuUDxMFLTysl8O8m8gCAfDdmlqRcdH";

    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    private static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    /**
     * 发送短信
     *
     * @param telephone    电话号码
     * @param signName     签名
     * @param templateCode 模板编号
     * @param code         验证码
     * @return 返回的状态码为OK，则发送成功
     */
    public static String send(String telephone, String signName, String templateCode, String code) {
        try {
            Client client = SMSUtils.createClient(accessKeyId, accessKeySecret);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(telephone)
                    .setSignName(signName)  // 短信签名
                    .setTemplateCode(templateCode)  // 模板编号
                    .setTemplateParam("{\"code\":\"" + code + "\"}");
            // 发送短信，获取发送结果
            SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
            // 将结果转成Map对象
            Map<String, Object> map = sendSmsResponse.toMap();
            // 获取主体部分
            Map<String, String> body = (Map<String, String>) map.get("body");
            System.out.println(body);
            return body.get("Code");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 失败返回空
        return null;
    }

    /**
     * 发送体检预约成功通知短信
     * @param telephone
     * @param code
     * @return
     */
//    public static String sendOrderNotice(String telephone, String code) {
//        return send(telephone, "万里路程序员广州校区", ORDER_NOTICE, code);
//    }

    /**
     * 发送发送短信验证码
     * @param telephone
     * @param code
     * @return
     */
    public static String sendTelephoneLoginNotice(String telephone, String code) {
        return send(telephone, "姚丰茂的短信验证", VALIDATE_CODE, code);
    }

    /**
     * 调用方式
     *
     * @param args
     */
    public static void main(String[] args) {
        String code = SMSUtils.send("15112559761", "万里路程序员广州校区", VALIDATE_CODE, "520520");
        System.out.println(code);

        // String code2 = SMSUtils.sendOrderNotice("15112559761", "888888");
        // System.out.println(code2);
        //
        // String code3 = SMSUtils.sendTelephoneLoginNotice("15112559761", "666666");
        // System.out.println(code3);
    }
}