package org.itachi.cms.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by itachi on 2017/6/13.
 * User: itachi
 * Date: 2017/6/13
 * Time: 17:26
 */
@RestController
@RequestMapping("/api/admin")
@Validated
public class AdminMenuApiController extends BaseController {
    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    public List<Map<String, Object>> menu() throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("pId", 0);
        map.put("id", 2);
        map.put("name", "账号管理");
        list.add(map);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("pId", 2);
        map1.put("id", 3);
        map1.put("name", "用户管理");
        map1.put("url", request.getContextPath() + "/admin/users");
        list.add(map1);
        return list;
    }
}
