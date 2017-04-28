package org.itachi.cms.action;

import org.glassfish.jersey.server.mvc.Viewable;
import org.itachi.cms.constant.Constants;
import org.itachi.cms.dto.AdmuserDTO;
import org.itachi.cms.dto.RoleTreeDTO;
import org.itachi.cms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by itachi on 2017/4/12.
 * User: itachi
 * Date: 2017/4/12
 * Time: 22:56
 */
@Path("/")
public class MainAction extends BaseAction {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 头页面
     */
    @GET
    @Path("header")
    @Produces(MediaType.TEXT_HTML)
   public Viewable header() throws Exception {
        AdmuserDTO userDTO = (AdmuserDTO) (request.getSession().getAttribute(Constants.SESSION_KEY));
        request.setAttribute("userDTO", userDTO);
        return new Viewable("/common/header");
    }

    /**
     * 左侧菜单
     */

    @GET
    @Path("leftMenu")
    @Produces(MediaType.TEXT_HTML)
    public Viewable leftMenu() throws Exception {
        return new Viewable("/common/leftMenu");
    }

    /**
     * 欢迎页面
     */

    @GET
    @Path("welcome")
    @Produces(MediaType.TEXT_HTML)
    public Viewable welcome() throws Exception {
        return new Viewable("/common/welcome");
    }

    /**
     * 进入没有权限页面
     */

    @GET
    @Path("norole")
    @Produces(MediaType.TEXT_HTML)
    public Viewable norole() throws Exception {
        return new Viewable("/common/noRole");
    }





    @POST
    @Path("loadMenu")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RoleTreeDTO> loadMenu() throws Exception {
        AdmuserDTO userDTO = (AdmuserDTO) (request.getSession().getAttribute(Constants.SESSION_KEY));
        return roleRepository.loadMenu(userDTO);
    }


}
