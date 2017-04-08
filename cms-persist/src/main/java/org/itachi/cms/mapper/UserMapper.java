package org.itachi.cms.mapper;

import org.apache.ibatis.annotations.Param;
import org.itachi.cms.dto.UserDTO;

/**
 * Created by itachi on 2017/3/19.
 * User: itachi
 * Date: 2017/3/19
 * Time: 13:18
 */
public interface UserMapper {
    UserDTO getUser(@Param("id") long id);

    UserDTO getUserByW3Id(@Param("w3Id") String w3Id);
}
