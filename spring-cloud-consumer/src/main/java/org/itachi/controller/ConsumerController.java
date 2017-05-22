package org.itachi.controller;

import org.itachi.remote.HelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by itachi on 2017/5/22.
 * User: itachi
 * Date: 2017/5/22
 * Time: 21:14
 */
@RestController
public class ConsumerController {
    @Autowired
    HelloRemote helloRemote;

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {
        return helloRemote.hello(name);
    }
}
