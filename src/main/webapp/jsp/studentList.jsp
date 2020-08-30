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
            toolbar:[{
                text:'导出成excel',
                // iconCls:'icon-save',
                handler:function () {
                    //获取后台传递的参数
                    var className=$('#t-student').datagrid('getData').className;
                    var methodName=$('#t-student').datagrid('getData').methodName;
                    //获取表头信息
                    var header =$('#t-student').datagrid('options').columns[0];
                    var fields="";
                    var titles="";
                    for (var i=0;i<header.length;i++){
                        var field=header[i].field;
                        var title=header[i].title;
                        var hiddenFlag=header[i].hidden;
                        if(!hiddenFlag){
                            var dh= i==(header.length-1)?"":",";
                            fields=fields+field+dh;
                            titles=titles+title+dh;
                        }
                    }
                    //向后台发送请求，数据用form表单形式传输
                    var form=$("<form>");
                    form.attr('style','display:none');
                    form.attr('target','');
                    form.attr('method','post');
                    form.attr('action','export');
                    //添加input数据
                    var input1=$("<input>");
                    input1.attr('type','hidden');
                    input1.attr('name','fields');
                    input1.attr('value',fields);

                    var input2=$("<input>");
                    input2.attr('type','hidden');
                    input2.attr('name','titles');
                    input2.attr('value',titles);

                    var input3=$("<input>");
                    input3.attr('type','hidden');
                    input3.attr('name','className');
                    input3.attr('value',className);

                    var input4=$("<input>");
                    input4.attr('type','hidden');
                    input4.attr('name','methodName');
                    input4.attr('value',methodName);
                    //将表单数据放入body中
                    $('body').append(form);
                    form.append(input1);
                    form.append(input2);
                    form.append(input3);
                    form.append(input4);
                    //提交表单
                    form.submit();
                }
            }]

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
