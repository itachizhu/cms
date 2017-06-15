package org.itachi.cms.controller;

import org.itachi.cms.dto.AdminMenuDTO;
import org.itachi.cms.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by itachi on 2017/6/13.
 * User: itachi
 * Date: 2017/6/13
 * Time: 17:26
 */
@RestController
@RequestMapping("/api/admin/menus")
@Validated
public class AdminMenuApiController extends BaseController {

    @Autowired
    private AdminMenuService adminMenuService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public List<AdminMenuDTO> menus() throws Exception {
        return adminMenuService.menus(request.getContextPath());
    }
}
