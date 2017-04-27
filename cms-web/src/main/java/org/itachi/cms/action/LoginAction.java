package org.itachi.cms.action;

import org.glassfish.jersey.server.mvc.Viewable;
import org.itachi.cms.constant.Constants;
import org.itachi.cms.dto.AdmuserDTO;
import org.itachi.cms.repository.AdmUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Enumeration;

/**
 * Created by itachi on 2017/4/12.
 * User: itachi
 * Date: 2017/4/12
 * Time: 22:56
 */
@Path("/")
public class LoginAction extends BaseAction {
    @Autowired
    private AdmUserRepository admUserRepository;


    /**
     * 退出登陆
     */
    @GET
    @Path("loginout")
    @Produces(MediaType.TEXT_HTML)
    public Viewable loginout() throws Exception {

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Enumeration<String> names = session.getAttributeNames();
                if (names != null) {
                    while (names.hasMoreElements()) {
                        String attribute = names.nextElement();
                        session.removeAttribute(attribute);
                    }
                }
                session.invalidate();
            }
        } catch (Exception e) {
        }
        return new Viewable("/common/loginPage");
    }


    /**
     * 进入登录页面
     */
    @GET
    @Path("login")
    @Produces(MediaType.TEXT_HTML)
    public Viewable login() throws Exception {
        return new Viewable("/common/loginPage");
    }


    /**
     * 登陆
     */
    @POST
    @Path("login/login")
    @Consumes("application/x-www-form-urlencoded")
    // @ResponseStatus(HttpStatus.NO_CONTENT)
    public String toLogin(@FormParam("accout") String accout, @FormParam("password") String password) throws Exception {
        // 获取用户名，密码，到service层验证，数据库取用户数据

        AdmuserDTO amduserDTO = new AdmuserDTO();
        amduserDTO.setAccout(accout);
        amduserDTO.setPassword(password);
        AdmuserDTO userDTO = admUserRepository.getUserByAccout(amduserDTO);

        if (userDTO == null) {
            return "账号或者密码错误";
        }


        request.getSession(true).setAttribute(Constants.SESSION_KEY, userDTO);

        return "success";


    }


}
