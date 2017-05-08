package org.itachi.cms.controller;

import org.itachi.cms.constant.Constants;
import org.itachi.cms.dto.AdminUserDTO;
import org.itachi.cms.dto.PagerDTO;
import org.itachi.cms.dto.RoleDTO;
import org.itachi.cms.dto.RoleTreeDTO;
import org.itachi.cms.service.RoleService;
import org.itachi.cms.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * Created by yangz on 2017/5/5.
 */
@Controller
@RequestMapping("/role")
@Validated
public class RoleController extends BaseController {

    protected static final String SUCCESS ="success";

    @Autowired
    private RoleService roleService;
    /**
     * 进入分页展示页面
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list() throws Exception {
        return "/role/roleList";
    }


    /**
     * 获取分页展示数据
     */
    @RequestMapping(value = "gridlist", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> gridlist(@RequestParam(value="roleid", required = false)  int roleId,
                                        @RequestParam(value="roleName", required = false)  String roleName,
                                        @RequestParam(value="roleUrl", required = false) String roleUrl,
                                        @RequestParam(value="page", required = false) int page,
                                        @RequestParam(value="rows", required = false) int rows) throws Exception {


        PagerDTO pager = new PagerDTO(page, rows);
        Map<String, Object> map = roleService.gridlist(roleId, roleName,roleUrl,pager.getBegin(), pager.getRows());

        return map;
    }

    /**
     * 加载权限树
     */
    @RequestMapping(value = "listtree", method = RequestMethod.POST)
    @ResponseBody
    public List<RoleTreeDTO> listtree(@RequestParam("id") Long id) throws Exception {
        List<RoleTreeDTO> list =null;
        AdminUserDTO userDTO =null;
        if (id==null) {
            userDTO = (AdminUserDTO) (request.getSession().getAttribute(Constants.SESSION_KEY));
            list = roleService.listtree(userDTO,true);
        }else {
            userDTO=new AdminUserDTO();
            userDTO.setId(id);
            list = roleService.listtree(userDTO,true);
        }
        return list;
    }

    /**
     * 进入添加权限页面
     */

    @RequestMapping(value = "toadd", method = RequestMethod.GET)
    public String toadd() throws Exception {
        return "/role/addRole";
    }


    /**
     * 进入添加权限目录页面
     */
    @RequestMapping(value = "toadddir", method = RequestMethod.GET)
    public String toadddir() throws Exception {
        return "/role/addRoleDir";
    }

    /**
     * 添加权限
     */
    @RequestMapping(value = "addrole", method = RequestMethod.POST)
    @ResponseBody
    public String addrole(@RequestParam("pid") long pid,
                          @Size(min = 2, max = 20, message = "{auth.name.size}") @RequestParam("name") String name,
                          @RequestParam("roleurl") String roleurl,
                          @RequestParam("ismenu") int ismenu,
                          @Size(min = 2, max = 50, message = "{auth.describe.size}")@RequestParam("describe") String describe,
                          @RequestParam("module") String module,
                          @RequestParam("action") String action) throws Exception {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setPid(pid);
        roleDTO.setName(name);
        roleDTO.setRoleurl(roleurl);
        roleDTO.setIsmenu(ismenu);
        roleDTO.setDescribe(describe);
        roleDTO.setModule(module);
        roleDTO.setAction(action);
        int i = roleService.addRoleDTO(roleDTO);
        if (i < 1) {
            return "添加失败！";
        }
        return SUCCESS;
    }


    /**
     * 进入修改页面，根据ID查询权限对象
     */
    @RequestMapping(value = "tomodify", method = RequestMethod.GET)
    public String tomodify(@RequestParam("roleid") long id) throws Exception {
        RoleDTO roleDTO = roleService.findRole(id);

        request.setAttribute("role", roleDTO);
        return "/role/modifyRole";
    }


    /**
     * 修改权限
     */
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    @ResponseBody
    public String modify(@RequestParam("id") long id,
                         @RequestParam("pid") long pid,
                         @Size(min = 2, max = 20, message = "{auth.name.size}") @RequestParam("name") String name,
                         @RequestParam("roleurl") String roleurl,
                         @RequestParam("ismenu") int ismenu,
                         @Size(min = 2, max = 50, message = "{auth.describe.size}")@RequestParam("describe") String describe,
                         @RequestParam("module") String module,
                         @RequestParam("action") String action) throws Exception {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(id);
        roleDTO.setPid(pid);
        roleDTO.setName(name);
        roleDTO.setRoleurl(roleurl);
        roleDTO.setIsmenu(ismenu);
        roleDTO.setDescribe(describe);
        roleDTO.setModule(module);
        roleDTO.setAction(action);
        int i = roleService.updateRoleDTO(roleDTO);
        if (i < 1) {
            return "修改失败！";
        }


        return SUCCESS;
    }

    /**
     * 删除权限
     */
    @RequestMapping(value = "deleterole", method = RequestMethod.POST)
    @ResponseBody
    public String deleterole(@RequestParam("ids") String ids) throws Exception {
        int[] roleids= StringUtil.strArrToIntArr(ids);
        return roleService.deleteRoleDTO(roleids);
    }
}
