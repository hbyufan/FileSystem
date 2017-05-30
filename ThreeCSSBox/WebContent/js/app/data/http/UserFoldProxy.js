function UserFoldProxy() {
    this.NAME = "UserFoldProxy";
    this.createUserFold = function (userFoldName, userFoldParentId) {
        var data = {
            "hOpCode": 50003,
            "userFoldName": userFoldName,
            "userFoldParentId": userFoldParentId
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.createUserFoldSuccess;
        sendParam.failHandle = this.createUserFoldFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        sendParam.url = $T.url.boxUrl;
        $T.httpUtil.send(sendParam);
    }
    this.createUserFoldSuccess = function (result, sendParam) {

    }
    this.createUserFoldFail = function (result, sendParam) {

    }
    this.updateUserFold = function (userFoldId, userFoldName, userFoldState, isUpdateUserFoldParent, userFoldParentId) {
        var data = {
            "hOpCode": 50004,
            "userFoldId": userFoldId,
            "userFoldName": userFoldName,
            "isUpdateUserFoldParent": isUpdateUserFoldParent == "1" ? true : false,
            "userFoldParentId": userFoldParentId,
            "userFoldState": userFoldState
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.updateUserFoldSuccess;
        sendParam.failHandle = this.updateUserFoldFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        sendParam.url = $T.url.boxUrl;
        $T.httpUtil.send(sendParam);
    }
    this.updateUserFoldSuccess = function (result, sendParam) {

    }
    this.updateUserFoldFail = function (result, sendParam) {

    }
    this.getUserFoldChildren = function (userFoldParentId) {
        var data = {
            "hOpCode": 50005,
            "userFoldParentId": userFoldParentId
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.getUserFoldChildrenSuccess;
        sendParam.failHandle = this.getUserFoldChildrenFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        sendParam.url = $T.url.boxUrl;
        $T.httpUtil.send(sendParam);
    }
    this.getUserFoldChildrenSuccess = function (result, sendParam) {
        $T.viewManager.notifyObservers($T.viewManager.getNotification($T.notificationExt.GET_FOLD_CHILDREN_SUCCESS, result));
    }
    this.getUserFoldChildrenFail = function (result, sendParam) {

    }
    this.getRecycleBin = function (userFoldTopId) {
        var data = {
            "hOpCode": 50006,
            "userFoldTopId": userFoldTopId
        };

        var sendParam = new SendParam();
        sendParam.successHandle = this.getRecycleBinSuccess;
        sendParam.failHandle = this.getRecycleBinFail;
        sendParam.object = this;
        sendParam.data = data;
        sendParam.token = $T.cookieParam.getCookieParam($T.cookieName.TOKEN);
        sendParam.url = $T.url.boxUrl;
        $T.httpUtil.send(sendParam);
    }
    this.getRecycleBinSuccess = function (result, sendParam) {

    }
    this.getRecycleBinFail = function (result, sendParam) {

    }
}
$T.userFoldProxy = new UserFoldProxy();