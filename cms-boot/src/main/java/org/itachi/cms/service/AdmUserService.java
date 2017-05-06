package org.itachi.cms.service;

import org.itachi.cms.dto.AdmuserDTO;
import org.itachi.cms.dto.PagerDTO;
import org.itachi.cms.repository.AdmUserGroupCheckRepository;
import org.itachi.cms.repository.AdmUserGroupRepository;
import org.itachi.cms.repository.AdmUserRepository;
import org.itachi.cms.repository.UserGroupRelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Map<String, Object> gridlist(Map<String, Object> map) throws Exception {
        AdmuserDTO userDTO = (AdmuserDTO)map.get("userDTO");
        PagerDTO pager = (PagerDTO) map.get("pager");
        map.put("rows", admUserRepository.getUserList(userDTO, pager));
        map.put("total", admUserRepository.getUserCount(userDTO));
        return map;
    }
    public AdmuserDTO getUserByAccout(AdmuserDTO amduserDTO) throws Exception {
        return admUserRepository.getUserByAccout(amduserDTO);
    }

}