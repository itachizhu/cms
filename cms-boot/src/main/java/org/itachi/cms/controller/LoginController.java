package org.itachi.cms.controller;

import org.itachi.cms.constant.Constants;
import org.itachi.cms.dto.AdmuserDTO;
import org.itachi.cms.service.AdmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * Created by kyo on 2017/5/6.
 */
@Controller
public class LoginController extends BaseController {
    @Autowired
    private AdmUserService admUserService;

    /**
     * 登陆
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toLogin(@RequestParam("accout") String account, @RequestParam("password") String password) throws Exception {
        // 获取用户名，密码，到service层验证，数据库取用户数据

        AdmuserDTO amduserDTO = new AdmuserDTO();
        amduserDTO.setAccout(account);
        amduserDTO.setPassword(password);
        AdmuserDTO userDTO = admUserService.getUserByAccout(amduserDTO);

        if (userDTO == null) {
            throw new Exception("账号或者密码错误");
        }
        request.getSession(true).setAttribute(Constants.SESSION_KEY, userDTO);
    }

    /**
     * 进入登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() throws Exception {
        return "common/loginPage";
    }

    /**
     * 退出登陆
     */
    @RequestMapping(value = "/loginout", method = RequestMethod.GET)
    public String loginout() throws Exception {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Enumeration<String> names = session.getAttributeNames();
                if (names != null) {
                    while (names.hasMoreElements()) {
                        String attribute = names.nextElement();
                        session.removeAttribute(attribute);
                    }
                }
                session.invalidate();
            }
        } catch (Exception e) {
        }
        return "redirect:/login";
    }
}
