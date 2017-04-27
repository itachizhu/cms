package org.itachi.cms.repository;

import org.itachi.cms.dto.UserGroupRelDTO;
import org.itachi.cms.mapper.UserGroupRelMapper;
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
public class UserGroupRelRepository {
    @Autowired
    private UserGroupRelMapper userGroupRelMapper;

    public int addUserGroupRels(List<UserGroupRelDTO> list) throws Exception {
        return userGroupRelMapper.addUserGroupRels(list);
    }

    public int updateUserGroupRel(long id){

        return userGroupRelMapper.updateUserGroupRel(id);
    }
}
