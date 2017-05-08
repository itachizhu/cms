package org.itachi.cms.service;

import org.itachi.cms.dto.GroupRoleRelDTO;
import org.itachi.cms.repository.GroupRoleRelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liaoyongchao on 2017/5/7.
 */
@Component
public class GroupRoleRelService {

    @Autowired
    private GroupRoleRelRepository groupRoleRelRepository;
    public List<Long> findroleid(long id){
        return groupRoleRelRepository.findroleid(id);
    }

    public int addUserGroupRels(List<GroupRoleRelDTO> list) throws Exception {
        return groupRoleRelRepository.addUserGroupRels(list);
    }
    public int delGroupRoleRel(long id) throws Exception {
        return groupRoleRelRepository.delGroupRoleRel(id);
    }
    public int delGRoleRelList(int[] ids)throws Exception{
        return groupRoleRelRepository.delGRoleRelList(ids);
    }


}
