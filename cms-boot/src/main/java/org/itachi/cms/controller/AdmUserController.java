package org.itachi.cms.controller;

import org.itachi.cms.bean.AdmUserBean;
import org.itachi.cms.service.AdmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by thinkpad on 2017-05-04 .
 */

@Controller
@RequestMapping("/admuser")
public class AdmUserController {

    @Autowired
    private AdmUserService admUserService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String admuserList() throws Exception {
        return "/admUser/admUserList";
    }

    /**
     * 获取分页展示数据
     */
    @ResponseBody
    @RequestMapping(value = "/gridlist", method = RequestMethod.POST)
    public Map<String, Object> gridlist(AdmUserBean admUserBean) throws Exception {
        return admUserService.gridlist(admUserBean);
    }

}
