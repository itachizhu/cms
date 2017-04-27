package org.itachi.cms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.itachi.cms.dto.AdmusergroupDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by itachi on 2017/3/19.
 * User: itachi
 * Date: 2017/3/19
 * Time: 13:18
 */
@Mapper
public interface AdmUserGroupMapper {
    AdmusergroupDTO admusergroupById(long id);

    int delUserGroup(int[] ids);

    int updateUserGroup(AdmusergroupDTO admusergroupDTO);

    int addUserGroup(AdmusergroupDTO admusergroupDTO);

    List<AdmusergroupDTO> findAdmUserGroup(Map<String, Object> map);

    long findnewUGroupDTO(AdmusergroupDTO admusergroupDTO);

    int countAdmUserGroup(Map<String, Object> map);


}
