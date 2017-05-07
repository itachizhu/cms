package org.itachi.cms.repository;

import org.itachi.cms.dto.AdmusergroupDTO;
import org.itachi.cms.exception.ServiceException;
import org.itachi.cms.mapper.AdminUserGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by itachi on 2017/3/19.
 * User: itachi
 * Date: 2017/3/19
 * Time: 13:22
 */
@Component
@Transactional(propagation= Propagation.SUPPORTS, readOnly = true)
public class AdminUserGroupRepository {
    @Autowired
    private AdminUserGroupMapper adminUserGroupMapper;

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int delUserGroup(int[] ids){
        return adminUserGroupMapper.delUserGroup(ids);
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int updateUserGroup(AdmusergroupDTO usergroupDTO) throws Exception {
        return adminUserGroupMapper.updateUserGroup(usergroupDTO);
    }

    public long findnewUGroupDTO(AdmusergroupDTO usergroupDTO) throws Exception {
        return adminUserGroupMapper.findnewUGroupDTO(usergroupDTO);
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int addUserGroup(AdmusergroupDTO usergroupDTO) throws Exception {
        return adminUserGroupMapper.addUserGroup(usergroupDTO);
    }

    public AdmusergroupDTO admusergroupById(long id) throws Exception {
        return adminUserGroupMapper.admusergroupById(id);
    }

    public Map<String, Object> findAdmUserGroup(Map<String, Object> map) throws ServiceException {
        int count = adminUserGroupMapper.countAdmUserGroup(map);
        List<AdmusergroupDTO> admusergroupList = adminUserGroupMapper.findAdmUserGroup(map);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", admusergroupList);
        result.put("total", count);
        return result;
    }


}
