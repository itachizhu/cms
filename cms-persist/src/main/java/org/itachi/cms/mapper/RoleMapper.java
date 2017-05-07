package org.itachi.cms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.itachi.cms.dto.AdminUserDTO;
import org.itachi.cms.dto.RoleDTO;
import org.itachi.cms.dto.RoleTreeDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by itachi on 2017/3/19.
 * User: itachi
 * Date: 2017/3/19
 * Time: 13:18
 */
@Mapper
public interface RoleMapper {
    List<Long> isAdmin(AdminUserDTO admuserDTO);
    List<RoleTreeDTO> getAdminRole();
    List<RoleTreeDTO> getRoleTree(AdminUserDTO admuserDTO);
    List<RoleDTO> getRoles(Map<String, Object> map);
    int countRole(Map<String, Object> map);
    int countRolesByPid(int[] roleids);
    List<RoleTreeDTO> listTree(Map<String, Object> map);
    int addRoleDTO(RoleDTO roleDTO);
    int updateRoleDTO(RoleDTO roleDTO);
    int deleteRoleDTO(int[] roleids);
    RoleDTO findRole(long id);

}
