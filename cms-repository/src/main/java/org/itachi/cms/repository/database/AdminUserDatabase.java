package org.itachi.cms.repository.database;

import org.itachi.cms.dto.AdminUserDTO;
import org.itachi.cms.mapper.AdminUserMapper;
import org.itachi.cms.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by itachi on 2017/6/13.
 * User: itachi
 * Date: 2017/6/13
 * Time: 16:22
 */
@Component("adminUserDatabase")
public class AdminUserDatabase implements AdminUserRepository {
    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUserDTO findUser(AdminUserDTO adminUserDTO) throws Exception {
        return adminUserMapper.findUser(adminUserDTO);
    }
}
