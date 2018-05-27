package com.mksoft.shop.config.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
 * JWT配置读取
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    @Value("${jwt.headerKey:Authorization}")
    private String headerKey;

    @Value("${jwt.tokenType:Bearer}")
    private String tokenType;

    @Value("${jwt.clientId:tiaotiao5}")
    private String clientId;

    @Value("${jwt.signKey.admin:tiaotiaowu_admin}")
    private String signKeyAdmin;

    @Value("${jwt.signKey.app:tiaotiaowu_app}")
    private String signKeyApp;

    @Value("${jwt.name:tiaotiao5}")
    private String name;

    @Value("${jwt.expiresMinute.App}")
    private int expiresMinuteApp;

    @Value("${jwt.expiresMinute.Admin}")
    private int expiresMinuteAdmin;

    public String getHeaderKey() {
        return headerKey;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSignKeyAdmin() {
        return signKeyAdmin;
    }

    public void setSignKeyAdmin(String signKeyAdmin) {
        this.signKeyAdmin = signKeyAdmin;
    }

    public String getSignKeyApp() {
        return signKeyApp;
    }

    public void setSignKeyApp(String signKeyApp) {
        this.signKeyApp = signKeyApp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getExpiresMinuteApp() {
        return expiresMinuteApp;
    }

    public void setExpiresMinuteApp(int expiresMinuteApp) {
        this.expiresMinuteApp = expiresMinuteApp;
    }

    public int getExpiresMinuteAdmin() {
        return expiresMinuteAdmin;
    }

    public void setExpiresMinuteAdmin(int expiresMinuteAdmin) {
        this.expiresMinuteAdmin = expiresMinuteAdmin;
    }
}
