package org.itachi.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by itachi on 2017/6/13.
 * User: itachi
 * Date: 2017/6/13
 * Time: 16:52
 */
@Controller
@RequestMapping("/admin/users")
public class AdminUserViewController extends BaseController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String users() throws Exception {
        return "admin/user/list";
    }
}
