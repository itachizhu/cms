<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--添加权限窗口 -->
<div class="easyui-layout" data-options="fit:true">
    <!--基础信息窗口 start-->
    <div data-options="region:'north',split:true" border="false" style="padding:10px 10px 20px 50px">

        <form id="modifyAdmUser">
            <input value="${admuser.id}" type="hidden" name="admUserId"></input>
            <table cellpadding="5">
                <tr>
                    <td>登陆账号:</td>
                    <td><input class="easyui-textbox" value="${admuser.account}" type="text" name="modifyAdmUserAcout" required="true"
                               missingMessage="不能为空"></input>
                    </td>
                    <td>姓名:</td>
                    <td><input class="easyui-textbox" value="${admuser.name}" type="text" name="modifyAdmUserName" required="true"
                               missingMessage="不能为空"></input>
                    </td>
                    <td>手机号:</td>
                    <td><input class="easyui-textbox" value="${admuser.phone}" type="text" name="modifyAdmUserPhone" required="true"
                               missingMessage="不能为空"></input>
                    </td>
                </tr>
                <tr>
                    <td>部门:</td>
                    <td><input class="easyui-textbox" value="${admuser.department}" type="text" name="modifyAdmUserDepartment" required="true"
                               missingMessage="不能为空"></input>
                    </td>
                    <td>邮箱:</td>
                    <td>
                        <input class="easyui-textbox" value="${admuser.mail}" type="text" name="modifyAdmUserEmail" validtype="email" required="true"
                               missingMessage="不能为空" invalidMessage="邮箱格式不正确"></input>
                    </td>

                    <td>新密码:</td>
                    <td><input class="easyui-textbox" type="password" name="modifyAdmUserPassword" required="true"  missingMessage="不修改密码请留空"></input>
                    </td>
                </tr>
                <tr>
                    <td> </td>
                    <td> </td>
                    <td> </td>
                    <td><a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitModifyAmdUserForm()">Submit</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearModifyAmdUserForm()">Clear</a>
                    </td>
                    <td> </td>
                    <td> </td>
                </tr>

            </table>
        </form>

    </div>
    <!--基础信息窗口 end-->

    <!--权限列表展示start-->
    <div data-options="region:'center'" border="false" style="padding:0px">
        <table id="updateAdmUserGroup" title="选择用户组" cellspacing="0" cellpadding="0">
            <thead>
            <tr>
                <th field="id" width="30">管理员组ID</th>
                <th field="groupname" width="50">管理员组名称</th>
                <th field="describe" width="50">描述信息</th>
                <th field="createtime" formatter="dataformatter" width="50">添加时间</th>
            </tr>
            </thead>
        </table>
        <div id="modifyAdmUser_toolbar" style="padding:5px;height:auto">

            <div>
                组名称:
                <input name="modifyAdmUser_UserGroupName" class="easyui-textbox" style="width: 100px">
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="modifyAdmUserObj.search()">查询</a>
            </div>
        </div>
    </div>
    <!--权限列表展示end-->
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/admUser/modifyAdmuser.js"></script>