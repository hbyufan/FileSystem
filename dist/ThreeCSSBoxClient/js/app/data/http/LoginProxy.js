function LoginProxy() {
    this.NAME = "LoginProxy";
    this.login = function (token) {
        var data = {
            "hOpCode": 50002,
            "token": token
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.loginSuccess;
        sendParam.failHandle = this.loginFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.url = $T.url.boxUrl;
        $T.httpUtil.send(sendParam);
    }
    this.loginSuccess = function (result, sendParam) {
        $T.cookieParam.setCookieParam($T.cookieName.USER_FOLD_TOP_ID, result.userFoldTopId);
        $T.cookieParam.setCookieParam($T.cookieName.USER_ID, result.userId);
        $T.cookieParam.setCookieParam($T.cookieName.USER_REALNAME, result.userRealName);
        $T.viewManager.notifyObservers($T.viewManager.getNotification($T.notificationExt.LOGIN_SUCCESS));
    }
    this.loginFail = function (result, sendParam) {

    }
}
$T.loginProxy = new LoginProxy();