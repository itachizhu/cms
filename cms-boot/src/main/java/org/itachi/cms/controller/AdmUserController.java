package org.itachi.cms.controller;

import org.itachi.cms.bean.AdmUserBean;
import org.itachi.cms.service.AdmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by thinkpad on 2017-05-04 .
 */

@Controller
@RequestMapping("/api/admuser")
public class AdmUserController {

    @Autowired
    private AdmUserService admUserService;

    /**
     * 获取分页展示数据
     */
    @RequestMapping(value = "/gridlist", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> gridlist(@RequestBody AdmUserBean admUserBean) throws Exception {
        return admUserService.gridlist(admUserBean);
    }

}
