package org.itachi.cms.action;

import org.glassfish.jersey.server.mvc.Viewable;
import org.itachi.cms.constant.Constants;
import org.itachi.cms.dto.UserDTO;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by itachi on 2017/4/12.
 * User: itachi
 * Date: 2017/4/12
 * Time: 22:56
 */
@Path("/")
public class LoginAction extends BaseAction {
    @GET
    @Path("login")
    @Produces(MediaType.TEXT_HTML)
    public Viewable login() throws Exception {
        return new Viewable("/login");
    }

    @POST
    @Path("login")
    @Produces(APPLICATION_JSON_UTF8)
    public Map<String, Object> toLogin() throws Exception {
        // 获取用户名，密码，到service层验证，数据库取用户数据

        // request.getSession(true).setAttribute();
        UserDTO user = new UserDTO();
        request.getSession(true).setAttribute(Constants.SESSION_KEY, user);

        return null;
    }
}
