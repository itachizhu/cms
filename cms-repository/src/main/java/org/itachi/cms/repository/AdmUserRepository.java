package org.itachi.cms.repository;

import org.itachi.cms.dto.AdmuserDTO;
import org.itachi.cms.dto.PagerDTO;
import org.itachi.cms.mapper.AdmUserMapper;
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
public class AdmUserRepository {
    @Autowired
    private AdmUserMapper admUserMapper;

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public String deleteUserDTO(int[] uids){
        int delUser = admUserMapper.delUser(uids);
        if (delUser < 1) {
            return "删除管理员基本信息失败";
        }

        int delRel = admUserMapper.delRel(uids);
        if (delRel < 1) {
            return "删除管理员和组关系失败";
        }
        return null;
    }

    public AdmuserDTO getUserById(long id) throws Exception {
        return admUserMapper.getUserById(id);
    }

    public AdmuserDTO getUser(String accout) throws Exception {
        return admUserMapper.getUser(accout);
    }

    public AdmuserDTO getUserByAccout(AdmuserDTO amduserDTO) throws Exception {
        return admUserMapper.findUser(amduserDTO);
    }

    public int getUserCount(AdmuserDTO userDTO){
        return admUserMapper.getUserCount(userDTO);
    }

    public List<AdmuserDTO> getUserList(AdmuserDTO userDTO, PagerDTO pager){

        Map<String, Object>map=new HashMap<String, Object>();
        map.put("admUser", userDTO);
        map.put("pager", pager);
        List<AdmuserDTO> userList = admUserMapper.getUserList(map);


        return userList;
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int addUser(AdmuserDTO userDTO){
        return admUserMapper.addUser(userDTO);
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int updateUser(AdmuserDTO userDTO){
        return admUserMapper.updateUser(userDTO);
    }

}
