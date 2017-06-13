package org.itachi.cms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.itachi.cms.dto.AdminUserDTO;

/**
 * Created by itachi on 2017/3/19.
 * User: itachi
 * Date: 2017/3/19
 * Time: 13:18
 */
@Mapper
public interface AdminUserMapper {
    AdminUserDTO findUser(AdminUserDTO adminUserDTO);
}
