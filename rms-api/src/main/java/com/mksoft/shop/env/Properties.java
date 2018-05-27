package com.mksoft.shop.env;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/common.properties")
public class Properties {

    @Autowired
    private Environment env;

    public String get(String key){
        return env.getProperty(key);
    }

    public String getFileDownloadPath(){
        return env.getProperty("file.download.path");
    }

    public String getSqlConditionSymbol(){
        return env.getProperty("sql.condition.symbol");
    }
}