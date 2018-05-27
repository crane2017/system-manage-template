package com.mksoft.shop.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * 用于JWT认证的过滤器
 */
@WebFilter(urlPatterns = {"/xxx"})
public class JwtFilter extends GenericFilterBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        logger.info(httpRequest.getRequestURL().toString());
        logger.info(httpRequest.getRemoteAddr());

        final String headerKey = jwtConfig.getHeaderKey();
        final String tokenType = jwtConfig.getTokenType();

        String auth = httpRequest.getHeader(headerKey);
        logger.debug(String.format("HttpHeader=%s:%s", headerKey, auth));

        //token字符串未设置或者长度不符合要求(Bearer X.X.X)
        if ((auth == null) || (auth.length() < tokenType.length() + 6)) {
            logger.debug("token字符串未设置或者长度不符合要求(Bearer X.X.X)");
            unAuth(response);
            return;
        }

        String headStr = auth.substring(0, tokenType.length());
        //token类型不符合要求(Bearer)
        if (headStr.compareTo(tokenType) != 0) {
            logger.debug("token类型不符合要求(Bearer)");
            unAuth(response);
            return;
        }

        String token = auth.substring(tokenType.length() + 1, auth.length());
        Jws<Claims> jws = jwtHelper.parseJWT(token);
        Header header = jws.getHeader();
        Claims claims = jws.getBody();

        //token解析不正确
        if (claims == null) {
            logger.debug("token解析不正确或者已经过期");
            unAuth(response);
            return;
        }

        logger.debug(claims.toString());
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader(jwtConfig.getHeaderKey(),jwtHelper.createJWT(header));
        chain.doFilter(request, response);
    }

    private void unAuth(final ServletResponse response) {
        //验证不通过
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        return;
    }
}
