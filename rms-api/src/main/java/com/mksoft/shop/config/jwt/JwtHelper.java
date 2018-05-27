package com.mksoft.shop.config.jwt;

import com.mksoft.shop.Constants;
import com.mksoft.shop.util.SecurityUtil;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * 构造及解析jwt的工具类
 */
@Component
public class JwtHelper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtConfig jwtConfig;

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;

    @Value("${server.context-path}")
    private String serverContextPath;
    @Value("${jwt.rootUri.login}")
    private String loginRootUri;
    @Value("${jwt.rootUri.admin}")
    private String adminRootUri;
    @Value("${jwt.rootUri.app}")
    private String appRootUri;
    @Value("${jwt.signKey.admin}")
    private String adminSignKey;
    @Value("${jwt.signKey.app}")
    private String appSignKey;
    @Value("${jwt.expiresMinute.App}")
    private Long expirationApp;
    @Value("${jwt.expiresMinute.Admin}")
    private Long expirationAdmin;

    public boolean preHandle() {
        logger.info(request.getRequestURL().toString());
        logger.info(request.getRemoteAddr());

        final String headerKey = jwtConfig.getHeaderKey();
        final String tokenType = jwtConfig.getTokenType();

        String auth = request.getHeader(headerKey);
        logger.debug(String.format("HttpHeader=%s:%s", headerKey, auth));

        //token字符串未设置或者长度不符合要求(Bearer X.X.X)
        if ((auth == null) || (auth.length() < tokenType.length() + 6)) {
            logger.debug("token字符串未设置或者长度不符合要求(Bearer X.X.X)");
            return false;
        }

        String headStr = auth.substring(0, tokenType.length());
        //token类型不符合要求(Bearer)
        if (headStr.compareTo(tokenType) != 0) {
            logger.debug("token类型不符合要求(Bearer)");
            return false;
        }

        String token = auth.substring(tokenType.length() + 1, auth.length());
        Jws<Claims> jws = parseJWT(token);
        Header header = null;
        Claims claims = null;

        if (jws != null) {
            header = jws.getHeader();
            claims = jws.getBody();
        }

        //token解析不正确
        if (claims == null) {
            logger.debug("token解析不正确或者已经过期");
            return false;
        }

        request.setAttribute(Constants.LOGIN_PKID_KEY, header.get(Constants.LOGIN_PKID_KEY));
        request.setAttribute(Constants.LOGIN_UUID_KEY, header.get(Constants.LOGIN_UUID_KEY));

        logger.debug(claims.toString());
        Map.Entry<String, String> headerEntry = createJWTHeader(header);
        response.setHeader(headerEntry.getKey(), headerEntry.getValue());

        return true;
    }

    public String getMatchedSysName() {
        String matchedSysName = "";
        String servletPath = request.getServletPath();

        if (StringUtils.isNotEmpty(servletPath)) {
            if(servletPath.startsWith(adminRootUri)){
                matchedSysName = Constants.SYS_NAME_ADMIN;
            } else if(servletPath.startsWith(appRootUri)){
                matchedSysName = Constants.SYS_NAME_APP;
            } else if(servletPath.startsWith(loginRootUri)){
                if(servletPath.startsWith(loginRootUri + adminRootUri)){
                    matchedSysName = Constants.SYS_NAME_ADMIN;
                } else if(servletPath.startsWith(loginRootUri + appRootUri)){
                    matchedSysName = Constants.SYS_NAME_APP;
                }
            }
        }

        return matchedSysName;
    }

    public String getSignKey() {
        String signKey = "";
        String matchedSysName = getMatchedSysName();

        if(Constants.SYS_NAME_ADMIN.equals(matchedSysName)){
            signKey = jwtConfig.getSignKeyAdmin();
        } else if(Constants.SYS_NAME_APP.equals(matchedSysName)){
            signKey = jwtConfig.getSignKeyApp();
        }

        return SecurityUtil.base64Encode(signKey);
    }

    public Date getExpirationDate() {
        Date expirationDate = null;

        if(Constants.SYS_NAME_ADMIN.equals(getMatchedSysName())){
            expirationDate = new Date(System.currentTimeMillis() + expirationAdmin * 1000 * 60);
        } else if(Constants.SYS_NAME_APP.equals(getMatchedSysName())){
            expirationDate = new Date(System.currentTimeMillis() + expirationApp * 1000 * 60);
        }

        return expirationDate;
    }

    public Jws<Claims> parseJWT(String token) {
        try {
            String key = getSignKey();

            Jws<Claims> jws = Jwts.parser().setSigningKey(key)
                    .parseClaimsJws(token);
            return jws;
        } catch (SignatureException e) {
            logger.debug("签名不正确");
            return null;
        } catch (ExpiredJwtException e) {
            logger.debug("token过期");
            return null;
        } catch (Exception e) {
            logger.debug(e.toString());
            return null;
        }
    }

    public Integer getLoginPkid() {
        preHandle();

        Integer intPkid = null;

        Object objPkid = request.getAttribute(Constants.LOGIN_PKID_KEY);

        if (objPkid != null) {
            intPkid = (Integer) objPkid;
        }

        return intPkid;
    }

    public String getLoginUUID() {
        preHandle();

        String strUUID = "";

        Object objUUID = request.getAttribute(Constants.LOGIN_UUID_KEY);

        if (objUUID != null) {
            strUUID = (String) objUUID;
        }

        return strUUID;
    }

    public Map.Entry<String, String> createJWTHeader(Map user) {
        final String headerKey = jwtConfig.getHeaderKey();
        final String tokenType = jwtConfig.getTokenType();

        String token = createJWT(user);

        HashMap<String, String> header = new HashMap<String, String>();
        //Bearer X.X.X
        header.put(headerKey, String.format("%s %s", tokenType, token));

        return header.entrySet().iterator().next();
    }

    /**
     * 生成token
     *
     * @param user
     * @return
     */
    public String createJWT(Map user) {
        long expMillis = 0;
        Date expirationDate = getExpirationDate();

        if(expirationDate != null){
            expMillis = expirationDate.getTime();
        }

        return createJWT(user, expMillis);
    }

    /**
     * 生成token,指定有效期
     *
     * @param user
     * @param expMillis
     * @return
     */
    public String createJWT(Map user, long expMillis) {

        String audience = jwtConfig.getClientId();
        String issuer = jwtConfig.getName();
        String key = getSignKey();

        Integer userPkid = (Integer) user.get(Constants.LOGIN_PKID_KEY);
        String userUuid = (String) user.get(Constants.LOGIN_UUID_KEY);
        String userLogin = (String) user.get(Constants.LOGIN_LOGIN_KEY);

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setSubject(userLogin)
                .setHeaderParam("typ", "JWT")
                .setHeaderParam(Constants.LOGIN_PKID_KEY, userPkid)
                .setHeaderParam(Constants.LOGIN_UUID_KEY, userUuid)
                .setIssuer(issuer)
                .setAudience(audience)
                .setIssuedAt(new Date())
                .signWith(signatureAlgorithm, key);

        //添加Token过期时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        Date exp = new Date(expMillis);

        builder.setExpiration(exp).setNotBefore(now);

        //生成JWT
        return builder.compact();
    }
}
