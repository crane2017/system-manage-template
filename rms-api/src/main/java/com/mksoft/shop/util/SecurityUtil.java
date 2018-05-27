package com.mksoft.shop.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;

public class SecurityUtil {
    public static String pwdEncode(String pwd) {
        String md5Result = DigestUtils.md5Hex(pwd);
        String sha256Result = DigestUtils.sha256Hex(md5Result);

        return sha256Result;
    }

    public static JSONObject decryptMiniApp(String strSessionKey, String strIv, String strEncryptedData) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        JSONObject result = null;
        String strResult = null;

        Security.addProvider(new BouncyCastleProvider());
        Key key = new SecretKeySpec(Base64.decode(strSessionKey), "AES");

        Cipher out = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        out.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(Base64.decode(strIv)));
        byte[] dec = out.doFinal(Base64.decode(strEncryptedData));
        strResult = new String(dec);

        return (JSONObject)JSONObject.parse(strResult);
    }

    public static String base64Encode(String str) {
        byte[] b = null;
        String encodedStr = null;

        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (b != null) {
            encodedStr = new BASE64Encoder().encode(b);
        }

        return encodedStr;
    }

    public static String base64Decode(String encodedStr) {
        byte[] b = null;
        String result = null;

        if (encodedStr != null) {
            BASE64Decoder decoder = new BASE64Decoder();

            try {
                b = decoder.decodeBuffer(encodedStr);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}