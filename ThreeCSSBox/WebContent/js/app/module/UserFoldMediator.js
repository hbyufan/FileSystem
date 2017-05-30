function UserFoldMediator() {

    this.init = function (view) {


        $("#createUserFold").on("click", this.onCreateUserFold);
        $("#getUserFoldChildren").on("click", this.onGetUserFoldChildren);
        $("#getRecycleBin").on("click", this.onGetRecycleBin);
    }
    // 注销方法
    this.dispose = function () {


        $("#createUserFold").remove("click", this.onCreateUserFold);
        $("#getUserFoldChildren").remove("click", this.onGetUserFoldChildren);
        $("#getRecycleBin").remove("click", this.onGetRecycleBin);
    }
    // 关心消息数组
    this.listNotificationInterests = [$T.notificationExt.GET_FOLD_CHILDREN_SUCCESS];
    // 关心的消息处理
    this.handleNotification = function (data) {
        switch (data[0].name) {
            case $T.notificationExt.GET_FOLD_CHILDREN_SUCCESS:
                this.onGetFoldChildrenSuccess(data[0].body);
                break;
        }

    }
    this.onGetFoldChildrenSuccess = function (data) {
        $("#list")[0].innerHTML = "";
        if (data.userFileList != null) {
            for (var i = 0; i < data.userFileList.length; i++) {
                var userFile = data.userFileList[i];
                var view = document.createElement("div");
                var body = '<span>' + userFile.userFileName + '</span><input id="' + userFile.userFileId + '_download" type="button" value="下载"/></br>';
                view.innerHTML = body;
                $("#list").append($(view));
                $("#" + userFile.userFileId + "_download").on("click", this.onDownLoad);
            }
        }
    }
    this.onDownLoad = function (event) {
        var id = event.currentTarget.id;
        var array = id.split("_");
        window.location.href = $T.userFileProxy.getUserFile(array[0]);
    }
    this.onCreateUserFold = function () {
        var userFoldName = $("#userFoldName").val();
        var userFoldParentId = $("#userFoldParentId").val();
        $T.userFoldProxy.createUserFold(userFoldName, userFoldParentId);
    }
    this.onGetUserFoldChildren = function () {
        var userFoldParentId = $("#userFoldParentIdSelect").val();
        $T.userFoldProxy.getUserFoldChildren(userFoldParentId);
    }
    this.onGetRecycleBin = function () {
        var userFoldTopId = $("#userFoldTopId").val();
        $T.userFoldProxy.getRecycleBin(userFoldTopId);
    }
}
$T.userFoldMediator = new UserFoldMediator();