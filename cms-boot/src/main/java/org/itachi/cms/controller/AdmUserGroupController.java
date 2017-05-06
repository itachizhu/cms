package org.itachi.cms.controller;

import org.itachi.cms.service.RoleService;
import org.itachi.cms.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liaoyongchao on 2017/5/3.
 */
@Controller
@RequestMapping("/admusergroup")
public class AdmUserGroupController {
   // @Autowired
    //private UserGroupService userGroupService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws Exception {
        model.addAttribute("name", "liaoyongchao");
        return "userGroups/userGroupList";
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
