package org.itachi.cms.controller;

import org.itachi.cms.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by kyo on 2017/5/6.
 */
public abstract class BaseController {
    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpServletRequest request;

    protected Map<String, String> getQueryParameters() throws Exception {
        return Utils.getQueryParameters(request);
    }
}
