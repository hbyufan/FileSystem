function LoginBoxMediator() {
    this.init = function (view) {
        var token = $T.getUrlParam.getUrlParam($T.httpConfig.TOKEN);
        if (token == null) {
            // token是空应该提示无法登陆
            return;
        }
        // 设置cookie
        $T.cookieParam.setCookieParam($T.cookieName.TOKEN, token);
        $T.loginProxy.login(token);
    }
    // 注销方法
    this.dispose = function () {

    }
    // 关心消息数组
    this.listNotificationInterests = [$T.notificationExt.LOGIN_SUCCESS];
    // 关心的消息处理
    this.handleNotification = function (data) {
        switch (data[0].name) {
            case $T.notificationExt.LOGIN_SUCCESS:
                window.location.href = "index.html?token=" + $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
                break;
        }
    }
    this.advanceTime = function (passedTime) {

    }
}
$T.loginBoxMediator = new LoginBoxMediator();