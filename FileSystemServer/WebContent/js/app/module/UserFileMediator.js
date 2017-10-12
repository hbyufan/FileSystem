function UserFileMediator() {

    this.init = function (view) {


        $("#md5Check").on("click", this.onMd5Check);
        $("#updateUserFile").on("click", this.onUpdateUserFile);
    }
    // 注销方法
    this.dispose = function () {


        $("#md5Check").remove("click", this.onMd5Check);
        $("#updateUserFile").remove("click", this.onUpdateUserFile);
    }
    // 关心消息数组
    this.listNotificationInterests = [];
    // 关心的消息处理
    this.handleNotification = function (data) {

    }
    this.onMd5Check = function () {
        var userFileId = $("#userFileId").val();
        $T.uploadFileProxy.checkMD5(null, null, null, null, 0, "111");
    }
    this.onUpdateUserFile = function () {
        var userFileId = $("#userFileIdUpdate").val();
        var userFileName = $("#userFileName").val();
        var isUpdateUserFoldParent = $("#isUpdateUserFoldParent").val();
        var userFoldParentId = $("#userFoldParentId").val();
        var userFileState = $("#userFileState").val();
        $T.userFileProxy.updateUserFile(userFileId, userFileName, userFileState, isUpdateUserFoldParent, userFoldParentId);
    }
}
$T.userFileMediator = new UserFileMediator();