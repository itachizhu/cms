package org.itachi.cms.action;

import org.glassfish.jersey.server.mvc.Viewable;
import org.itachi.cms.dto.AdmusergroupDTO;
import org.itachi.cms.dto.GroupRoleRelDTO;
import org.itachi.cms.dto.PagerDTO;
import org.itachi.cms.dto.RoleTreeDTO;
import org.itachi.cms.repository.AdmUserGroupRepository;
import org.itachi.cms.repository.GroupRoleRelRepository;
import org.itachi.cms.repository.RoleRepository;
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
@Path("/admusergroup")
public class AdmUserGroupAction extends BaseAction {

    @Autowired
    private AdmUserGroupRepository admUserGroupRepository;
    @Autowired
    private GroupRoleRelRepository groupRoleRelRepository;
    @Autowired
    private RoleRepository roleRepository;
    /**
     * 进入管理员组管理页面
     */
    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public Viewable list() throws Exception {
        return new Viewable("/admusergroup/admUserGroupList");
    }


    /**
     * 获取管理员组列表数据
     */
    @POST
    @Path("/gridlist")
    @Consumes("application/x-www-form-urlencoded")
    public Map<String, Object> gridlist(@FormParam("groupName") String groupName,
                                        @FormParam("page") int page,
                                        @FormParam("rows") int rows) throws Exception {
        PagerDTO pager = new PagerDTO(page, rows);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pager", pager);
        map.put("groupname", groupName);


        Map<String, Object> result = admUserGroupRepository.findAdmUserGroup(map);

        return result;
    }


    /**
     * 进入添加页面
     */
    @GET
    @Path("/toadd")
    @Produces(MediaType.TEXT_HTML)
    public Viewable toadd() throws Exception {
        return new Viewable("/admusergroup/addAdmusergroup");
    }


    /**
     * 添加管理员组
     */
    @POST
    @Path("/addadmusergroup")
    @Consumes("application/x-www-form-urlencoded")
    public String addadmusergroup(@FormParam("groupname") String groupname,
                                  @FormParam("describe") String describe,
                                  @FormParam("ids") String ids) throws Exception {

        if (!StringUtil.isNotNull(groupname)) {
            return "管理员组名称不能为空";
        }

        if (!StringUtil.isMaxSize(groupname, 20)) {
            return "管理员组名称长度不能超过20个字符";
        }

        if (!StringUtil.isNotNull(describe)) {
            return "描述信息不能为空";
        }

        if (!StringUtil.isMaxSize(describe, 50)) {
            return "权限名称长度不能超过50个字符";
        }
        if (!StringUtil.MinSize(ids)) {
            return "权限请至少选择一个";
        }
        AdmusergroupDTO usergroupDTO = new AdmusergroupDTO();
        usergroupDTO.setGroupname(groupname);
        usergroupDTO.setDes(describe);
        usergroupDTO.setIsdel(1);
        int num = admUserGroupRepository.addUserGroup(usergroupDTO);

        if (num < 1) {
            return "添加失败";
        }
        int[] gids;
        try {
            gids = StringUtil.strArrToIntArr(ids);
        } catch (Exception e) {
            return "ids不是数字,添加管理员组失败。";
        }
        long newid = admUserGroupRepository.findnewUGroupDTO(usergroupDTO);

        List<GroupRoleRelDTO> list = new ArrayList<GroupRoleRelDTO>();
        for (int i = 0; i < gids.length; i++) {
            long gid = gids[i];
            GroupRoleRelDTO groupRoleRelDTO = new GroupRoleRelDTO();
            groupRoleRelDTO.setIsdel(1);
            groupRoleRelDTO.setGroupid(newid);
            groupRoleRelDTO.setRoleid(gid);


            list.add(groupRoleRelDTO);
        }
        num = groupRoleRelRepository.addUserGroupRels(list);
        if (num < 1) {
            return "出现异常，部分权限添加失败，请补充添加权限。";
        }
        return SUCCESS;
    }


    /**
     * 进入修改管理员组页面
     */

    @GET
    @Path("/tomodify")
    @Produces(MediaType.TEXT_HTML)
    public Viewable tomodify(@QueryParam("admusergroupid") long id) throws Exception {

        AdmusergroupDTO admusergroupDTO = admUserGroupRepository.admusergroupById(id);
        request.setAttribute("admusergroup", admusergroupDTO);

        return new Viewable("/admusergroup/modifyAdmusergroup");
    }


