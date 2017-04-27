<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style type="text/css">
    .fr {
        float: right;
        display: inline;
    }
    
    .main {
        height: 95px;
    }
    
    .content {
        width: 100%;
        background-color: #E0ECFF;
    }
</style>
<div class="content">
    <div class="fr">
        <%--<h2 style="margin-right:10px">欢迎：<%=session.getAttribute("username")%></h2>--%>
            <h2 style="margin-right:10px">欢迎：${userDTO.name}</h2>
        <a href='<%=request.getContextPath()%>/loginout'>退出</a>
    </div>
    <div class="main">
        <h1> XXX 管理系统 </h1>
    </div>
</div>