package org.itachi.cms.service;

import org.itachi.cms.dto.*;
import org.itachi.cms.error.CmsError;
import org.itachi.cms.exception.ServiceException;
import org.itachi.cms.repository.AdminUserGroupCheckRepository;
import org.itachi.cms.repository.AdminUserGroupRepository;
import org.itachi.cms.repository.AdminUserRepository;
import org.itachi.cms.repository.UserGroupRelRepository;
import org.itachi.cms.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thinkpad on 2017-05-05 .
 */

@Component
public class AdminUserService {
    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private UserGroupRelRepository userGroupRelpository;

    @Autowired
    private AdminUserGroupRepository adminUserGroupRepository;

    @Autowired
    private AdminUserGroupCheckRepository groupCheckRepository;

    public Map<String, Object> gridlist(Map<String, Object> map) throws Exception {
        AdminUserDTO userDTO = (AdminUserDTO)map.get("userDTO");
        PagerDTO pager = (PagerDTO) map.get("pager");
        map.put("rows", adminUserRepository.getUserList(userDTO, pager));
        map.put("total", adminUserRepository.getUserCount(userDTO));
        return map;
    }

    public String addAdminUser(AdminUserDTO dto) throws Exception{
        validateForm(dto);

        dto.setAccount(dto.getAccount());
        int count = adminUserRepository.getUserCount(dto);
        if (count > 0) {
            throw new ServiceException(CmsError.Error.USER_EXISTS);
        } else {
            dto.setPassword(dto.getPassword());
            dto.setName(dto.getName());
            dto.setPhone(dto.getPhone());
            dto.setMail(dto.getMail());
            dto.setIsdel(1);
            dto.setDepartment(dto.getDepartment());
            adminUserRepository.addUser(dto);
            AdminUserDTO admuser = adminUserRepository.getUser(dto.getAccount());
            int[] gids;
            try {
                gids = StringUtil.strArrToIntArr(dto.getGroupids());
            } catch (Exception e) {
                throw new ServiceException(CmsError.Error.AUTHORITY_GROUP_FAILURE);
            }
            List<UserGroupRelDTO> list = new ArrayList<UserGroupRelDTO>();
            for (int i = 0; i < gids.length; i++) {
                UserGroupRelDTO userGroupRelDTO = new UserGroupRelDTO();
                userGroupRelDTO.setIsdel(1);
                userGroupRelDTO.setUserid(admuser.getId());
                int l = gids[i];
                userGroupRelDTO.setGroupid((long) l);
                list.add(userGroupRelDTO);
            }
            userGroupRelpository.addUserGroupRels(list);
        }
        return "success";
    }


    /**
     * 校驗參數
     * @param dto
     * @return
     * @throws Exception
     */
    public String validateForm(AdminUserDTO dto) throws Exception{
        if (dto.getId() == null || dto.getId() < 1L) {
            throw new ServiceException(CmsError.Error.ID_EMPTY);
        }

        if (!StringUtil.isNotNull(dto.getAccount())) {
            return "账号不能为空";
        }
        if (!StringUtil.isMaxSize(dto.getAccount(), 20)) {
            return "账号长度不能超过20个字符";
        }
        if (!StringUtil.isNotNull(dto.getMail())) {
            return "邮箱不能为空";
        }
        if (!StringUtil.isMaxSize(dto.getMail(), 50)) {
            return "账号长度不能超过20个字符";
        }
        if (!StringUtil.isEmail(dto.getMail())) {
            return "邮箱格式错误";
        }
        if (!StringUtil.isNotNull(dto.getName())) {
            return "姓名不能为空";
        }
        if (!StringUtil.isMaxSize(dto.getName(), 20)) {
            return "姓名长度不能超过20个字符";
        }
        if (!StringUtil.isNotNull(dto.getPhone())) {
            return "手机号码不能为空";
        }
        if (!StringUtil.isMobile(dto.getPhone())) {
            return "手机号码格式错误";
        }
        if (!StringUtil.isNotNull(dto.getDepartment())) {
            return "部门不能为空";
        }
        if (!StringUtil.isMaxSize(dto.getDepartment(), 20)) {
            return "部门长度不能超过20个字符";
        }
        if (!StringUtil.isNotNull(dto.getPassword())) {
            return "密码不能为空";
        }
        if (!StringUtil.isMaxSize(dto.getPassword(), 20)) {
            return "密码长度不能超过20个字符";
        }
        if (!StringUtil.MinSize(dto.getGroupids())) {
            return "组信息请至少选择一个";
        }
        return "";
    }


    public AdminUserDTO getUserByAccount(AdminUserDTO amduserDTO) throws Exception {
        return adminUserRepository.getUserByAccount(amduserDTO);
    }

