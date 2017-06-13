//此方法 用于测试 不用每次进来都要点一次左侧菜单
$(document).ready(function(){
    // addTab('系统用户管理auto','admuser/list')
    // addTab('权限管理auto','role/list')
    // addTab('系统用户组管理auto','admusergroup/list')
    // addTab('添加系统用户组管理auto','admusergroup/toadd')
    // addTab('添加系统用户auto','/admuser/toaddadmuser')
});

//添加标签页的方法
function addTab(title, url) {
    if ($('#mainTab').tabs('exists', title)) {
        $('#mainTab').tabs('select', title);
    } else {
        //var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#mainTab').tabs('add', {
            title: title,
            //content:content,
            href: url,
            closable: true
        });
    }
}

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};