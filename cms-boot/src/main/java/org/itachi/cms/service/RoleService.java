package org.itachi.cms.service;

import org.itachi.cms.dto.AdminUserDTO;
import org.itachi.cms.dto.RoleDTO;
import org.itachi.cms.dto.RoleTreeDTO;
import org.itachi.cms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * Created by yangz on 2017/5/5.
 */
@Component
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public int addRoleDTO(RoleDTO roleDTO) {
        return roleRepository.addRoleDTO(roleDTO);
    }

    public String deleteRoleDTO(int[] roleids) {
        return roleRepository.deleteRoleDTO(roleids);

    }

    public int updateRoleDTO(RoleDTO roleDTO) {

        return roleRepository.updateRoleDTO(roleDTO);
    }

    public RoleDTO findRole(long id) {
        return roleRepository.findRole(id);
    }

    public List<RoleTreeDTO> loadMenu(AdminUserDTO admuserDTO) throws Exception {
        return roleRepository.loadMenu(admuserDTO);
    }


    public Boolean isAdminUser(AdminUserDTO admuserDTO) throws Exception {
        return roleRepository.isAdminUser(admuserDTO);
    }


    public Map<String, Object> gridlist(int roleid, String roleName, String roleUrl, int begin, int rows) throws Exception {
        return roleRepository.gridlist(roleid,roleName,roleUrl,begin,rows);
    }

    public List<RoleTreeDTO> listtree(AdminUserDTO userDTO, boolean bool) throws Exception {
        return roleRepository.listtree(userDTO,bool);
    }

}
