package org.itachi.cms.service;

import org.itachi.cms.dto.AdmuserDTO;
import org.itachi.cms.dto.RoleTreeDTO;
import org.itachi.cms.exception.ServiceException;
import org.itachi.cms.repository.AdmUserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by liaoyongchao on 2017/5/6.
 */
@Component
public class UserGroupService {

    @Autowired
    private AdmUserGroupRepository admUserGroupRepository;

    public Map<String, Object> findAdmUserGroup(Map<String, Object> map) throws ServiceException {
        return admUserGroupRepository.findAdmUserGroup(map);
    }
}
