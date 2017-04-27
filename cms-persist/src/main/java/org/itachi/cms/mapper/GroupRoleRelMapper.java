package org.itachi.cms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.itachi.cms.dto.GroupRoleRelDTO;

import java.util.List;

/**
 * Created by DE-9887 on 2017/4/20.
 */
@Mapper
public interface GroupRoleRelMapper {

    int addgroupRoleRel(List<GroupRoleRelDTO> list);
    int delGroupRoleRel(long id);
    List<Long> findroleid(long id);
    int delGRoleRelList(int[] ids);
}

