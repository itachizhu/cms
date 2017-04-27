package org.itachi.cms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.itachi.cms.dto.UserGroupRelDTO;

import java.util.List;

/**
 * Created by DE-9887 on 2017/4/20.
 */
@Mapper
public interface UserGroupRelMapper {

    int addUserGroupRels(List<UserGroupRelDTO> list);

    int updateUserGroupRel(long id);
}

