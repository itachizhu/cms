package org.itachi.cms.repository;

import org.itachi.cms.dto.AdmuserDTO;
import org.itachi.cms.dto.RoleDTO;
import org.itachi.cms.dto.RoleTreeDTO;
import org.itachi.cms.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleRepository {
    @Autowired
    private RoleMapper roleMapper;

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int addRoleDTO(RoleDTO roleDTO) {
        return roleMapper.addRoleDTO(roleDTO);
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public String deleteRoleDTO(int[] roleids) {
        int count = roleMapper.countRolesByPid(roleids);
        if (count > 0) {
            return "不能删除有子节点的权限，请先删除所有子节点！";
        }
        int deleteRole = roleMapper.deleteRoleDTO(roleids);
        if (deleteRole < 1) {
            return "删除失败！";
        }

        return "success";

    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly = false)
    public int updateRoleDTO(RoleDTO roleDTO) {
        return roleMapper.updateRoleDTO(roleDTO);
    }

    public RoleDTO findRole(long id) {
        return roleMapper.findRole(id);
    }

    public List<RoleTreeDTO> loadMenu(AdmuserDTO admuserDTO) throws Exception {
        List<RoleTreeDTO> roleList = new ArrayList<RoleTreeDTO>();
        if (this.isAdminUser(admuserDTO)) {
            roleList = roleMapper.getAdminRole();
            if (roleList.isEmpty()) {
                return roleList;
            }
        } else {
            roleList = roleMapper.getRoleTree(admuserDTO);
            if (roleList.isEmpty()) {
                return roleList;
            }

        }
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        for (RoleTreeDTO roleDTO : roleList) {
            map.put(roleDTO.getpId() + "", true);
        }

        for (int i = 0; i < roleList.size(); i++) {
            String roleurl = roleList.get(i).getRoleurl();
            String id = String.valueOf(roleList.get(i).getId());
            if (map.containsKey(id) && map.get(id)) {
                roleList.get(i).setOpen(true);
                roleList.get(i).setClick("");
                continue;
            }

            if (roleurl != null || roleurl.length() > 0) {
                String click = "click: addTab('" + roleList.get(i).getName() + "','" + roleList.get(i).getRoleurl() + "')";
                roleList.get(i).setClick(click);
            }
        }

        return roleList;
    }


    public Boolean isAdminUser(AdmuserDTO admuserDTO) throws Exception {
        boolean flag = false;
        List<Long> list = roleMapper.isAdmin(admuserDTO);
        if (list.isEmpty()) {
            return flag;
        }
        for (int i = 0; i < list.size(); i++) {
            long id = list.get(i);
            if (id == 1) {
                return true;
            }

        }
        return flag;
    }


    public Map<String, Object> gridlist(int roleid,String roleName,String roleUrl, int begin, int rows) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleid", roleid);
        map.put("roleName", roleName);
        map.put("roleUrl", roleUrl);
        map.put("begin", begin);
        map.put("rows", rows);

        Map<String, Object> result = new HashMap<String, Object>();

        List<RoleDTO> list = new ArrayList<>();
        int count = roleMapper.countRole(map);
        if (count < 1) {
            result.put("total", 0);
            result.put("rows", list);
            return result;
        }

        list = roleMapper.getRoles(map);
        result.put("total", count);
        result.put("rows", list);

        return result;
    }

    public List<RoleTreeDTO> listtree(AdmuserDTO userDTO, boolean bool) throws Exception {

        List<RoleTreeDTO> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("needRoot", bool);
        list = roleMapper.listTree(map);

        for (int i = 0, sies = list.size(); i < sies; i++) {
            if (bool) {
                if (list.get(i).getpId() != null && list.get(i).getpId() == 0) {
                    list.get(i).setOpen(true);
                }

                if (list.get(i).getId() == userDTO.getId()) {
                    list.get(i).setOpen(true);
                }
            } else {
                if (list.get(i).getpId() != null && list.get(i).getpId() == 0) {
                    list.get(i).setOpen(true);
                }
            }


        }

        return list;
    }


}
