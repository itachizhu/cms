package org.itachi.cms.action;

import org.glassfish.jersey.server.mvc.Viewable;
import org.itachi.cms.dto.*;
import org.itachi.cms.repository.AdminUserGroupCheckRepository;
import org.itachi.cms.repository.AdminUserGroupRepository;
import org.itachi.cms.repository.AdminUserRepository;
import org.itachi.cms.repository.UserGroupRelRepository;
import org.itachi.cms.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 20:01
 */
@Path("/admuser")
public class AdmUserAction extends BaseAction {


    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private UserGroupRelRepository userGroupRelpository;

    @Autowired
    private AdminUserGroupRepository adminUserGroupRepository;

    @Autowired
    private AdminUserGroupCheckRepository groupCheckRepository;

    /**
     * 进入管理员列表页面
     */
    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public Viewable list() throws Exception {
        return new Viewable("/admUser/admUserList");
    }


    /**
     * 获取分页展示数据
     */
    @POST
    @Path("/gridlist")
    @Consumes("application/x-www-form-urlencoded")
    public Map<String, Object> gridlist(@FormParam("admusermail") String admusermail,
                                        @FormParam("admuserphone") String admuserphone,
                                        @FormParam("admusername") String admusername,
                                        @FormParam("admuserid") Long admuserid,
                                        @FormParam("account") String account,
                                        @FormParam("page") int page,
                                        @FormParam("rows") int rows) throws Exception {

        AdminUserDTO userDTO = new AdminUserDTO();
        userDTO.setId(admuserid);
        userDTO.setAccount(account);
        userDTO.setMail(admusermail);
        userDTO.setPhone(admuserphone);
        userDTO.setName(admusername);
        PagerDTO pager = new PagerDTO(page, rows);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", adminUserRepository.getUserList(userDTO, pager));
        map.put("total", adminUserRepository.getUserCount(userDTO));
        return map;
    }


    /**
     * 进入添加页面
     */
    @GET
    @Path("/toaddadmuser")
    @Produces(MediaType.TEXT_HTML)
    public Viewable toaddadmuser() throws Exception {
        return new Viewable("/admUser/addAdmUser");
    }


    /**
     * 添加管理员
     */
    @POST
    @Path("/addadmuser")
    @Consumes("application/x-www-form-urlencoded")
    public String addadmuser(
            @FormParam("account") String account,
            @FormParam("mail") String mail,
            @FormParam("name") String name,
            @FormParam("phone") String phone,
            @FormParam("department") String department,
            @FormParam("password") String password,
            @FormParam("ids") String ids) throws Exception {


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
        AdminUserDTO admuserDTO = new AdminUserDTO();
        admuserDTO.setAccount(account);
        int count = adminUserRepository.getUserCount(admuserDTO);
        if (count > 0) {
            return "添加失败,账号已经存在!";
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
                return "ids不是数字，部分权限添加失败，请补充添加权限。";
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


        return SUCCESS;
    }


    /**
     * 进入修改页面
     */
    @GET
    @Path("/tomodifyadmuser")
    @Produces(MediaType.TEXT_HTML)
    public Viewable tomodifyadmuser(@QueryParam("admUserId") long id) throws Exception {
        AdminUserDTO admuserDTO = adminUserRepository.getUserById(id);
        request.setAttribute("admuser", admuserDTO);
        return new Viewable("/admUser/modifyAdmUser");
    }


    /**
     * 修改管理员
     */
    @POST
    @Path("/modifyyadmuser")
    @Consumes("application/x-www-form-urlencoded")
    public String modifyyadmuser(
            @FormParam("userId") long userId,
            @FormParam("account") String account,
            @FormParam("mail") String mail,
            @FormParam("name") String name,
            @FormParam("phone") String phone,
            @FormParam("department") String department,
            @FormParam("password") String password,
            @FormParam("groupids") String groupids
    ) throws Exception {

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

        if (!StringUtil.MinSize(groupids)) {
            return "组信息请至少选择一个";
        }
        if (password.length() > 0) {


            if (!StringUtil.isNotNull(password)) {
                return "密码不能为空";
            }
            if (!StringUtil.isMaxSize(password, 20)) {
                return "密码长度不能超过20个字符";
            }
        }
        AdminUserDTO admuserDTO = new AdminUserDTO();
        admuserDTO.setId(userId);
        admuserDTO.setAccount(account);
        admuserDTO.setPassword(password);
        admuserDTO.setName(name);
        admuserDTO.setPhone(phone);
        admuserDTO.setMail(mail);
        admuserDTO.setIsdel(1);
        admuserDTO.setDepartment(department);
        int code = adminUserRepository.updateUser(admuserDTO);
        if (code < 1) {
            return "修改失败";
        } else {
            userGroupRelpository.updateUserGroupRel(userId);

            int[] gids;
            try {
                gids = StringUtil.strArrToIntArr(groupids);
            } catch (Exception e) {
                return "出现异常，部分权限添加失败，请补充添加权限。";
            }

            List<UserGroupRelDTO> list = new ArrayList<UserGroupRelDTO>();
            for (int i = 0; i < gids.length; i++) {
                UserGroupRelDTO userGroupRelDTO = new UserGroupRelDTO();
                userGroupRelDTO.setIsdel(1);
                userGroupRelDTO.setUserid(userId);
                int l = gids[i];
                userGroupRelDTO.setGroupid((long) l);
                list.add(userGroupRelDTO);
            }
            code = userGroupRelpository.addUserGroupRels(list);
            if (code < 1) {
                return "出现异常，部分权限添加失败，请补充添加权限。";
            }
        }
        return SUCCESS;
    }

    /**
     * 删除
     */
    @POST
    @Path("/delete")
    @Consumes("application/x-www-form-urlencoded")
    public String delete(@FormParam("userids") String userids) throws Exception {

        int[] uids = StringUtil.strArrToIntArr(userids);
        String result = adminUserRepository.deleteUserDTO(uids);
        if (result != null) {
            return result;
        }

        return SUCCESS;
    }


    /**
     * 获取管理员组列表数据
     * 修改管理员的时候需要加载管理员组列表，并且设置已经选择的权限为选中状态
     */
    @POST
    @Path("/gridgrouplist")
    @Consumes("application/x-www-form-urlencoded")
    public Map<String, Object> gridgrouplist(@FormParam("admUserId") long admUserId,
                                             @FormParam("groupName") String groupName,
                                             @FormParam("page") int page,
                                             @FormParam("rows") int rows) throws Exception {


        PagerDTO pager = new PagerDTO(page, rows);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pager", pager);
        map.put("groupName", groupName);
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

}
