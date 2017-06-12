package org.itachi.cms.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.itachi.cms.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by itachi on 2017/4/23.
 * User: itachi
 * Date: 2017/4/23
 * Time: 09:41
 */
@Configuration
public class WebConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> includePatterns;
        List<String> excludePatterns;
        // 添加需要验证的路径正则表达式集合
        try {
            includePatterns = mapper.readValue(messageSource.getMessage("include.patterns",null, null, Locale.getDefault()), new TypeReference<List<String>>(){});
         } catch (Exception e) {
            includePatterns = new ArrayList<>();
        }
        // 添加需要忽略，不进入验证的正则表达式集合
        try {
            excludePatterns = mapper.readValue(messageSource.getMessage("exclude.patterns",null, null, Locale.getDefault()), new TypeReference<List<String>>(){});
        } catch (Exception e) {
            excludePatterns = new ArrayList<>();
        }
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns(includePatterns.toArray(new String[]{}))
                .excludePathPatterns(excludePatterns.toArray(new String[]{}));
    }

    /*
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(myHttpMessageConverter());
    }

    private MappingJackson2HttpMessageConverter myHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false);
        mapper.configure(DeserializationFeature.WRAP_EXCEPTIONS, false);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
        converter.setObjectMapper(mapper);
        return converter;
    }
    */
}

