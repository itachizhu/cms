package org.itachi.cms.controller;

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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liaoyongchao on 2017/5/3.
 */
@Controller
@RequestMapping("/admusergroup")
public class AdminUserGroupController {
    @Autowired
    private UserGroupService userGroupService;
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

    @RequestMapping(value = "/addUserGroup", method = RequestMethod.GET)
    public String addUserGroup(Model model) throws Exception {
        model.addAttribute("name", "liaoyongchao");
        return "userGroups/addUserGroup.html";
    }

    @RequestMapping(value = "/modifyUserGroup", method = RequestMethod.GET)
    public String modifyUserGroup(Model model) throws Exception {
        model.addAttribute("name", "liaoyongchao");
        return "userGroups/modifyUserGroup.html";
    }

}
