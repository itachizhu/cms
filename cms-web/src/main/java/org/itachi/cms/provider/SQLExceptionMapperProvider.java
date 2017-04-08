package org.itachi.cms.provider;

import org.itachi.cms.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 18:56
 */
@Provider
@Priority(Priorities.ENTITY_CODER)
public class SQLExceptionMapperProvider implements ExceptionMapper<SQLException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLExceptionMapperProvider.class);

    @Override
    public Response toResponse(SQLException exception) {
        LOGGER.error("====SQLException: {} \n\n {} \n\n {}", exception, exception.getErrorCode(), exception.getMessage());
        Map<String, Object> result = new HashMap<>();
        Response.Status status = ThrowableUtil.handleException(result, exception, null);
        return Response.status(status).entity(result).type(MediaType.APPLICATION_JSON + ";charset=UTF-8").build();
    }
}
