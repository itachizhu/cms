package org.itachi.cms.service;

import org.itachi.cms.exception.ServiceException;
import org.itachi.cms.repository.AdminUserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by liaoyongchao on 2017/5/6.
 */
@Component
public class UserGroupService {

    @Autowired
    private AdminUserGroupRepository adminUserGroupRepository;

    public Map<String, Object> findAdmUserGroup(Map<String, Object> map) throws ServiceException {
        return adminUserGroupRepository.findAdmUserGroup(map);
    }
}
