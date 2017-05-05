package org.itachi.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liaoyongchao on 2017/5/3.
 */
@Controller
@RequestMapping("/group")
public class UserGroupController {
    @RequestMapping(value = "/userGroupList", method = RequestMethod.GET)
    public String index(Model model) throws Exception {
        model.addAttribute("name", "liaoyongchao");
        return "userGroups/userGroupList";
    }

}
