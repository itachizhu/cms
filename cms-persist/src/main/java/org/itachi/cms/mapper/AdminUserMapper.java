package org.itachi.cms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.itachi.cms.dto.AdminUserDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by itachi on 2017/3/19.
 * User: itachi
 * Date: 2017/3/19
 * Time: 13:18
 */
@Mapper
public interface AdminUserMapper {

    int delUser(int[] uids);

    int delRel(int[] uids);

    AdminUserDTO getUserById(long id);

    int addUser(AdminUserDTO amduserDTO);

    int updateUser(AdminUserDTO userDTO);

    AdminUserDTO findUser(AdminUserDTO amduserDTO);

    AdminUserDTO getUser(@Param("account") String account);

    int getUserCount(AdminUserDTO userDTO);

    List<AdminUserDTO> getUserList(Map<String, Object> map);

}
