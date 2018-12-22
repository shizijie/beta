package com.shizijie.beta.auth.config;

import com.shizijie.beta.filter.LoginFilter;
import com.shizijie.beta.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author shizijie
 * @version 2018-12-15 下午10:33
 */
@Configuration
public class FilterConfig {
    @Autowired
    RedisService redisService;
    @Autowired
    Environment env;
    @Bean
    public LoginFilter loginFilter(){
        return new LoginFilter(redisService,env);
    }
    @Bean
    public FilterRegistrationBean filterRegistrationBean(LoginFilter loginFilter){
        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
        registrationBean.setFilter(loginFilter);
        registrationBean.addUrlPatterns();
        registrationBean.setName("login");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