    public AdminUserDTO login(String account, String password) throws Exception {
        // 首先判断账号合法，字符串必须符合对应的值，否则不需要再去数据库里面找了，浪费时间和资源
        if (account.trim().isEmpty() || account.length() > 15) {
            throw new ServiceException(CmsError.Error.ACCOUNT_INVALID);
        }
        // 密码也是一样，且抛出的提示均是非常简洁明了的
        if (password.length() < 6 || password.length() > 16) {
            throw new ServiceException(CmsError.Error.PASSWORD_INVALID);
        }
        AdminUserDTO adminUserDTO = new AdminUserDTO();
        adminUserDTO.setAccount(account);
        adminUserDTO.setPassword(password);
        AdminUserDTO userDTO = adminUserRepository.getUserByAccount(adminUserDTO);
        // 对应的账号密码在库中找不到，这个时候对象不一定是null，可能是所有值都是空的一个对象
        if (userDTO == null || userDTO.getId() == null || userDTO.getId() < 1L) {
            throw new ServiceException(CmsError.Error.USER_NOT_EXISTS);
        }
        return userDTO;
    }
    public AdminUserDTO getUserById(long id) throws Exception {
        return adminUserRepository.getUserById(id);
    }

    public String modifyyadmuser(AdminUserDTO dto) throws Exception{
        validateForm(dto);

        /*
        dto.setId(dto.getId());
        dto.setAccount(dto.getAccount());
        dto.setPassword(dto.getPassword());
        dto.setName(dto.getName());
        dto.setPhone(dto.getPhone());
        dto.setMail(dto.getMail());

        dto.setDepartment(dto.getDepartment());
        */

        // dto.setId(1L);
        dto.setIsdel(1);
        int code = adminUserRepository.updateUser(dto);
        if (code < 1) {
            return "修改失败";
        } else {
            userGroupRelpository.updateUserGroupRel(dto.getId());
            int[] gids;
            try {
                gids = StringUtil.strArrToIntArr(dto.getGroupids());
            } catch (Exception e) {
                return "出现异常，部分权限添加失败，请补充添加权限。";
            }
            List<UserGroupRelDTO> list = new ArrayList<UserGroupRelDTO>();
            for (int i = 0; i < gids.length; i++) {
                UserGroupRelDTO userGroupRelDTO = new UserGroupRelDTO();
                userGroupRelDTO.setIsdel(1);
                userGroupRelDTO.setUserid(dto.getId());
                int l = gids[i];
                userGroupRelDTO.setGroupid((long) l);
                list.add(userGroupRelDTO);
            }
            code = userGroupRelpository.addUserGroupRels(list);
            if (code < 1) {
                return "出现异常，部分权限添加失败，请补充添加权限。";
            }
        }
        return "success";
    }
    public Map<String, Object> gridgrouplist(long admUserId,String groupname,int page,int rows) throws Exception {


        PagerDTO pager = new PagerDTO(page, rows);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pager", pager);
        map.put("groupname", groupname);
        Map<String, Object> UserGroupMap = adminUserGroupRepository.findAdmUserGroup(map);
        List<AdmusergroupDTO> usergroupList = (List<AdmusergroupDTO>) UserGroupMap.get("rows");
        Map<String, Boolean> bolmap = groupCheckRepository.getAllCheckGroup(admUserId);
        List<AdmusergroupcheckDTO> groupChecks = new ArrayList<AdmusergroupcheckDTO>();
        for (int i = 0; i < usergroupList.size(); i++) {
            AdmusergroupcheckDTO groupcheckDTO = new AdmusergroupcheckDTO();
            groupcheckDTO.setId(usergroupList.get(i).getId());
            groupcheckDTO.setGroupname(usergroupList.get(i).getGroupname());
            groupcheckDTO.setDes(usergroupList.get(i).getDes());
            groupcheckDTO.setCreatetime(usergroupList.get(i).getCreatetime());
            groupcheckDTO.setUpdatetime(usergroupList.get(i).getUpdatetime());
            groupcheckDTO.setIsdel(usergroupList.get(i).getIsdel());
            String idkey = usergroupList.get(i).getId() + "";
            if (bolmap.containsKey(idkey)) {
                groupcheckDTO.setCheck(bolmap.get(idkey));
            } else {
                groupcheckDTO.setCheck(false);
            }

            groupChecks.add(groupcheckDTO);
        }
        Map<String, Object> result = new HashMap<String, Object>();

        result.put("rows", groupChecks);
        result.put("total", UserGroupMap.get("total"));

        return result;

    }

    public Map<String, Object> findAdmUserGroup(Map<String, Object> map) throws Exception {
        return adminUserGroupRepository.findAdmUserGroup(map);
    }
    public String deleteUserDTO(int[] uids){
        return adminUserRepository.deleteUserDTO(uids);
    }


    }