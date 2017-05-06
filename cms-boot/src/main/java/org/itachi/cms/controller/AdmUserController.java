package org.itachi.cms.controller;

import org.itachi.cms.dto.AdmuserDTO;
import org.itachi.cms.dto.PagerDTO;
import org.itachi.cms.service.AdmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import java.util.HashMap;
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
    public Map<String, Object> gridlist(@FormParam("admusermail") String admusermail,
                                        @FormParam("admuserphone") String admuserphone,
                                        @FormParam("admusername") String admusername,
                                        @FormParam("admuserid") Long admuserid,
                                        @FormParam("account") String account,
                                        @DefaultValue("1")@FormParam("page") int page,
                                        @DefaultValue("10")@FormParam("rows") int rows) throws Exception {

        AdmuserDTO userDTO = new AdmuserDTO();
        userDTO.setId(admuserid);
        userDTO.setAccout(account);
        userDTO.setMail(admusermail);
        userDTO.setPhone(admuserphone);
        userDTO.setName(admusername);
        PagerDTO pager = new PagerDTO(page, rows);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userDTO",userDTO);
        map.put("pager",pager);
        return admUserService.gridlist(map);
    }

}
