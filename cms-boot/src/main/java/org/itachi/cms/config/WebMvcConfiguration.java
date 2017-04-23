package org.itachi.cms.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by itachi on 2017/4/23.
 * User: itachi
 * Date: 2017/4/23
 * Time: 10:16
 */
@Configuration
public class WebMvcConfiguration extends WebMvcAutoConfiguration {
    @Primary
    @Bean
    public ObjectMapper mapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }
}
