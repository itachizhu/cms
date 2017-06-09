package org.itachi.cms.controller;

import org.hibernate.validator.constraints.NotEmpty;
import org.itachi.cms.bean.ValidParamBean;
import org.itachi.cms.beans.ValidBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.BeanParam;
import javax.ws.rs.QueryParam;

/**
 * Created by itachi on 2017/4/23.
 * User: itachi
 * Date: 2017/4/23
 * Time: 01:35
 */
@Controller
@RequestMapping("/test")
@Validated
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

    @RequestMapping(value = "/valid", method = RequestMethod.GET)
    @ResponseBody
    public String valid(@Size(min = 2, max = 20, message = "{id.size}") @RequestParam String id) throws Exception {
        return "我就试试呗：" + id;
    }

    @RequestMapping(value = "/valid", method = RequestMethod.POST)
    @ResponseBody
    public ValidBean validBean(@Valid @RequestBody ValidBean user) throws Exception {
        return user;
    }

    @RequestMapping(value = "/valid", method = RequestMethod.PUT)
    @ResponseBody
    public ValidParamBean validJaxRsBean(@Valid @BeanParam ValidParamBean user) throws Exception {
        return user;
    }

    @RequestMapping(value = "/valid", method = RequestMethod.DELETE)
    @ResponseBody
    public String validJaxRs(@NotNull @NotEmpty @QueryParam("name") String name) throws Exception {
        return name;
    }
}
