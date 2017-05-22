package org.itachi.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by itachi on 2017/5/22.
 * User: itachi
 * Date: 2017/5/22
 * Time: 21:08
 */
@FeignClient(name = "spring-cloud-producer")
public interface HelloRemote {
    @RequestMapping("/hello")
    String hello(@RequestParam(value = "name") String name);
}