    /**
     * 修改管理员组
     */
    @POST
    @Path("/modifyadmusergroup")
    @Consumes("application/x-www-form-urlencoded")
    public String modifyadmusergroup(@FormParam("id") long id,
                                     @FormParam("groupname") String groupname,
                                     @FormParam("describe") String describe,
                                     @FormParam("ids") String ids) throws Exception {

        if (!StringUtil.isNotNull(groupname)) {
            return "管理员组名称不能为空";
        }

        if (!StringUtil.isMaxSize(groupname, 20)) {
            return "管理员组名称长度不能超过20个字符";
        }

        if (!StringUtil.isNotNull(describe)) {
            return "描述信息不能为空";
        }

        if (!StringUtil.isMaxSize(describe, 50)) {
            return "权限名称长度不能超过50个字符";
        }
        if (!StringUtil.MinSize(ids)) {
            return "权限请至少选择一个";
        }
        AdmusergroupDTO usergroupDTO = new AdmusergroupDTO();
        usergroupDTO.setId(id);
        usergroupDTO.setGroupname(groupname);
        usergroupDTO.setDes(describe);
        usergroupDTO.setIsdel(1);
        int num = admUserGroupRepository.updateUserGroup(usergroupDTO);
        if (num < 1) {
            return "修改失败";
        }

        int[] gids;
        try {
            gids = StringUtil.strArrToIntArr(ids);
        } catch (Exception e) {
            return "ids不是数字,添加管理员组失败。";
        }

        groupRoleRelRepository.delGroupRoleRel(id);


        List<GroupRoleRelDTO> list = new ArrayList<GroupRoleRelDTO>();
        for (int i = 0; i < gids.length; i++) {
            long gid = gids[i];
            GroupRoleRelDTO groupRoleRelDTO = new GroupRoleRelDTO();
            groupRoleRelDTO.setIsdel(1);
            groupRoleRelDTO.setGroupid(id);
            groupRoleRelDTO.setRoleid(gid);


            list.add(groupRoleRelDTO);
        }

       groupRoleRelRepository.addUserGroupRels(list);

        return SUCCESS;
    }

    /**
     * 删除管理员组
     */
    @POST
    @Path("/delete")
    @Consumes("application/x-www-form-urlencoded")
    public String delete(@FormParam("ids") String ids) throws Exception {
        int[] gids;
        try {
            gids = StringUtil.strArrToIntArr(ids);
        } catch (Exception e) {
            return "ids不是数字,删除管理员组失败。";
        }
        int num=0;
        num = admUserGroupRepository.delUserGroup(gids);
        if (num < 1) {
            return "删除失败";
        }
        num = groupRoleRelRepository.delGRoleRelList(gids);
        if (num < 1) {
            return "删除失败";
        }
        return SUCCESS;
    }


    /**
     * 加载权限树(用于添加管理员组的时候选择权限)
     */
    @POST
    @Path("/loadtreewithoutroot")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<RoleTreeDTO> loadtreewithoutroot() throws Exception {

        List<RoleTreeDTO> list =null;

            list = roleRepository.listtree(null,false);
        return list;
    }



    /**
     * 加载权限树(用于修改管理员组的时候选择权限-添加时选择的权限在修改的时候需要选中)
     */
    @POST
    @Path("/loadtreechecked")
    @Consumes("application/x-www-form-urlencoded")
    public List<RoleTreeDTO> loadtreechecked(@FormParam("admgroupuserid") long admgroupuserid) throws Exception {
        List<RoleTreeDTO> list =null;
        List<Long> roleids =null;
        try {
             roleids = groupRoleRelRepository.findroleid(admgroupuserid);
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
   /* func (this *AdmUserGroupController) Loadtreechecked() {
        admgroupuserid, _ := this.GetInt64("admgroupuserid")
        roleIdMap := service.AdmUserGroupService.GetAllRoleByGroupId(admgroupuserid)
        //查询树结构不加载root节点
        roles := service.RoleService.Listtree(false)
        if roleIdMap == nil {
            //展开一级目录
            for i, role := range roles {
                if role.Pid == 0 {
                    roles[i].Open = true
                }
            }
        } else {
            for i, role := range roles {
                if role.Pid == 0 {
                    roles[i].Open = true
                }
                if _, ok := roleIdMap[role.Id]; ok {
                    roles[i].Checked = true
                }
            }
        }
        this.jsonResult(roles)
    }*/
}
