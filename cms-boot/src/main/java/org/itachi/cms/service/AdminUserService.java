package org.itachi.cms.service;

import org.itachi.cms.bean.AdminUserParamBean;
import org.itachi.cms.beans.AdminUserBean;
import org.itachi.cms.dto.*;
import org.itachi.cms.error.CmsError;
import org.itachi.cms.exception.ServiceException;
import org.itachi.cms.repository.AdminUserGroupCheckRepository;
import org.itachi.cms.repository.AdminUserGroupRepository;
import org.itachi.cms.repository.AdminUserRepository;
import org.itachi.cms.repository.UserGroupRelRepository;
import org.itachi.cms.util.StringUtil;
import org.itachi.cms.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    public Map<String, Object> gridlist(AdminUserParamBean adminUserParamBean,int page ,int rows) throws Exception {
        AdminUserDTO userDTO = Utils.adminUserForm(adminUserParamBean);
        PagerDTO pager = new PagerDTO(page, rows);
        Map<String, Object> map = new HashMap<>();
        map.put("rows", adminUserRepository.getUserList(userDTO, pager));
        map.put("total", adminUserRepository.getUserCount(userDTO));
        return map;
    }
    public String addAdminUser(String account,String mail,String name,String phone,String department,String password,String ids) throws Exception{
        validateForm(account,mail,name,phone,department,password,ids);
        AdminUserDTO admuserDTO = new AdminUserDTO();
        admuserDTO.setAccount(account);
        int count = adminUserRepository.getUserCount(admuserDTO);
        if (count > 0) {
            throw new ServiceException(CmsError.Error.USER_EXISTS);
        } else {
            admuserDTO.setPassword(password);
            admuserDTO.setName(name);
            admuserDTO.setPhone(phone);
            admuserDTO.setMail(mail);
            admuserDTO.setIsdel(1);
            admuserDTO.setDepartment(department);
            adminUserRepository.addUser(admuserDTO);
            AdminUserDTO admuser = adminUserRepository.getUser(account);
            int[] gids;
            try {
                gids = StringUtil.strArrToIntArr(ids);
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
     * 校验提交的form数据
     * @param account
     * @param mail
     * @param name
     * @param phone
     * @param department
     * @param password
     * @param ids
     * @return
     * @throws Exception
     */
    public String validateForm(String account,String mail,String name,String phone,String department,String password,String ids) throws Exception{
        if (!StringUtil.isNotNull(account)) {
            return "账号不能为空";
        }
        if (!StringUtil.isMaxSize(account, 20)) {
            return "账号长度不能超过20个字符";
        }
        if (!StringUtil.isNotNull(mail)) {
            return "邮箱不能为空";
        }
        if (!StringUtil.isMaxSize(mail, 50)) {
            return "账号长度不能超过20个字符";
        }
        if (!StringUtil.isEmail(mail)) {
            return "邮箱格式错误";
        }
        if (!StringUtil.isNotNull(name)) {
            return "姓名不能为空";
        }
        if (!StringUtil.isMaxSize(name, 20)) {
            return "姓名长度不能超过20个字符";
        }
        if (!StringUtil.isNotNull(phone)) {
            return "手机号码不能为空";
        }
        if (!StringUtil.isMobile(phone)) {
            return "手机号码格式错误";
        }
        if (!StringUtil.isNotNull(department)) {
            return "部门不能为空";
        }
        if (!StringUtil.isMaxSize(department, 20)) {
            return "部门长度不能超过20个字符";
        }
        if (!StringUtil.isNotNull(password)) {
            return "密码不能为空";
        }
        if (!StringUtil.isMaxSize(password, 20)) {
            return "密码长度不能超过20个字符";
        }
        if (!StringUtil.MinSize(ids)) {
            return "组信息请至少选择一个";
        }
        return "";
    }
    /**
     * 校驗參數
     * @param dto
     * @return
     * @throws Exception
     */
    public String validateForm(AdminUserBean dto) throws Exception{
        if (dto.getId() == null || dto.getId() < 1L) {
            throw new ServiceException(CmsError.Error.ID_EMPTY);
        }
        if (dto.getAccount().equals(null) || dto.getAccount().isEmpty()) {
            throw new ServiceException(CmsError.Error.ACCOUNT_EMPTY);
        }
        if (dto.getMail().equals(null) || dto.getMail().isEmpty()) {
            throw new ServiceException(CmsError.Error.MAIL_EMPTY);
        }
        if (dto.getName().equals(null) || dto.getName().isEmpty()) {
            throw new ServiceException(CmsError.Error.NAME_EMPTY);
        }
        if (dto.getPhone().equals(null) || dto.getPhone().isEmpty()) {
            throw new ServiceException(CmsError.Error.PHONE_EMPTY);
        }
        if (dto.getDepartment().equals(null) || dto.getDepartment().isEmpty()) {
            throw new ServiceException(CmsError.Error.DEPARTMENT_EMPTY);
        }
        if (dto.getGroupids().equals(null) || dto.getGroupids().isEmpty()) {
            throw new ServiceException(CmsError.Error.GROUP_EMPTY);
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

    public String modifyyadmuser(AdminUserBean bean) throws Exception{
        validateForm(bean);

        AdminUserDTO dto = new AdminUserDTO();
        dto.setId(bean.getId());
        dto.setAccount(bean.getAccount());
        dto.setPassword(bean.getPassword());
        dto.setName(bean.getName());
        dto.setPhone(bean.getPhone());
        dto.setMail(bean.getMail());
        dto.setDepartment(bean.getDepartment());
        dto.setIsdel(1);
        int code = adminUserRepository.updateUser(dto);
        if (code < 1) {
            throw new ServiceException(CmsError.Error.MODIFY_FAILED);
        } else {
            userGroupRelpository.updateUserGroupRel(bean.getId());
            int[] gids;
            try {
                gids = StringUtil.strArrToIntArr(bean.getGroupids());
            } catch (Exception e) {
                throw new ServiceException(CmsError.Error.AUTHORITY_GROUP_FAILURE);
            }
            List<UserGroupRelDTO> list = new ArrayList<UserGroupRelDTO>();
            for (int i = 0; i < gids.length; i++) {
                UserGroupRelDTO userGroupRelDTO = new UserGroupRelDTO();
                userGroupRelDTO.setIsdel(1);
                userGroupRelDTO.setUserid(bean.getId());
                int l = gids[i];
                userGroupRelDTO.setGroupid((long) l);
                list.add(userGroupRelDTO);
            }
            code = userGroupRelpository.addUserGroupRels(list);
            if (code < 1) {
                throw new ServiceException(CmsError.Error.AUTHORITY_GROUP_FAILURE);
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


    /**
     * 进入添加页面
     */
    @RequestMapping(value = "/toaddadmuser", method = RequestMethod.GET)
    public String addAdmuser() throws Exception {
        return "admUser/addAdmUser";
    }
}