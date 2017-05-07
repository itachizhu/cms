package org.itachi.cms.service;

import org.itachi.cms.dto.AdminUserDTO;
import org.itachi.cms.dto.PagerDTO;
import org.itachi.cms.error.CmsError;
import org.itachi.cms.exception.ServiceException;
import org.itachi.cms.repository.AdminUserGroupCheckRepository;
import org.itachi.cms.repository.AdminUserGroupRepository;
import org.itachi.cms.repository.AdminUserRepository;
import org.itachi.cms.repository.UserGroupRelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by thinkpad on 2017-05-05 .
 */

@Component
public class AdminUserService {
    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private UserGroupRelRepository userGroupRelpository;

    @Autowired
    private AdminUserGroupRepository adminUserGroupRepository;

    @Autowired
    private AdminUserGroupCheckRepository groupCheckRepository;

    public Map<String, Object> gridlist(Map<String, Object> map) throws Exception {
        AdminUserDTO userDTO = (AdminUserDTO)map.get("userDTO");
        PagerDTO pager = (PagerDTO) map.get("pager");
        map.put("rows", adminUserRepository.getUserList(userDTO, pager));
        map.put("total", adminUserRepository.getUserCount(userDTO));
        return map;
    }

    public AdminUserDTO getUserByAccount(AdminUserDTO amduserDTO) throws Exception {
        return adminUserRepository.getUserByAccount(amduserDTO);
    }

    public AdminUserDTO login(String account, String password) throws Exception {
        // 首先判断账号合法，字符串必须符合对应的值，否则不需要再去数据库里面找了，浪费时间和资源
        if (account.trim().isEmpty() || account.length() > 15) {
            throw new ServiceException(CmsError.Error.ACCOUNT_INVALID);
        }
        // 密码也是一样，且抛出的提示均是非常简洁明了的
        if (password.length() < 6 || password.length() > 16) {
            throw new ServiceException(CmsError.Error.PASSWORD_INVALID);
        }
        AdminUserDTO adminUserDTO = new AdminUserDTO();
        adminUserDTO.setAccount(account);
        adminUserDTO.setPassword(password);
        AdminUserDTO userDTO = adminUserRepository.getUserByAccount(adminUserDTO);
        // 对应的账号密码在库中找不到，这个时候对象不一定是null，可能是所有值都是空的一个对象
        if (userDTO == null || userDTO.getId() == null || userDTO.getId() < 1L) {
            throw new ServiceException(CmsError.Error.USER_NOT_EXISTS);
        }
        return userDTO;
    }
}