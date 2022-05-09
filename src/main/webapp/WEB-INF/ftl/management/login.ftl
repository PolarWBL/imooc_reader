<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>慕课书评网数据管理系统</title>
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
    <script src="/resources/layui/layui.js"></script>
    <style>
        body{
            background-color: gainsboro;
        }
        .oa-container{
            width: 500px;
            height: 380px;
            position: absolute;
            top: 50%;
            left: 50%;
            margin-top: -190px;
            margin-left: -250px;
        }
        .title{
            text-align: center;
            margin: 20px;
        }
        input{
            text-align: center;
            font-size: 24px;
        }
        button{
            width: 240px;
            margin: 0 130px;
        }
    </style>

</head>
<body>

<div class="oa-container">
    <h1 class="title">慕课书评网数据管理系统</h1>
    <form class="layui-form" action="#">
        <div class="layui-form-item">
            <input type="text" name="username" required  lay-verify="required" placeholder="用户名" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-item">
            <input type="password" name="password" required lay-verify="required" placeholder="密码" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-item">
            <button class="layui-btn" lay-submit lay-filter="login">登录</button>
        </div>
    </form>
</div>
<script>
    layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(login)', function(formData){
            console.log(formData);
            layui.$.ajax({
                url: "/management/check_login",
                data: formData.field,
                type: "post",
                dataType: "JSON",
                success: function (json) {
                    if (json.code === "0") {
                        window.location = "/management/index.html";
                    } else {
                        layer.msg(json.msg);
                    }
                }
            });
            return false;
        });
    });
</script>

</body>
</html>