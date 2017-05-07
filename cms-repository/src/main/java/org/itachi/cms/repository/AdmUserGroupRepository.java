package org.itachi.cms.repository;

import org.itachi.cms.dto.AdmusergroupDTO;
import org.itachi.cms.exception.ServiceException;
import org.itachi.cms.mapper.AdmUserGroupMapper;
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
public class AdmUserGroupRepository {
    @Autowired
    private AdmUserGroupMapper admUserGroupMapper;

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int delUserGroup(int[] ids){
        return admUserGroupMapper.delUserGroup(ids);
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int updateUserGroup(AdmusergroupDTO usergroupDTO) throws Exception {
        return admUserGroupMapper.updateUserGroup(usergroupDTO);
    }

    public long findnewUGroupDTO(AdmusergroupDTO usergroupDTO) throws Exception {
        return admUserGroupMapper.findnewUGroupDTO(usergroupDTO);
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int addUserGroup(AdmusergroupDTO usergroupDTO) throws Exception {
        return admUserGroupMapper.addUserGroup(usergroupDTO);
    }

    public AdmusergroupDTO admusergroupById(long id) throws Exception {
        return admUserGroupMapper.admusergroupById(id);
    }

    public Map<String, Object> findAdmUserGroup(Map<String, Object> map) throws ServiceException {
        int count = admUserGroupMapper.countAdmUserGroup(map);
        List<AdmusergroupDTO> admusergroupList = admUserGroupMapper.findAdmUserGroup(map);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", admusergroupList);
        result.put("total", count);
        return result;
    }


}
