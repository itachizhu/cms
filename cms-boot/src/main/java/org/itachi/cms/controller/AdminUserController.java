package org.itachi.cms.controller;

import org.itachi.cms.dto.AdminUserDTO;
import org.itachi.cms.dto.PagerDTO;
import org.itachi.cms.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2017-05-04 .
 */

@Controller
@RequestMapping("/admuser")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String admuserList() throws Exception {
        return "/admUser/admUserList";
    }

    /**
     * 获取分页展示数据
     */
    @ResponseBody
    @RequestMapping(value = "/gridlist", method = RequestMethod.POST)
    public Map<String, Object> gridlist(@RequestParam(value = "admusermail", required = false) String admusermail,
                                        @RequestParam(value = "admuserphone", required = false) String admuserphone,
                                        @RequestParam(value = "admusername", required = false) String admusername,
                                        @RequestParam(value = "admuserid", required = false) Long admuserid,
                                        @RequestParam(value = "admaccout", required = false) String admaccout,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                        @RequestParam(value = "rows", required = false, defaultValue = "10") int rows) throws Exception {

        AdminUserDTO userDTO = new AdminUserDTO();
        userDTO.setId(admuserid);
        userDTO.setAccount(admaccout);
        userDTO.setMail(admusermail);
        userDTO.setPhone(admuserphone);
        userDTO.setName(admusername);
        PagerDTO pager = new PagerDTO(page, rows);
        Map<String, Object> map = new HashMap<>();
        map.put("userDTO",userDTO);
        map.put("pager",pager);
        return adminUserService.gridlist(map);
    }


    /**
     * 进入添加页面
     */
    @RequestMapping(value = "/toaddadmuser", method = RequestMethod.GET)
    public String addAdmuser() throws Exception {
        return "/admUser/addAdmUser";
    }

    /**
     * 添加管理员
     */
    @ResponseBody
    @RequestMapping(value = "/addadmuser", method = RequestMethod.POST)
    public String addadmuser(@RequestParam(value = "account", required = false) String account,
                    @RequestParam(value = "mail", required = false) String mail,
                    @RequestParam(value = "name", required = false) String name,
                    @RequestParam(value = "phone", required = false) String phone,
                    @RequestParam(value = "department", required = false) String department,
                    @RequestParam(value = "password", required = false) String password,
                    @RequestParam(value = "ids", required = false) String ids) throws Exception {
        return adminUserService.addAdminUser(account,mail,name,phone,department,password,ids);
    }




}
