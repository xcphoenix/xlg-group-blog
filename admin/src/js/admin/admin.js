layui.use(['table', 'form', 'element', 'layer'], function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;
    let element = layui.element;
    let $ = layui.jquery;

    $.ajaxSetup({
        xhrFields: {
            withCredentials: true
        }
    });

    let domain = "http://groupblog.xcphoenix.top";
    let getQQImage = function (qq) {
        let qqImageApiPrefix = "http://q1.qlogo.cn/g?b=qq&nk=";
        let qqImageApiSuffix = "&s=100";
        return qqImageApiPrefix + qq + qqImageApiSuffix;
    };
    let getUrl = function (type) {
        return domain + "/api/type/" + type + "/args";
    };

    let showMsg = function (msg) {
        layer.alert(msg, {
            skin: 'layui-layer-molv'
            , closeBtn: 0
            , anim: 5
        })
    };
    let renderUserData = function (data) {
        if (data.code !== 0) {
            showMsg(data.msg);
        } else {
            console.log(data);

            $('#user_link').attr("href", domain + "/user/" + data.data.uid);
            $('#user_name').text(data.data.username);
            $('#user_img').attr('src', getQQImage(data.data.qq));
            $("input[name='qq']").attr('value', data.data.qq);
            $("textarea[name='signature']").val(data.data.signature);
        }
    };
    let errorDealWith = function (err) {
        console.log(err);

        let errJson = err.responseJSON;
        if (errJson == null || errJson.msg == null) {
            showMsg('服务器异常');
        }
        else {
            showMsg(errJson.msg);
        }

        if (errJson.code === -2) {
            setTimeout(
                function () {
                    location.href = '/index.html';
                },
                3000
            );
        }
    };
    let getUserData = {
        url: domain + "/api/user/data",
        type: 'GET',
        xhrFields: {
            withCredentials: true
        },
        success: function (data) {
            renderUserData(data);
        },
        error: function (err) {
            errorDealWith(err);
        }
    };
    let formRender = function (url) {
        return {
            elem: '#type_param'
            , url: url
            , cols: [[
                {field: 'id', type: 'numbers', title: '序号'}
                , {field: 'param', title: '参数'}
                , {field: 'value', title: '值', edit: 'text'}
                , {field: 'description', title: '说明'}
            ]]
            , id: 'type_param'
            , page: false
        }
    };

    // 获取用户信息
    $.ajax(getUserData);

    // 获取用户博客参数
    $.ajax({
        url: domain + "/api/user/blog/params",
        type: 'GET',
        xhrFields: {
            withCredentials: true
        },
        success: function (data) {
            console.log(data);
            // 渲染数据
            layui.form.val('type', {'blog_type': data.data.blogType});
            table.render(formRender(getUrl(data.data.blogType)));
        },
        error: function (err) {
            errorDealWith(err);
        }
    });

    // 提交用户资料
    $(document).on('click', '#form_user_data', function (data) {
        let user = {};
        user.qq = $("input[name='qq']").val();
        user.signature = $("textarea[name='signature']").val();

        $.ajax({
            url: domain + "/api/user/data",
            type: 'PUT',
            xhrFields: {
                withCredentials: true
            },
            data: JSON.stringify(user),
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            success: function (data) {
                if (data.code !== 0) {
                    showMsg(data.msg);
                } else {
                    showMsg(data.msg);
                }
                $.ajax(getUserData);
            },
            error: function (err) {
                errorDealWith(err);
            }
        });
    });

    // 选择博客类型
    form.on('select(type_select)', function (data) {
        console.log(data);
        table.render(formRender(getUrl(data.value)));
    });

    // 更新博客参数
    $(document).on('click', '#submit', function () {
        console.log(table.cache['type_param']);
        let blog_type = form.val('type').blog_type;
        let params = table.cache['type_param'];
        let reqParams = {};
        if (blog_type == null || blog_type === '') {
            layer.msg('博客类型不能为空');
            return;
        }
        for (let i = 0; i < params.length; i++) {
            if (params[i].value == null) {
                layer.msg('参数不能为空');
                return;
            } else {
                reqParams[params[i].param] = params[i].value;
            }
        }
        let request = {};
        request.blogType = blog_type;
        request.params = reqParams;
        console.log(request);

        $.ajax({
            url: domain + "/api/user/blog/params",
            type: 'PUT',
            xhrFields: {
                withCredentials: true
            },
            data: JSON.stringify(request),
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            success: function (data) {
                console.log(data);
                showMsg(data.msg);
                // 重新渲染
                table.render(formRender(getUrl(request.blogType)));
            },
            error: function (err) {
                errorDealWith(err);
                // 重新渲染
                table.render(formRender(getUrl(request.blogType)));
            }
        });
    });

    // 修改密码
    $(document).on('click', '#passwd_submit', function () {
        let pattern = /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{6,20}$/;
        let currentPassword = $("input[name='currPasswd']").val();
        let newPassword = $("input[name='newPasswd']").val();
        let confirmPassword = $("input[name='confirmPasswd']").val();

        if (currentPassword === "" || newPassword === "" ||
            confirmPassword === "") {
            showMsg('密码不能为空');
        }

        if (newPassword !== confirmPassword) {
            showMsg('两次密码输入不同');
        }

        if (!pattern.test(newPassword)) {
            showMsg('密码不符合要求');
        }

        let request = {};
        request.currentPasswd = currentPassword;
        request.newPasswd = newPassword;

        console.log(request);

        $.ajax({
            url: domain + "/api/user/passwd",
            type: 'PUT',
            xhrFields: {
                withCredentials: true
            },
            data: JSON.stringify(request),
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            success: function (data) {
                console.log(data);
                showMsg(data.msg);
            },
            error: function (err) {
                errorDealWith(err);
            }
        });

    });

    $(document).on('click', '#logout', function () {
        $.ajax({
            url: domain + "/api/logout",
            type: 'POST',
            xhrFields: {
                withCredentials: true
            },
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            success: function (data) {
                console.log(data);
                showMsg(data.msg);
                setTimeout(
                    function () {
                        location.href = '/index.html';
                    },
                    2000
                );
            },
            error: function (err) {
                errorDealWith(err);
            }
        });
    })

});
