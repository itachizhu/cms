package org.itachi.cms.controller;

import org.itachi.cms.beans.AdminUserBean;
import org.itachi.cms.dto.AdminUserDTO;
import org.itachi.cms.dto.PagerDTO;
import org.itachi.cms.repository.AdminUserGroupCheckRepository;
import org.itachi.cms.repository.AdminUserGroupRepository;
import org.itachi.cms.service.AdminUserService;
import org.itachi.cms.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2017-05-04 .
 */

@Controller
@RequestMapping("/admuser")
public class AdminUserController extends BaseController{

    protected static final String SUCCESS ="success";

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminUserGroupRepository adminUserGroupRepository;
    @Autowired
    private AdminUserGroupCheckRepository groupCheckRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String admuserList() throws Exception {
        return "admUser/admUserList";
    }

    /**
     * 获取分页展示数据
     */
    @ResponseBody
    @RequestMapping(value = "/gridlist", method = RequestMethod.POST)
    public Map<String, Object> gridlist(@RequestParam(value = "admusermail", required = false) String admusermail,
                                        @RequestParam(value = "admuserphone", required = false) String admuserphone,
                                        @RequestParam(value = "admusername", required = false) String admusername,
                                        @RequestParam(value = "admuserid", required = false) Long admuserid,
                                        @RequestParam(value = "admaccout", required = false) String admaccout,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                        @RequestParam(value = "rows", required = false, defaultValue = "10") int rows) throws Exception {

        AdminUserDTO userDTO = new AdminUserDTO();
        userDTO.setId(admuserid);
        userDTO.setAccount(admaccout);
        userDTO.setMail(admusermail);
        userDTO.setPhone(admuserphone);
        userDTO.setName(admusername);
        PagerDTO pager = new PagerDTO(page, rows);
        Map<String, Object> map = new HashMap<>();
        map.put("userDTO",userDTO);
        map.put("pager",pager);
        return adminUserService.gridlist(map);
    }


    /**
     * 进入添加页面
     */
    @RequestMapping(value = "/toaddadmuser", method = RequestMethod.GET)
    public String addAdmuser() throws Exception {
        return "admUser/addAdmUser";
    }

    /**
     * 添加管理员
     */
    @ResponseBody
    @RequestMapping(value = "/addadmuser", method = RequestMethod.POST)
    public String addadmuser(@RequestParam(value = "account", required = false) String account,
                             @RequestParam(value = "mail", required = false) String mail,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "phone", required = false) String phone,
                             @RequestParam(value = "department", required = false) String department,
                             @RequestParam(value = "password", required = false) String password,
                             @RequestParam(value = "ids", required = false) String ids) throws Exception {
        return adminUserService.addAdminUser(account,mail,name,phone,department,password,ids);
    }

    /**
     * 进入修改页面
     */
    @RequestMapping(value = "/tomodifyadmuser", method = RequestMethod.GET)
    public String tomodifyadmuser(Model model,@RequestParam("admUserId") long id) throws Exception {
        AdminUserDTO admuserDTO = adminUserService.getUserById(id);
        model.addAttribute("admuser", admuserDTO);
        return "admUser/modifyAdmUser";
    }


    /**
     * 修改管理员
     */
    @ResponseBody
    @RequestMapping(value = "/modifyyadmuser", method = RequestMethod.POST)
    public String modifyyadmuser(@RequestBody AdminUserBean dto) throws Exception {
        return adminUserService.modifyyadmuser(dto);
    }


    /**
     * 获取管理员组列表数据
     * 修改管理员的时候需要加载管理员组列表，并且设置已经选择的权限为选中状态
     */
    @ResponseBody
    @RequestMapping(value = "/gridgrouplist", method = RequestMethod.POST)
    public Map<String, Object> gridgrouplist(
            @RequestParam("admUserId") long admUserId,
            @RequestParam("groupName") String groupname,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows) throws Exception {
        return adminUserService.gridgrouplist(admUserId,groupname,page,rows);
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("userids") String userids) throws Exception {

        int[] uids = StringUtil.strArrToIntArr(userids);
        String result = adminUserService.deleteUserDTO(uids);
        if (result != null) {
            return result;
        }
        return "success";
    }

}
