package org.itachi.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yangz on 2017/5/4.
 */

@Controller
@RequestMapping("/")
public class ViewController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) throws Exception {
        return "index";
    }
}
