package com.szj.beta.strive.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author shizijie
 * @version 2019-07-28 下午9:30
 */
@Configuration
public class DruidConfig {
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    @Bean
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Component
    @ConfigurationProperties(prefix = "spring.datasource.druid.stat-view-servlet")
    @Setter
    class StatViewServletConfig{
        private String urlPattern;
        private String allow;
        private String deny;
        private String resetEnable;
        private String loginUsername;
        private String loginPassword;
        @Bean
        public ServletRegistrationBean druidStatViewServlet() {
            ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),urlPattern);
            // IP白名单
            servletRegistrationBean.addInitParameter("allow", allow);
            // IP黑名单(共同存在时，deny优先于allow)
            servletRegistrationBean.addInitParameter("deny", deny);
            //控制台管理用户
            servletRegistrationBean.addInitParameter("loginUsername", loginUsername);
            servletRegistrationBean.addInitParameter("loginPassword", loginPassword);
            //是否能够重置数据 禁用HTML页面上的“Reset All”功能
            servletRegistrationBean.addInitParameter("resetEnable", resetEnable);
            return servletRegistrationBean;
        }
    }
    @Component
    @ConfigurationProperties(prefix = "spring.datasource.druid.web-stat-filter")
    @Setter
    class WebStatFilterConfig{
        private boolean enabled;
        private String urlPattern;
        private String exclusions;

        @Bean
        public FilterRegistrationBean druidWebStatFilter() {
            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
            filterRegistrationBean.setEnabled(enabled);
            filterRegistrationBean.setUrlPatterns(Arrays.asList(urlPattern));
            filterRegistrationBean.setUrlPatterns(Arrays.asList(exclusions));
            return filterRegistrationBean;
        }
    }


}
