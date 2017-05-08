function submitAddRoleForm() {
    url = "role/addrole"
    var pid = $("input[name='searchRolepid']").val()
    var data = {
        pid: pid,
        name: $("input[name='rolename']").val(),
        roleurl: $("input[name='roleurl']").val(),
        module: $("input[name='module']").val(),
        action: $("input[name='action']").val(),
        ismenu: $("#roleismenu").combobox('getValue'),
        describe: $("input[name='roledescribe']").val()
    };


    $.post(url, data, function (result) {
        if (result == "success") {
            clearAddRoleForm()
            loadTree(pid)
            loaddatagrid(pid)
            $.messager.alert('操作提示', "添加成功", 'info');
        }else {
        $.messager.alert('操作提示', result, 'warning');
        selections=0;
    }
    }).error(function (XMLHttpRequest) {
        if (XMLHttpRequest.status == 500) {
            $("#errmsg").html("服务器异常，请联系管理员！");
        } else if (XMLHttpRequest.status == 404) {
            $("#errmsg").html("请求地址不存在，请联系管理员！");
        } else {
            if (XMLHttpRequest.responseJSON && XMLHttpRequest.responseJSON.message) {
                debugger
                console.log(XMLHttpRequest.responseJSON.code);
                $.messager.alert('操作提示', XMLHttpRequest.responseJSON.message, 'warning');
            }
        }
    });
}

function clearAddRoleForm() {
    $('#addrole').form('clear');
    $("#roleismenu").combobox({value:"1"})
}