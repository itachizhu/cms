package org.itachi.cms.service;

import org.itachi.cms.bean.AdmUserBean;
import org.itachi.cms.dto.AdmuserDTO;
import org.itachi.cms.dto.PagerDTO;
import org.itachi.cms.repository.AdmUserGroupCheckRepository;
import org.itachi.cms.repository.AdmUserGroupRepository;
import org.itachi.cms.repository.AdmUserRepository;
import org.itachi.cms.repository.UserGroupRelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2017-05-05 .
 */

@Service
public class AdmUserService {
    @Autowired
    private AdmUserRepository admUserRepository;

    @Autowired
    private UserGroupRelRepository userGroupRelpository;

    @Autowired
    private AdmUserGroupRepository admUserGroupRepository;

    @Autowired
    private AdmUserGroupCheckRepository groupCheckRepository;

    public Map<String, Object> gridlist(AdmUserBean admUserBean) throws Exception {
        AdmuserDTO userDTO = new AdmuserDTO();
        userDTO.setId(admUserBean.admUserId);
        userDTO.setAccout(admUserBean.account);
        userDTO.setMail(admUserBean.admUserMail);
        userDTO.setPhone(admUserBean.admUserPhone);
        userDTO.setName(admUserBean.admUserName);
        PagerDTO pager = new PagerDTO(admUserBean.page, admUserBean.rows);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", admUserRepository.getUserList(userDTO, pager));
        map.put("total", admUserRepository.getUserCount(userDTO));
        return map;
    }


}