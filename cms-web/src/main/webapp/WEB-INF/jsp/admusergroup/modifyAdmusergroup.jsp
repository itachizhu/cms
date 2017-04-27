<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
<!--添加权限窗口 -->
<div class="easyui-layout" data-options="fit:true">
    <!--基础信息窗口 start-->
    <div data-options="region:'west',split:true" border="false" style="width:350px;padding:10px 10px 20px 50px">
        <form id="modifyamdusergroup" method="post">
            <input value="${admusergroup.id}" type="hidden" name="admgroupuserid"></input>
            <table cellpadding="5">
                <tr>
                    <td>管理员组名称:</td>
                    <td><input class="easyui-textbox" value="${admusergroup.groupname}" type="text" name="ag_m_name" required="true" missingMessage="不能为空"></input>
                    </td>
                </tr>
                <tr>
                    <td>描述信息:</td>
                    <td><input class="easyui-textbox" style="height:60px" data-options="multiline:true" value="${admusergroup.des}" name="ag_m_describe" required="true" missingMessage="不能为空"></input>
                    </td>
                </tr>

            </table>

            <div style="text-align:center;padding:10px">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitModifyAmdUserGroupForm()">Submit</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitModifyAmdUserGroupForm()">Clear</a>
            </div>
        </form>
    </div>
    <!--基础信息窗口 end-->

    <!--权限列表展示start-->
    <div data-options="region:'center'" border="false" style="padding:0px">
        选择权限：
        <div class="modifyadmgrouproletree left">
            <ul id="modifyadmgrouproletree" class="ztree"></ul>
        </div>
    </div>
    <!--权限列表展示end-->
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/admusergroup/modifyadmusergroup.js"></script>
</body>

</html>