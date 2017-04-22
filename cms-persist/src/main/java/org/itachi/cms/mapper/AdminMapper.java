package org.itachi.cms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.itachi.cms.dto.AdminUserDTO;

/**
 * Created by itachi on 2017/4/23.
 * User: itachi
 * Date: 2017/4/23
 * Time: 01:28
 */
@Mapper
public interface AdminMapper {
    AdminUserDTO getAdminUser(@Param("id") long id) throws Exception;
}

