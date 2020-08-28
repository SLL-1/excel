<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2020-08-26
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
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
    <link rel="stylesheet" type="text/css" href="css/common.css" />
<script type="text/javascript">
    $(function () {
        $('#t-student').datagrid({
            idField:'id',
            title:'数据列表',
            fit:true,
            height:450,
            url:'student-list',
            fitColumns:true,
            striped:true,//隔行变色
            loadMsg:'数据正在加载，请等待。。。',
            rownumbers:true,
            frozenColumns:[[//冻结列特性 ,不要与fitColumns 特性一起使用
                {
                    field:'ck',
                    width:50,
                    checkbox:true,
                }
            ]],
            columns:[[
                // {
                //     field: 'sid',
                //     title: 'Student id',
                //     hidden:true,
                // },
                {
                    field: 'stunum',
                    title: '编号',
                    width:100,
                    sortable:true,
                },
                {
                    field: 'stuname',
                    title: '姓名',
                    width:100,
                    sortable:true,
                },
                {
                    field: 'stuage',
                    title: '年龄',
                    width:100,
                    sortable:true,
                },
                {
                    field: 'stusex',
                    title: '性别',
                    width:100,
                    sortable:true,
                },
                {
                    field: 'stubirthday',
                    title: '出生日期',
                    width:100,
                    sortable:true,
                },
                {
                    field: 'stuhobby',
                    title: '爱好',
                    width:100,
                    sortable:true,
                }

            ]],
            pagination:true,//创建分页工具栏
            pageSize:10,
            pageList:[5,10,15,20,50],

        });
    });

</script>
</head>
<body>
<div class="easyui-layout" style="width: 100%;height: 100%;">
    <div region="center">
        <table id="t-student"></table>
    </div>

</div>
</body>
</html>
