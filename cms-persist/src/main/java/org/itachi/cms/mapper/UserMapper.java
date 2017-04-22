package org.itachi.cms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.itachi.cms.dto.UserDTO;

/**
 * Created by itachi on 2017/3/19.
 * User: itachi
 * Date: 2017/3/19
 * Time: 13:18
 */
@Mapper
public interface UserMapper {
    UserDTO getUser(@Param("id") long id) throws Exception;

    UserDTO getUserByW3Id(@Param("w3Id") String w3Id) throws Exception;
}
