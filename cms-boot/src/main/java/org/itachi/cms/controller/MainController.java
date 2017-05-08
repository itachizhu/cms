package org.itachi.cms.controller;

import org.itachi.cms.constant.Constants;
import org.itachi.cms.dto.AdminUserDTO;
import org.itachi.cms.dto.RoleTreeDTO;
import org.itachi.cms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * Created by yangz on 2017/5/4.
 */
@Controller
@RequestMapping("/")
public class MainController  extends BaseController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "header", method = RequestMethod.GET)
    public String header(Model model) throws Exception {
        AdminUserDTO userDTO = (AdminUserDTO) (request.getSession().getAttribute(Constants.SESSION_KEY));
        model.addAttribute("user",userDTO);
        return "common/header";
    }

    @RequestMapping(value = "leftMenu", method = RequestMethod.GET)
    public String leftMenu(Model model) throws Exception {
        return "common/leftMenu";
    }

    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String welcome(Model model) throws Exception {
        return "common/welcome";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) throws Exception {
        return "/index";
    }

    /**
     加载主页面权限tree
     */

    @RequestMapping(value = "loadMenu", method = RequestMethod.POST)
    @ResponseBody
    public List<RoleTreeDTO> loadMenu() throws Exception {
        AdminUserDTO userDTO = (AdminUserDTO) (request.getSession().getAttribute(Constants.SESSION_KEY));
        List<RoleTreeDTO> list = roleService.loadMenu(userDTO);
        return list;
    }
}
