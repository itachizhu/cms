/**
 * Created by itachi on 2017/6/15.
 */
function exceptionDialog(title, content, redirectUrl) {
    if ($("error_message_div").length === 0) {
        $(document.body).prepend('<div id="error_message_div" class="easyui-dialog" title="' + title + '"></div>');
    }
    $("#error_message_div").dialog({
        content: content,
        width: 200,
        height: 100,
        modal: true,
        title: title,
        onClose: function () {
            if (redirectUrl) {
                window.location.href = redirectUrl;
            } else {
                $(this).dialog('close');
            }
        }
    });
}

function handleException(XMLHttpRequest, redirectUrl) {
    if (XMLHttpRequest.status == 500) {
        exceptionDialog("系统异常", "服务器异常，请联系管理员！", redirectUrl);
        // $("#errmsg").html("服务器异常，请联系管理员！");
    } else if (XMLHttpRequest.status == 404) {
        exceptionDialog("系统异常", "请求地址不存在，请联系管理员！", redirectUrl);
        // $("#errmsg").html("请求地址不存在，请联系管理员！");
    } else {
        if (XMLHttpRequest.responseJSON && XMLHttpRequest.responseJSON.message) {
            console.log(XMLHttpRequest.responseJSON.code);
            // $("#errmsg").html(XMLHttpRequest.responseJSON.message);
            exceptionDialog("系统异常", XMLHttpRequest.responseJSON.message, null);
        }
    }
}