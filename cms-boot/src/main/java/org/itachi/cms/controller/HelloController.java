package org.itachi.cms.controller;

import org.itachi.cms.dto.AdminUserDTO;
import org.itachi.cms.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by itachi on 2017/4/23.
 * User: itachi
 * Date: 2017/4/23
 * Time: 01:35
 */
@Controller
@RequestMapping("/test")
public class HelloController {
    @Autowired
    private AdminMapper adminMapper;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public AdminUserDTO hello() throws Exception {
        return adminMapper.getAdminUser(1L);
        // return "Hello World!";
    }

    @RequestMapping(value = "/myerror", method = RequestMethod.GET)
    @ResponseBody
    public String error() throws Exception {
        throw new Exception("自定义尝试抛出异常!");
    }
}
