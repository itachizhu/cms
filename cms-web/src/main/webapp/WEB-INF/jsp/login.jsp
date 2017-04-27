<%--
  Created by IntelliJ IDEA.
  User: yangz
  Date: 2017/4/10
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<header>
    <LINK href="favicon.ico" type=image/x-icon rel="shortcut icon" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/js/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/js/themes/icon.css">
    <!--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/js/themes/color.css">-->
    <!--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/js/demo/demo.css">-->
    <!--<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/bootstrap.min.css" />-->
    <!--<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/font-awesome.min.css" />-->

    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/demo.css" type="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/zTreeStyle/zTreeStyle.css" type="text/css">

</header>

<body class="easyui-layout">
<!--顶部logo区域-->
<div href="<%=request.getContextPath()%>/header" data-options="region:'north',split:true" border="false" style="height:100px;">
</div>
<!--页面左侧导航栏-->
<div href="<%=request.getContextPath()%>/leftMenu" data-options="region:'west',iconCls:'icon-ok',title:'导航菜单',split:true" style=" width:220px;background:#ffffff">

</div>
<!--中间展示区-->
<div data-options="region:'center'" style="padding:0px;background:#eee;">
    <div id="mainTab" class="easyui-tabs" data-options="fit:true" border="false" style="background:#E0ECFF">
        <div data-options="region:'west',iconCls:'icon-home',title:'   Home'" href="<%=request.getContextPath()%>/welcome" style="display:none;">
        </div>
    </div>
</div>

<!--<div data-options="region:'south'" style="height:25px;line-height: 5px;  text-align: center;vertical-align:middle">
    版权所有 京公网安备xxxxxxxxxxxxxxxxxx号
</div>-->
</body>
<!--<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-3.1.0.min.js"></script>-->
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery/jquery.ztree.core.js"></script>
<!--easyUI-->
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/index.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/phpjs-min.js"></script>
</html>
