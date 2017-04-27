package org.itachi.cms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.itachi.cms.dto.AdmuserDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by itachi on 2017/3/19.
 * User: itachi
 * Date: 2017/3/19
 * Time: 13:18
 */
@Mapper
public interface AdmUserMapper {

    int delUser(int[] uids);

    int delRel(int[] uids);

    AdmuserDTO getUserById(long id);

    int addUser(AdmuserDTO amduserDTO);

    int updateUser(AdmuserDTO userDTO);

    AdmuserDTO findUser(AdmuserDTO amduserDTO);

    AdmuserDTO getUser(@Param("accout") String accout);

    int getUserCount(AdmuserDTO userDTO);

    List<AdmuserDTO> getUserList(Map<String, Object> map);

}
