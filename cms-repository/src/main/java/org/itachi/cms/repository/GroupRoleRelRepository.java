package org.itachi.cms.repository;

import org.itachi.cms.dto.GroupRoleRelDTO;
import org.itachi.cms.mapper.GroupRoleRelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by itachi on 2017/3/19.
 * User: itachi
 * Date: 2017/3/19
 * Time: 13:22
 */
@Component
@Transactional(propagation= Propagation.SUPPORTS, readOnly = true)
public class GroupRoleRelRepository {
    @Autowired
    private GroupRoleRelMapper groupRoleRelMapper;

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int addUserGroupRels(List<GroupRoleRelDTO> list) throws Exception {
        return groupRoleRelMapper.addgroupRoleRel(list);
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int delGroupRoleRel(long id) throws Exception {
        return groupRoleRelMapper.delGroupRoleRel(id);
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int delGRoleRelList(int[] ids){
        return groupRoleRelMapper.delGRoleRelList(ids);
    }

    public List<Long> findroleid(long id){
        return groupRoleRelMapper.findroleid(id);
    }

}
