package org.itachi.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by itachi on 2017/6/13.
 * User: itachi
 * Date: 2017/6/13
 * Time: 16:53
 */
@Controller
@RequestMapping("/admin")
public class AdminViewController extends BaseController {
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu() throws Exception {
        return "admin/menu";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() throws Exception {
        return "admin/index";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() throws Exception {
        return "admin/welcome";
    }

    @RequestMapping(value = "/header", method = RequestMethod.GET)
    public String header(Model model) throws Exception {
        // model.addAttribute("user", request.getSession(false).getAttribute(Constants.SESSION_KEY));
        return "admin/header";
    }
}
