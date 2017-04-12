package org.itachi.cms.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 19:56
 */
abstract class BaseAction {
    protected static final String APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON + ";charset=UTF-8";

    @Context
    protected HttpServletRequest request;

    @Context
    protected HttpServletResponse response;

    @Context
    protected UriInfo uriInfo;

    protected Map<String, String> getQueryParameters() throws Exception {
        Map<String, String> map = new HashMap<>();
        MultivaluedMap<String, String> parameters = uriInfo.getQueryParameters();
        for (String key : parameters.keySet()) {
            map.put(key, parameters.get(key).get(0));
        }
        return map;
    }
}
