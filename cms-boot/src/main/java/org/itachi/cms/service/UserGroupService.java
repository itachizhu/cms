package org.itachi.cms.service;

import org.itachi.cms.dto.AdmusergroupDTO;
import org.itachi.cms.dto.GroupRoleRelDTO;
import org.itachi.cms.dto.PagerDTO;
import org.itachi.cms.dto.RoleTreeDTO;
import org.itachi.cms.error.CmsError;
import org.itachi.cms.exception.ServiceException;
import org.itachi.cms.repository.AdminUserGroupRepository;
import org.itachi.cms.repository.GroupRoleRelRepository;
import org.itachi.cms.repository.RoleRepository;
import org.itachi.cms.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liaoyongchao on 2017/5/6.
 */
@Component
public class UserGroupService {

    @Autowired
    private AdminUserGroupRepository admUserGroupRepository;
    @Autowired
    private GroupRoleRelRepository groupRoleRelRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Map<String, Object> findAdmUserGroup(String groupName,int page,int rows) throws Exception {
        PagerDTO pager = new PagerDTO(page, rows);
        Map<String, Object> map = new HashMap<>();
        map.put("pager", pager);
        map.put("groupname", groupName);
        return admUserGroupRepository.findAdmUserGroup(map);
    }

    public AdmusergroupDTO admusergroupById(long id) throws Exception {
        return admUserGroupRepository.admusergroupById(id);
    }


    public String addUserGroupForResult(String groupname,String describe,String ids) throws Exception{
        AdmusergroupDTO usergroupDTO = new AdmusergroupDTO();
        usergroupDTO.setGroupname(groupname);
        usergroupDTO.setDes(describe);
        usergroupDTO.setIsdel(1);
        int num = admUserGroupRepository.addUserGroup(usergroupDTO);

        if (num < 1) {
            throw new ServiceException(CmsError.Error.ADD_OR_MODIFY_GROUP_FAILURE);
        }
        int[] gids;
        try {
            gids = StringUtil.strArrToIntArr(ids);
        } catch (Exception e) {
            throw new ServiceException(CmsError.Error.ADD_OR_MODIFY_GROUP_FAILURE);
        }
        long newid = admUserGroupRepository.findnewUGroupDTO(usergroupDTO);

        List<GroupRoleRelDTO> list = groupRoleRelList(gids,newid);
        num = groupRoleRelRepository.addUserGroupRels(list);
        if (num < 1) {
            throw new ServiceException(CmsError.Error.AUTHORITY_GROUP_FAILURE);
        }
        return "success";
    }


    public String modifyUserGroupForResult(long id,String groupname,String describe,String ids) throws Exception {
        AdmusergroupDTO usergroupDTO = new AdmusergroupDTO();
        usergroupDTO.setId(id);
        usergroupDTO.setGroupname(groupname);
        usergroupDTO.setDes(describe);
        usergroupDTO.setIsdel(1);
        int num = admUserGroupRepository.updateUserGroup(usergroupDTO);
        if (num < 1) {
            throw new ServiceException(CmsError.Error.ADD_OR_MODIFY_GROUP_FAILURE);
        }

        int[] gids;
        try {
            gids = StringUtil.strArrToIntArr(ids);
        } catch (Exception e) {
            throw new ServiceException(CmsError.Error.ADD_OR_MODIFY_GROUP_FAILURE);
        }
        groupRoleRelRepository.delGroupRoleRel(id);
        List<GroupRoleRelDTO> list = groupRoleRelList(gids,id);
        groupRoleRelRepository.addUserGroupRels(list);
        return "success";
    }

    public List<GroupRoleRelDTO> groupRoleRelList(int[] gids, long id) throws Exception{
        List<GroupRoleRelDTO> list = new ArrayList<GroupRoleRelDTO>();
        for (int i = 0; i < gids.length; i++) {
            long gid = gids[i];
            GroupRoleRelDTO groupRoleRelDTO = new GroupRoleRelDTO();
            groupRoleRelDTO.setIsdel(1);
            groupRoleRelDTO.setGroupid(id);
            groupRoleRelDTO.setRoleid(gid);
            list.add(groupRoleRelDTO);
        }
        return list;
    }

    public String delUserGroupForResult(String ids) throws Exception{
        int[] gids;
        try {
            gids = StringUtil.strArrToIntArr(ids);
        } catch (Exception e) {
            throw new ServiceException(CmsError.Error.DEL_GROUP_FAILURE);
        }
        int num=0;
        num = admUserGroupRepository.delUserGroup(gids);
        if (num < 1) {
            throw new ServiceException(CmsError.Error.DEL_GROUP_FAILURE);
        }
        num = groupRoleRelRepository.delGRoleRelList(gids);
        if (num < 1) {
            throw new ServiceException(CmsError.Error.DEL_GROUP_FAILURE);
        }
        return "success";
    }

    public List<RoleTreeDTO> getRoleTreeList(String admgroupuserid) throws Exception{
        List<RoleTreeDTO> list =null;
        List<Long> roleids =null;
        try {
            long groupId = Long.parseLong(admgroupuserid);
            roleids = groupRoleRelRepository.findroleid(groupId);
        }catch (Exception e){
        }

        Map<String, Boolean> map = new HashMap<String, Boolean>();
        if(roleids!=null&&roleids.size()>0){

            for (Long roleid : roleids) {
                map.put(roleid + "", true);
            }
        }
        list = roleRepository.listtree(null,false);
        for (int i = 0, sies = list.size(); i < sies; i++) {
            if(map.containsKey(list.get(i).getId()))
                list.get(i).setChecked(true);
        }
        return list;
    }

    public List<RoleTreeDTO> loadtreewithoutrootList() throws Exception{
        List<RoleTreeDTO> list =null;
        list = roleRepository.listtree(null,false);
        return list;
    }




}
