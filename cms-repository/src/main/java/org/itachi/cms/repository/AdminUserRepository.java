package org.itachi.cms.repository;

import org.itachi.cms.dto.AdminUserDTO;
import org.itachi.cms.dto.PagerDTO;
import org.itachi.cms.mapper.AdminUserMapper;
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
public class AdminUserRepository {
    @Autowired
    private AdminUserMapper adminUserMapper;

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public String deleteUserDTO(int[] uids){
        int delUser = adminUserMapper.delUser(uids);
        if (delUser < 1) {
            return "删除管理员基本信息失败";
        }

        int delRel = adminUserMapper.delRel(uids);
        if (delRel < 1) {
            return "删除管理员和组关系失败";
        }
        return null;
    }

    public AdminUserDTO getUserById(long id) throws Exception {
        return adminUserMapper.getUserById(id);
    }

    public AdminUserDTO getUser(String accout) throws Exception {
        return adminUserMapper.getUser(accout);
    }

    public AdminUserDTO getUserByAccount(AdminUserDTO amduserDTO) throws Exception {
        return adminUserMapper.findUser(amduserDTO);
    }

    public int getUserCount(AdminUserDTO userDTO){
        return adminUserMapper.getUserCount(userDTO);
    }

    public List<AdminUserDTO> getUserList(AdminUserDTO userDTO, PagerDTO pager){

        Map<String, Object>map=new HashMap<String, Object>();
        map.put("admUser", userDTO);
        map.put("pager", pager);
        List<AdminUserDTO> userList = adminUserMapper.getUserList(map);


        return userList;
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int addUser(AdminUserDTO userDTO){
        return adminUserMapper.addUser(userDTO);
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int updateUser(AdminUserDTO userDTO){
        return adminUserMapper.updateUser(userDTO);
    }

}
