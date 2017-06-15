package org.itachi.cms.repository;

import org.itachi.cms.dto.AdminUserDTO;

/**
 * Created by itachi on 2017/6/13.
 * User: itachi
 * Date: 2017/6/13
 * Time: 16:21
 */
public interface AdminUserRepository {
    AdminUserDTO findUser(AdminUserDTO adminUserDTO) throws Exception;
}
