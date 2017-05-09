package org.itachi.cms.controller;

import org.itachi.cms.dto.AdmusergroupDTO;
import org.itachi.cms.dto.GroupRoleRelDTO;
import org.itachi.cms.dto.RoleTreeDTO;
import org.itachi.cms.error.CmsError;
import org.itachi.cms.exception.ServiceException;
import org.itachi.cms.service.GroupRoleRelService;
import org.itachi.cms.util.StringUtil;
import org.itachi.cms.dto.PagerDTO;
import org.itachi.cms.service.RoleService;
import org.itachi.cms.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liaoyongchao on 2017/5/3.
 */
@Controller
@RequestMapping("/admusergroup")
public class AdminUserGroupController extends BaseController{
    @Autowired
    private UserGroupService userGroupService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws Exception {
        return "userGroups/userGroupList";
    }

    /**
     * 获取管理员组列表数据
     */
    @RequestMapping(value = "/gridlist", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> gridlist(@RequestParam(value = "groupName", required = false) String groupName,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                        @RequestParam(value = "rows", required = false, defaultValue = "10") int rows) throws Exception {
        Map<String, Object> result = userGroupService.findAdmUserGroup(groupName,page,rows);
        return result;
    }

    @RequestMapping(value = "/toadd", method = RequestMethod.GET)
    public String addUserGroup(Model model) throws Exception {
        model.addAttribute("name", "liaoyongchao");
        return "userGroups/addUserGroup";
    }


    /**
     * 添加管理员组
     */
    @RequestMapping(value = "/addadmusergroup", method = RequestMethod.POST)
    @ResponseBody
    public String addadmusergroup(@Size(min = 1,max = 20, message = "{userGroup.name.size}")
                                  @RequestParam("groupname") String groupname,
                                  @Size(min = 1,max = 50, message = "{userGroup.desc.size}")
                                  @RequestParam("describe") String describe,
                                  @NotNull@RequestParam("ids") String ids) throws Exception {

        return userGroupService.addUserGroupForResult(groupname,describe,ids);
    }

    /**
     * 进入修改管理员组页面
     */

    @RequestMapping(value = "/tomodify", method = RequestMethod.GET)
    public String tomodify(@RequestParam("admusergroupid") long id,Model model) throws Exception {

        AdmusergroupDTO admusergroupDTO = userGroupService.admusergroupById(id);
        model.addAttribute("admusergroup", admusergroupDTO);

        return "userGroups/modifyUserGroup";
    }


    /**
     * 修改管理员组
     */
    @RequestMapping(value = "/modifyadmusergroup", method = RequestMethod.POST)
    @ResponseBody
    public String modifyadmusergroup(@RequestParam("id") long id,
                                     @Size(min = 1,max = 20, message = "{userGroup.name.size}")
                                     @RequestParam("groupname") String groupname,
                                     @Size(min = 1,max = 50, message = "{userGroup.desc.size}")
                                     @RequestParam("describe") String describe,
                                     @NotNull@RequestParam("ids") String ids) throws Exception {

        return  userGroupService.modifyUserGroupForResult(id,groupname,describe,ids);
    }

    /**
     * 删除管理员组
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam("ids") String ids) throws Exception {

        return userGroupService.delUserGroupForResult(ids);

    }


    /**
     * 加载权限树(用于添加管理员组的时候选择权限)
     */
    @RequestMapping(value = "/loadtreewithoutroot", method = RequestMethod.POST)
    @ResponseBody
    public List<RoleTreeDTO> loadtreewithoutroot() throws Exception {
        return  userGroupService.loadtreewithoutrootList();
    }

    /**
     * 加载权限树(用于修改管理员组的时候选择权限-添加时选择的权限在修改的时候需要选中)
     */
    @RequestMapping(value = "/loadtreechecked", method = RequestMethod.POST)
    @ResponseBody
    public List<RoleTreeDTO> loadtreechecked(@RequestParam("admgroupuserid") String admgroupuserid) throws Exception {
        return userGroupService.getRoleTreeList(admgroupuserid);
    }

}
