package org.itachi.cms.controller;

import org.itachi.cms.dto.AdmusergroupDTO;
import org.itachi.cms.dto.GroupRoleRelDTO;
import org.itachi.cms.dto.RoleTreeDTO;
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
    @Autowired
    private GroupRoleRelService groupRoleRelService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws Exception {
        model.addAttribute("name", "liaoyongchao");
        return "userGroups/userGroupList";
    }

    /**
     * 获取管理员组列表数据
     */
    @RequestMapping(value = "/gridlist", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> gridlist(@RequestParam(value = "groupName", required = false) String groupName,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                        @RequestParam(value = "rows", required = false, defaultValue = "3") int rows) throws Exception {
        PagerDTO pager = new PagerDTO(page, rows);
        Map<String, Object> map = new HashMap<>();
        map.put("pager", pager);
        map.put("groupname", groupName);
        Map<String, Object> result = userGroupService.findAdmUserGroup(map);
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
    public String addadmusergroup(@RequestParam("groupname") String groupname,
                                  @RequestParam("describe") String describe,
                                  @RequestParam("ids") String ids) throws Exception {

        if (!StringUtil.isNotNull(groupname)) {
            return "管理员组名称不能为空";
        }

        if (!StringUtil.isMaxSize(groupname, 20)) {
            return "管理员组名称长度不能超过20个字符";
        }

        if (!StringUtil.isNotNull(describe)) {
            return "描述信息不能为空";
        }

        if (!StringUtil.isMaxSize(describe, 50)) {
            return "权限名称长度不能超过50个字符";
        }
        if (!StringUtil.MinSize(ids)) {
            return "权限请至少选择一个";
        }
        AdmusergroupDTO usergroupDTO = new AdmusergroupDTO();
        usergroupDTO.setGroupname(groupname);
        usergroupDTO.setDes(describe);
        usergroupDTO.setIsdel(1);
        int num = userGroupService.addUserGroup(usergroupDTO);

        if (num < 1) {
            return "添加失败";
        }
        int[] gids;
        try {
            gids = StringUtil.strArrToIntArr(ids);
        } catch (Exception e) {
            return "ids不是数字,添加管理员组失败。";
        }
        long newid = userGroupService.findnewUGroupDTO(usergroupDTO);

        List<GroupRoleRelDTO> list = new ArrayList<GroupRoleRelDTO>();
        for (int i = 0; i < gids.length; i++) {
            long gid = gids[i];
            GroupRoleRelDTO groupRoleRelDTO = new GroupRoleRelDTO();
            groupRoleRelDTO.setIsdel(1);
            groupRoleRelDTO.setGroupid(newid);
            groupRoleRelDTO.setRoleid(gid);


            list.add(groupRoleRelDTO);
        }
        num = groupRoleRelService.addUserGroupRels(list);
        if (num < 1) {
            return "出现异常，部分权限添加失败，请补充添加权限。";
        }
        return "success";
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
     * 删除管理员组
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam("ids") String ids) throws ServiceException {
        int[] gids;
        try {
            gids = StringUtil.strArrToIntArr(ids);
        } catch (Exception e) {
            return "ids不是数字,删除管理员组失败。";
        }
        int num=0;
        //num = userGroupService.delUserGroup(gids);
        if (num < 1) {
            return "删除失败";
        }
        // num = userGroupService.delGRoleRelList(gids);
        if (num < 1) {
            return "删除失败";
        }
        return "success";
    }


    /**
     * 加载权限树(用于添加管理员组的时候选择权限)
     */
    @RequestMapping(value = "/loadtreewithoutroot", method = RequestMethod.POST)
    @ResponseBody
    public List<RoleTreeDTO> loadtreewithoutroot() throws Exception {

        List<RoleTreeDTO> list =null;

        list = roleService.listtree(null,false);
        return list;
    }

    /**
     * 加载权限树(用于修改管理员组的时候选择权限-添加时选择的权限在修改的时候需要选中)
     */
    @RequestMapping(value = "/loadtreechecked", method = RequestMethod.POST)
    @ResponseBody
    public List<RoleTreeDTO> loadtreechecked(@RequestParam("admgroupuserid") String admgroupuserid) throws Exception {
        List<RoleTreeDTO> list =null;
        List<Long> roleids =null;
        try {
            long groupId = Long.parseLong(admgroupuserid);
            roleids = groupRoleRelService.findroleid(groupId);
        }catch (Exception e){
        }

        Map<String, Boolean> map = new HashMap<String, Boolean>();
        if(roleids!=null&&roleids.size()>0){

            for (Long roleid : roleids) {
                map.put(roleid + "", true);
            }
        }
        list = roleService.listtree(null,false);
        for (int i = 0, sies = list.size(); i < sies; i++) {
            if(map.containsKey(list.get(i).getId()))
                list.get(i).setChecked(true);

        }

        return list;
    }

}
