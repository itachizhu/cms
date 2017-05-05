package org.itachi.cms.service;

import org.itachi.cms.dto.AdmuserDTO;
import org.itachi.cms.dto.RoleTreeDTO;
import org.itachi.cms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yangz on 2017/5/5.
 */
@Component
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleTreeDTO> loadMenu(AdmuserDTO userDTO) throws Exception {
        return roleRepository.loadMenu(userDTO);
    }


}
