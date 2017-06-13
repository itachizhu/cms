package org.itachi.cms.service;

import org.itachi.cms.constant.Constants;
import org.itachi.cms.dto.AdminUserDTO;
import org.itachi.cms.error.CmsError;
import org.itachi.cms.exception.ServiceException;
import org.itachi.cms.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by thinkpad on 2017-05-05 .
 */

@Component
public class AdminUserService {
    @Autowired
    @Qualifier("adminUserDatabase")
    private AdminUserRepository adminUserRepository;

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
        adminUserDTO.setUserName(account);
        adminUserDTO.setPassword(password);
        AdminUserDTO userDTO = adminUserRepository.findUser(adminUserDTO);
        // 对应的账号密码在库中找不到，这个时候对象不一定是null，可能是所有值都是空的一个对象
        if (userDTO == null || userDTO.getId() == null || userDTO.getId() < 1L) {
            throw new ServiceException(CmsError.Error.USER_NOT_EXISTS);
        }
        return userDTO;
    }

    public void logout(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(Constants.SESSION_KEY) != null) {
            AdminUserDTO adminUserDTO = (AdminUserDTO)session.getAttribute(Constants.SESSION_KEY);
            if (adminUserDTO != null && adminUserDTO.getId() != null && adminUserDTO.getId() > 0) {

            }
        }
    }
}