package org.itachi.cms.config;

import org.itachi.cms.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by itachi on 2017/4/23.
 * User: itachi
 * Date: 2017/4/23
 * Time: 09:41
 */
@Configuration
public class WebConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**");
    }
}

