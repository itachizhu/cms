<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--添加权限窗口 -->
<div style="padding:10px 50px 20px 50px">
    <form id="addrole" method="post">
        <table cellpadding="5">
            <tr>
                <td>权限名称:</td>
                <td><input class="easyui-textbox" type="text" name="rolename" required="true" missingMessage="不能为空"></input>
                </td>
            </tr>
            <tr>
                <td>权限地址:</td>
                <td><input class="easyui-textbox" type="text" name="roleurl" required="true" missingMessage="请求地址(如果仅作为导航菜单目录请留空)"></input>
                </td>
            </tr>
            <tr>
                <td>模块名称:</td>
                <td><input class="easyui-textbox" type="text" name="module" required="true" missingMessage="Conorller名称(如果仅作为导航菜单目录请留空)"></input>
                </td>
            </tr>
            <tr>
                <td>操作名称:</td>
                <td><input class="easyui-textbox" type="text" name="action" required="true" missingMessage="方法名称(如果仅作为导航菜单目录请留空)"></input>
                </td>
            </tr>
            <tr>
                <td>是否作为菜单:</td>
                <td>
                    <select class="easyui-combobox" id="roleismenu" >
                        <option value="0">是</option>
                        <option value="1">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>权限说明:</td>
                <td><input class="easyui-textbox" name="roledescribe" data-options="multiline:true" style="height:60px"></input>
                </td>
            </tr>
        </table>
        <div style="text-align:center;padding:10px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitAddRoleForm()">Submit</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearAddRoleForm()">Clear</a>
        </div>
    </form>

</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/role/addRole.js"></script>