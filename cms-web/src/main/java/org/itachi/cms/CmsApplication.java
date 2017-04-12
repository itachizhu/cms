package org.itachi.cms;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.itachi.cms.filter.AuthorizationRequestFilter;
import org.itachi.cms.provider.*;

import javax.ws.rs.Priorities;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 16:51
 */
public class CmsApplication extends ResourceConfig {
    private static final String PACKAGE_NAME = "org.itachi.cms.action";

    public CmsApplication() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(JspMvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/jsp");
        packages(PACKAGE_NAME)
                .register(RequestContextFilter.class)
                .register(JacksonIgnoreNullProvider.class)
                .register(ExceptionMapperProvider.class)
                .register(ConstraintViolationProvider.class)
                .register(SQLExceptionMapperProvider.class)
                .register(BadSqlGrammarExceptionMapperProvider.class)
                .register(BaseExceptionMapperProvider.class)
                .register(MultiPartFeature.class)
                .register(JspMvcFeature.class)
                .register(AuthorizationRequestFilter.class, Priorities.AUTHENTICATION)
                .addProperties(properties);
    }
}
