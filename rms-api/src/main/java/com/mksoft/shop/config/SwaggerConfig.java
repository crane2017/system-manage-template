package com.mksoft.shop.config;

import com.google.common.collect.Ordering;
import com.mksoft.shop.config.jwt.JwtConfig;
import com.mksoft.shop.config.jwt.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.ApiListingReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * Swagger配置
     */
    @Bean
    public Docket api() {
        return newDocket("com.mksoft.shop.controller");
    }

    @Bean
    public Docket apiAdmin() {
        return newDocket("com.mksoft.shop.controller.admin");
    }

    @Bean
    public Docket apiApp() {
        return newDocket("com.mksoft.shop.controller.app");
    }

    public Docket newDocket(String target) {
        return new Docket(DocumentationType.SWAGGER_2).groupName(target)
                //.host("")
                //.pathMapping("api")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage(target)) // 对所有api进行监控
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .securitySchemes(newArrayList(apiKey()))
                .apiListingReferenceOrdering(new Ordering<ApiListingReference>() {
                    @Override
                    public int compare(ApiListingReference left, ApiListingReference right) {
                        return left.getPath().compareTo(right.getPath());
                    }
                });
    }

    /**
     * 设置右上角Authorize按钮
     *
     * @return
     */
    private ApiKey apiKey() {
        return new ApiKey(jwtConfig.getHeaderKey(), "", ApiKeyVehicle.HEADER.getValue());
    }

    /**
     * 自动设置认证token
     *
     * @return
     */

/*    @Bean
    @Profile("dev")
    SecurityConfiguration security() {
        Map.Entry<String, String> tokenHeader = jwtHelper.createJWTHeader("1");
        return new SecurityConfiguration(
                "clientId", "clientSecret", "realm", "appName"
                , tokenHeader.getValue(), ApiKeyVehicle.HEADER, tokenHeader.getKey()
                , "scopeSeparator");
    }*/
}