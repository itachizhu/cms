package org.itachi.cms.provider;

import org.itachi.cms.exception.BaseException;
import org.itachi.cms.util.ThrowableUtil;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 19:46
 */
public class BaseExceptionMapperProvider implements ExceptionMapper<BaseException> {
    @Override
    public Response toResponse(BaseException exception) {
        Map<String, Object> result = new HashMap<>();
        Response.Status status = ThrowableUtil.handleException(result, exception, null);
        return Response.status(status).entity(result).type(MediaType.APPLICATION_JSON + ";charset=UTF-8").build();
    }
}
