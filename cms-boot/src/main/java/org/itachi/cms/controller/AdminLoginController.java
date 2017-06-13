package org.itachi.cms.controller;

import org.itachi.cms.constant.Constants;
import org.itachi.cms.service.AdminUserService;
import org.itachi.cms.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by itachi on 2017/6/12.
 * User: itachi
 * Date: 2017/6/12
 * Time: 15:48
 */
@Controller
@RequestMapping("/admin")
@Validated
public class AdminLoginController extends BaseController {
    @Autowired
    private AdminUserService adminUserService;

    /**
     * 登陆
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toLogin(@RequestParam("account") String account,
                        @RequestParam("password") String password) throws Exception {
        request.getSession(true).setAttribute(Constants.SESSION_KEY, adminUserService.login(account, password));
    }

    /**
     * 进入登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() throws Exception {
        Utils.cleanSessions(request);
        return "admin/login";
    }

    /**
     * 退出登陆
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String loginOut() throws Exception {
        // adminUserService.logout(request);
        Utils.cleanSessions(request);
        return "redirect:/admin/login";
    }
}
