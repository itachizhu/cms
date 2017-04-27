<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!--修改权限窗口 -->
<div style="padding:10px 50px 20px 50px">
    <form id="modifyrole" method="post">
        <input type="hidden" name="m_roleid" id="roleid" value="${role.id}" required="true" missingMessage="不能为空">
        <input type="hidden" name="rolepid" id="rolepid" value="${role.pid}" required="true" missingMessage="不能为空">
        <table cellpadding="5">
            <tr>
                <td>权限名称:</td>
                <td>
                    <input class="easyui-textbox" type="text" name="m_rolename" required="true" missingMessage="不能为空" value="${role.name}" />
                </td>
            </tr>
            <tr>
                <td>权限地址:</td>
                <td><input class="easyui-textbox" type="text" name="m_roleurl" required="true" missingMessage="不能为空" value="${role.roleurl}"></input>
                </td>
            </tr>
         
            <tr>
                <td>模块名称:</td>
                <td><input class="easyui-textbox" type="text" name="m_module" required="true" value="${role.module}" missingMessage="Conorller名称(如果仅作为导航菜单目录请留空)"></input>
                </td>
            </tr>
            <tr>
                <td>操作名称:</td>
                <td><input class="easyui-textbox" type="text" name="m_action" required="true" value="${role.action}" missingMessage="方法名称(如果仅作为导航菜单目录请留空)"></input>
                </td>
            </tr>
            <tr>
                <td>是否作为菜单:</td>
                <td>
                    <select class="easyui-combobox" id="m_roleismenu">
                        <c:if test="${role.ismenu == 0}">
                            <option value="0"  selected="selected"  >是</option>
                        </c:if>
                        <c:if test="${role.ismenu == 1}">
                            <option value="1"  selected="selected"  >否</option>
                        </c:if>
                     </select>
                </td>
            </tr>

            <tr>
                <td>权限说明:</td>
                <td><input class="easyui-textbox" name="m_roledescribe" data-options="multiline:true" style="height:60px" value="${role.describe}"></input>
                </td>
            </tr>
        </table>
        <div style="text-align:center;padding:10px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitModifyRoleForm()">Submit</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearModifyRoleForm()">Clear</a>
        </div>
    </form>
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/role/modifyRole.js"></script>