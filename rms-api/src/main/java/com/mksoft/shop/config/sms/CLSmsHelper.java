package com.mksoft.shop.config.sms;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by oneway on 2017/5/12.
 */
@Component
public class CLSmsHelper {

    /**
     * 短信发送
     *
     * @param mobile
     * @param verifictionCode
     * @return
     * @throws Exception
     */
    public boolean sendMessage(String mobile, String verifictionCode) {
        vo_message_cl msgCL = new vo_message_cl(mobile, verifictionCode);
        ResponseEntity<String> entity = batchSend(msgCL);

        if (entity == null || entity.getBody() == null) {
            return false;
        }
        if (!HttpStatus.OK.equals(entity.getStatusCode())) {
            return false;
        }

        System.out.println(entity.getBody());
        System.out.println(entity.getBody().length());

        if (entity.getBody().length() != 37) {
            return false;
        }
        if (!entity.getBody().matches("\\d{14},0[\\n]\\d+[\\n]")) {
            return false;
        }
        return true;
    }

    private ResponseEntity<String> batchSend(vo_message_cl msgCL) {
        RestTemplate restTemplate = new RestTemplate();

        String url = msgCL.getUrl();
        url += "?account={account}&pswd={pswd}&mobile={mobile}&needstatus={String.valueOf(needstatus)}&msg={msg}&product={product}&extno={extno}&";

        ResponseEntity<String> entity = null;
        try {
            entity = restTemplate.getForEntity(url, String.class
                    , msgCL.getAccount(), msgCL.getPawd(), msgCL.getMobile(), msgCL.isNeedstatus()
                    , msgCL.getMsg(), msgCL.getProduct(), msgCL.getExtno());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entity;
    }

    /**
     * 获取随机短信验证码
     *
     * @return
     * @author
     */
    public String getRandomSmsVerificationCode() {

        System.out.println("获取随机短信验证码");
        String sRand = "";

        try {
            Random random = new Random();
            for (int i = 0; i < 4; i++) {
                String rand = String.valueOf(random.nextInt(10));
                byte[] bRand = rand.getBytes();

                sRand += rand;

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause().getMessage());
        }
        return sRand;
    }

    private class vo_message_cl implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = -6969166483744264463L;
        private String url = "http://222.73.117.158/msg/HttpBatchSendSM";
        private String account = "XXXXXXX";
        private String pawd = "XXXXXXX";
        private String mobile;
        private String msg = "您好，【";
        private boolean needstatus = true;
        private String product = "XXXXXXX";
        private String extno = null;

        public vo_message_cl() {

        }

        public vo_message_cl(String mobile, String verifictionCode) {
            this.mobile = mobile;
            this.msg = this.msg + verifictionCode + "】是您的验证码！";
        }

        public String getUrl() {
            return url;
        }

        public String getAccount() {
            return account;
        }

        public String getPawd() {
            return pawd;
        }

        public String getMobile() {
            return mobile;
        }

        public String getMsg() {
            return msg;
        }

        public boolean isNeedstatus() {
            return needstatus;
        }

        public String getExtno() {
            return extno;
        }

        public String getProduct() {
            return product;
        }

    }
}
