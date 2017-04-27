package org.itachi.cms.action;

import org.glassfish.jersey.server.mvc.Viewable;
import org.itachi.cms.constant.Constants;
import org.itachi.cms.dto.AdmuserDTO;
import org.itachi.cms.dto.PagerDTO;
import org.itachi.cms.dto.RoleDTO;
import org.itachi.cms.dto.RoleTreeDTO;
import org.itachi.cms.repository.RoleRepository;
import org.itachi.cms.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 20:01
 */
@Path("/role")
public class RoleAction extends BaseAction {

    @Autowired
    private RoleRepository roleRepository;


    /**
     * 进入分页展示页面
     */
    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public Viewable list() throws Exception {
        return new Viewable("/role/roleList");
    }


    /**
     * 获取分页展示数据
     */
    @POST
    @Path("/gridlist")
    @Consumes("application/x-www-form-urlencoded")
    public Map<String, Object> gridlist(@FormParam("roleid") int roleid,
                                        @FormParam("roleName") String roleName,
                                        @FormParam("roleUrl") String roleUrl,
                                        @FormParam("page") int page,
                                        @FormParam("rows") int rows) throws Exception {


        PagerDTO pager = new PagerDTO(page, rows);
        Map<String, Object> map = roleRepository.gridlist(roleid, roleName,roleUrl,pager.getBegin(), pager.getRows());

        return map;
    }

    /**
     * 加载权限树
     */
    @POST
    @Path("/listtree")
    @Consumes({"application/x-www-form-urlencoded", MediaType.APPLICATION_JSON})
    public List<RoleTreeDTO> listtree(@FormParam("id") Long id) throws Exception {
        List<RoleTreeDTO> list =null;
        AdmuserDTO userDTO =null;
        if (id==null) {
             userDTO = (AdmuserDTO) (request.getSession().getAttribute(Constants.SESSION_KEY));
             list = roleRepository.listtree(userDTO,true);
        }else {
            userDTO=new AdmuserDTO();
            userDTO.setId(id);
            list = roleRepository.listtree(userDTO,true);
        }
        return list;
    }

    /**
     * 进入添加权限页面
     */

    @GET
    @Path("/toadd")
    @Produces(MediaType.TEXT_HTML)
    public Viewable toadd() throws Exception {
        return new Viewable("/role/addRole");
    }


    /**
     * 进入添加权限目录页面
     */
    @GET
    @Path("/toadddir")
    @Produces(MediaType.TEXT_HTML)
    public Viewable toadddir() throws Exception {
        return new Viewable("/role/addRoleDir");
    }

    /**
     * 添加权限
     */
    @POST
    @Path("/addrole")
    @Consumes("application/x-www-form-urlencoded")
    public String addrole(@FormParam("pid") long pid,
                          @FormParam("name") String name,
                          @FormParam("roleurl") String roleurl,
                          @FormParam("ismenu") int ismenu,
                          @FormParam("describe") String describe,
                          @FormParam("module") String module,
                          @FormParam("action") String action) throws Exception {
        if (!StringUtil.isNotNull(name)) {
            return "权限名称不能为空";
        }

        if (!StringUtil.isMaxSize(name, 20)) {
            return "权限名称长度不能超过20个字符";
        }

        if (!StringUtil.isNotNull(describe)) {
            return "描述信息不能为空";
        }

        if (!StringUtil.isMaxSize(describe, 50)) {
            return "权限名称长度不能超过50个字符";
        }
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setPid(pid);
        roleDTO.setName(name);
        roleDTO.setRoleurl(roleurl);
        roleDTO.setIsmenu(ismenu);
        roleDTO.setDescribe(describe);
        roleDTO.setModule(module);
        roleDTO.setAction(action);
        int i = roleRepository.addRoleDTO(roleDTO);
        if (i < 1) {
            return "添加失败！";
        }
        return SUCCESS;
    }


    /**
     * 进入修改页面，根据ID查询权限对象
     */
    @GET
    @Path("/tomodify")
    @Produces(MediaType.TEXT_HTML)
    public Viewable tomodify(@QueryParam("roleid") long id) throws Exception {
        RoleDTO roleDTO = roleRepository.findRole(id);

        request.getSession().setAttribute("role", roleDTO);
        return new Viewable("/role/modifyRole");
    }


    /**
     * 修改权限
     */
    @POST
    @Path("/modify")
    @Consumes("application/x-www-form-urlencoded")
    public String modify(@FormParam("id") long id,
                         @FormParam("pid") long pid,
                         @FormParam("name") String name,
                         @FormParam("roleurl") String roleurl,
                         @FormParam("ismenu") int ismenu,
                         @FormParam("describe") String describe,
                         @FormParam("module") String module,
                         @FormParam("action") String action) throws Exception {

        if (!StringUtil.isNotNull(name)) {
            return "权限名称不能为空";
        }

        if (!StringUtil.isMaxSize(name, 20)) {
            return "权限名称长度不能超过20个字符";
        }

        if (!StringUtil.isNotNull(describe)) {
            return "描述信息不能为空";
        }

        if (!StringUtil.isMaxSize(describe, 50)) {
            return "权限名称长度不能超过50个字符";
        }
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(id);
        roleDTO.setPid(pid);
        roleDTO.setName(name);
        roleDTO.setRoleurl(roleurl);
        roleDTO.setIsmenu(ismenu);
        roleDTO.setDescribe(describe);
        roleDTO.setModule(module);
        roleDTO.setAction(action);
        int i = roleRepository.updateRoleDTO(roleDTO);
        if (i < 1) {
            return "修改失败！";
        }


        return SUCCESS;
    }

    /**
     * 删除权限
     */
    @POST
    @Path("/deleterole")
    @Consumes("application/x-www-form-urlencoded")
    public String deleterole(@FormParam("ids") String ids) throws Exception {
        int[] roleids=StringUtil.strArrToIntArr(ids);
        return roleRepository.deleteRoleDTO(roleids);
    }

}
