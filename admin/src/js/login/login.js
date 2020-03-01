layui.use(['element', 'layer'], function () {
    let $ = layui.jquery;
    let layer = layui.layer;

    let showMsg = function (msg) {
        layer.alert(msg, {
            skin: 'layui-layer-molv'
            , closeBtn: 0
            , anim: 5
        })
    };

    $('#login-form').submit(function () {
        let username = $("input[name='username']").val();
        let password = $("input[name='password']").val();

        if (username === '' || password === '') {
            showMsg('用户名或密码不能为空');
        }

        let req_data = {};
        req_data.username = username;
        req_data.password = password;
        console.log(req_data);

        $.ajax({
            url: "http://groupblog.xcphoenix.top/api/login",
            type: 'POST',
            data: JSON.stringify(req_data),
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            xhrFields: {
                withCredentials: true
            },
            success: function (data) {
                if (data.code !== 0) {
                    showMsg(data.msg);
                } else {
                    layer.open({
                        content: data.msg,
                    });
                    document.cookie = "uid=" + data.data;
                    $(location).attr('href', './admin.html');
                }
            },
            error: function (err) {
                showMsg('服务器异常')
            }
        });
        return false;
    });

    $('#logout').click(function () {
        console.log('click')
    });

});