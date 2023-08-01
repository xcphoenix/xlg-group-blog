layui.use(['table', 'form', 'element', 'layer','upload','jquery'], function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;
    let element = layui.element;
    let $ = layui.jquery;
    let upload = layui.upload;
    let grades;
    let jquery = layui.$;
    let uid;
    let blogId;

    jquery('#uploadPreview').css({
        width: '200px',
        height: '200px'
    });

    $.ajaxSetup({
        xhrFields: {
            withCredentials: true
        },
        beforeSend: function(request) {
            const token = localStorage.getItem('token');
            if (token) {
                request.setRequestHeader('Authorization', 'Bearer ' + token);
            }
        }
    });

    // let domain = "https://blog.xiyoulinux.com/";
    let domain = "http://127.0.0.1:6789/";
    let getQQImage = function (qq) {
        let qqImageApiPrefix = "https://q1.qlogo.cn/g?b=qq&nk=";
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
            uid = data.data.uid;
            $('#user_link').attr("href", domain + "/user/" + data.data.uid);
            $('#user_name').text(data.data.username);

            if(data.data.avatarUrl == null){
                $('#user_img').attr('src', getQQImage(data.data.qq));
            }else{
                $('#user_img').attr('src', data.data.avatarUrl);
                // 验证头像是否可用
                verifyImg(data.data.avatarUrl)
                    .then(function (result) {
                        $('#user_img').attr('src', data.data.avatarUrl);
                    })
                    .catch(function (error) {
                        let url = data.data.avatarUrl;
                        url = url.replace(/^https:/, 'http:');
                        verifyImg(url)
                            .then(function (result) {
                                $('#user_img').attr('src', url);
                            })
                            .catch(function (error) {
                                $('#user_img').attr('src', getQQImage(data.data.qq));
                            });
                });
            }
            $("input[name='qq']").attr('value', data.data.qq);
            $("textarea[name='signature']").val(data.data.signature);
        }
    };
    let errorDealWith = function (err) {
        console.log(err);

        let errJson = err.responseJSON;
        if (errJson == null || errJson.msg == null) {
            showMsg('服务器异常');
        } else {
            showMsg(errJson.msg);
        }

        if (errJson.code === -2) {
            setTimeout(
                function () {
                    $(location).attr('href', './index.html');
                },
                3000
            );
        }
    };
    let getUserData = {
        url: domain + "api/user/data",
        type: 'GET',
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

    element.on('tab(tab)',function (data) {
        // 获取博客类型
        console.log(this.id)
        if(this.id === "blogSetting"){
            let getBlogTypeAjax = getBlogTypeSelect('blog_type');
            let getUserBlogAjax;
            if(document.getElementById('blogInfoTable').children.length === 1){
                // 获取用户博客参数
                getUserBlogAjax = $.ajax({
                    url: domain + "/api/user/blog/params",
                    type: 'GET',
                    xhrFields: {
                        withCredentials: true
                    },
                    success: function (data) {
                        console.log(data);
                        blogId = data.data.blogType;
                        // 渲染数据
                        layui.form.val('type', {'blog_type': data.data.blogType});
                        table.render(formRender(getUrl(data.data.blogType)));
                    },
                    error: function (err) {
                        errorDealWith(err);
                    }
                });
            }
            let ajaxArr = [getBlogTypeAjax, getUserBlogAjax];

            jquery.when.apply(jquery, ajaxArr).done(function() {
                // Ajax请求都已完成
                form.val('type', {
                    'blog_type': blogId
                });
            });
        }
    })
    // 获得年级信息
    $.ajax({
        url: domain + "api/user/grade",
        type: 'GET',
        headers: {
            'X-CSRFToken': localStorage.getItem('token')
        },
        crossDomain: true,
        success: function (data) {
            console.log(data);
            // 渲染数据
            grades = data.data;
            const gradeList = document.getElementById('grade-list');
            gradeList.innerHTML = ``;
            for (const grade of grades) {
                gradeList.innerHTML += `<dd class=""><a id="`+grade+`" href="javascript:;">` + grade + `</a></dd>`
            }
            //重新加载
            element.render('nav');
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
            crossDomain: true,
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

    //上传头像
    var uploadInst = upload.render({
        elem: '#uploadBtn', //绑定元素
        url: domain+'api/user/image', //上传接口
        method: 'POST', //上传方式
        data: { //上传额外的参数
        },
        accept: 'images', //允许上传的文件类型
        size: 1024 * 10, //最大允许上传的文件大小
        acceptMime: 'image/*',
        field: "image",
        before: function(obj){
            $('#avatar-image').show();
            //上传前回调
            obj.preview(function(index, file, result){
                $('#uploadPreview').attr('src', result).attr('alt', file.name);
            });
            $('#uploadText').html('上传中...');
        },
        done: function(res){
            $('#avatar-image').show();
            //上传完毕回调
            console.log(res);
            if(res.code === 0) {
                $('#uploadPreview').attr('src', res.data.src);
                $('#uploadText').html('上传成功');
                $('#user_img').attr('src',res.data.src);
            } else {
                $('#uploadText').html('上传失败');
            }
        },
        error: function(){
            $('#avatar-image').show();
            //请求异常回调
            $('#uploadText').html('上传失败');
        }
    });

    // 选择博客类型
    form.on('select(type_select)', function (data) {
        let blogTab = formRender(getUrl(data.value));
        table.render(blogTab);
        blogId = data.value;
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

    $(document).on('click', '#updateBlog', function () {
        let blog_type = form.val('type').blog_type;
        if (blog_type == null || blog_type === '') {
            layer.msg('博客类型不能为空');
            return;
        }
        if(blog_type >= 4){
            showMsg("不能抓取该类型博客");
            return;
        }
        $.ajax({
            url: domain + "/api/execByUid/"+uid,
            type: 'GET',
            xhrFields: {
                withCredentials: true
            },
            before: function () {
                showMsg("开启抓取");
            },
            error: function (err) {
                errorDealWith(err);
            }
        });
    });

    $(document).on('click', '#updateBlogs', function () {
        console.log("wait");
        $.ajax({
            url: domain + "/api/schedule",
            type: 'GET',
            xhrFields: {
                withCredentials: true
            },
            before: function () {
                showMsg("开启抓取");
            },
            error: function (err) {
                errorDealWith(err);
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

    // 注销
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
                        localStorage.removeItem('token');
                        clearCookie();
                        document.body.innerHTML = ""
                        $(location).attr('href', './index.html');
                    },
                    2000
                );
            },
            error: function (err) {
                errorDealWith(err);
            }
        });
    })

    element.on('nav(test)', function (data) {
        if(data.length <= 0){
            return;
        }
        if (data.text() === "用户信息" && data.get(0).id === "用户信息") {
            document.getElementById("user").style.display = "none";
            document.getElementById("newUser").style.display = "none";
            document.getElementById("manage").style.display = "block";
            return;
        }
        if(data.text() === "添加新成员" && data.get(0).id === "添加新成员"){
            document.getElementById("manage").style.display = "none";
            document.getElementById("user").style.display = "none";
            document.getElementById("newUser").style.display = "block";
            getBlogTypeSelect('addUser_blog_type');
            // 将当前年份作为文本框的默认值
            document.getElementById("addMember_grade").value = new Date().getFullYear();
            return;
        }
        for (const grade of grades) {
            if (data.text() === grade && data.get(0).id === grade) {
                document.getElementById("manage").style.display = "none";
                document.getElementById("newUser").style.display = "none";
                document.getElementById("user").style.display = "block";
                spanList(grade);
                userList(grade);
            }
        }
    });

    // 获取对应年级成员blog信息
    //todo: 别问为什么发这么多次请求，问就自由
    let userList = function (grade) {
        let userListData;
        $.ajax({
            url: domain + "/api/user/infos/"+grade,
            type: 'GET',
            xhrFields: {
                withCredentials: true
            },
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            success: function (data) {
                console.log(data);
                userListData = data.data;
                table.render({
                    elem: "#user-list",
                    cols: [[
                        {field: "uid", title: "ID", sort: true},
                        {field: "username", title: "姓名", sort: true},
                        {field: "lastPubTime",title: "最后博客时间", sort: true},
                        {field: "blogArg", title: "博客地址", sort: true,
                            templet: function(d){
                                return '<a href="' + d.blogArg + '" target="_blank">'+d.blogArg+'</a>'
                            }
                        },
                    ]],
                    data: userListData,
                    page: true,
                    limits: [20, 50, 100],
                    limit: 20,
                })
            },
            error: function (err) {
                errorDealWith(err);
            }
        });
    };

    let spanList = function (msg) {
        let str = document.getElementById('m');
        str.innerHTML = `<a href="javascript:;"> 首页 </a>
                <a href="javascript:;"> admin </a> 
                <a><cite> ` + msg + ` </cite></a>`;
        element.render('breadcrumb')
    }

    let getBlogTypeSelect = function (id) {
        // 防止重复请求
        if(document.getElementById(id).children.length < 2){
            return $.ajax({
                url: domain + "api/type/blogArgs",
                type: 'GET',
                xhrFields: {
                    withCredentials: true
                },
                success: function (data) {
                    // 渲染数据
                    let tmpType = data.data;
                    const blogType = document.getElementById(id);
                    for(const type of tmpType){
                        let option = document.createElement("option");
                        option.text = type.typeName;
                        option.value = type.typeId;
                        blogType.add(option);
                    }
                    form.render('select','type');
                },
                error: function (err) {
                    errorDealWith(err);
                }
            })
        }
    }

    // 年级格式验证规则
    form.verify({
        grade: function(value, item) {
            if ((value == null) || (value === "") || !(/^\d{4}$/).test(value)) {
                return '年级格式错误';
            }
        }
    });

    // 添加新成员
    form.on('submit(addMember)', function(data){
        console.log(data.field)
        // TODO: 发送表单数据到后端进行处理
        let request = {};
        request.username = data.field.username;
        request.grade = data.field.grade;
        request.blogType = data.field.blog_type
        request.blogArg = data.field.blog;
        $.ajax({
            url: domain + "api/user/addMember",
            type: 'POST',
            xhrFields: {
                withCredentials: true
            },
            data: JSON.stringify(request),
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            success: function (data) {
                // 渲染数据
                console.log("add success...")
            },
            error: function (err) {
                errorDealWith(err);
            }
        });
        layer.msg('添加成功');
        return false;
    });

    const clearCookie = () => {
        var keys = document.cookie.match(/[^ =;]+(?==)/g);
        if (keys) {
            for (var i = keys.length; i--; ) {
                document.cookie =
                    keys[i] + "=0;expires=" + new Date(0).toUTCString() + ";max-age=0";
            }
        }
    };

    let verifyImg = function(url)  {
        return new Promise(function(resolve, reject) {
            let img = new Image();
            img.src = url;
            img.addEventListener('load', function() {
                resolve(true);
            });
            img.addEventListener('error', function() {
                reject(false);
            });
        });
    }
});
