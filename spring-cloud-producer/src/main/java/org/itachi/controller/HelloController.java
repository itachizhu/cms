package org.itachi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by itachi on 2017/5/22.
 * User: itachi
 * Date: 2017/5/22
 * Time: 20:31
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello(@RequestParam String name) {
        return "hello " + name + ", this is first message!";
    }
}
