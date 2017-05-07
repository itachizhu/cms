package org.itachi.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping(value = "/myerror", method = RequestMethod.GET)
    @ResponseBody
    public String error() throws Exception {
        throw new Exception("自定义尝试抛出异常!");
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) throws Exception {
        model.addAttribute("name", "itachi");
        return "index";
    }
}
