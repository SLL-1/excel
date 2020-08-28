<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2020-08-25
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <title>Title</title>
    <!--引用jquery相关包，jquery.min.js要在最前面-->
    <script type="text/javascript" src="webjars/jquery-easyui/1.5.21/js/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/jquery-easyui/1.5.21/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="webjars/jquery-easyui/1.5.21/locale/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" type="text/css" href="webjars/jquery-easyui/1.5.21/css/easyui.css" />

    <script type="text/javascript">
        $(function () {
            $('a[title]').click(function () {
                var src=$(this).attr('title');
                var title=$(this).html();
                if ($('#tabs').tabs('exists',title)){
                    $('#tabs').tabs('select',title);
                }else{
                    $('#tabs').tabs('add',{
                        title:title,
                        content:'<iframe frameborder="0" style="width: 100%;height: 100%" src='+src+'></iframe>',
                        closable:true
                    });
                }
            });

        });
    </script>

</head>

<body>
<div class="easyui-layout" fit=true style="width: 100%;height: 100%;">
    <div region="west" split="true" title="菜单" style="width: 200px;" >
        <div class="easyui-accordion" fit=true>
            <div title="Excel导入导出" selected="true" style="overflow: auto;padding: 10px;">
            <a title="jsp/studentList.jsp">数据导入</a><br/>
            <a title="jsp/layout.jsp">数据导入1</a><br/>
            </div>
            <div title="Excel导入导出2" selected="false" style="overflow: auto;padding: 10px;">
                <a title="jsp/layout.jsp">数据导入2</a><br/>
                <a title="jsp/layout.jsp">数据导入21</a><br/>
            </div>
        </div>
    </div>
    <div region="center" title="主界面" style="padding: 5px;">
        <div id="tabs" class="easyui-tabs" fit=true style="width: 500px;height: 250px;" >

        </div>
    </div>

</div>
</body>
</html>
