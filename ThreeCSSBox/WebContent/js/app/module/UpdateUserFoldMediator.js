function UpdateUserFoldMediator() {

    this.init = function (view) {


        $("#updateUserFold").on("click", this.onUpdateUserFold);
    }
    // 注销方法
    this.dispose = function () {


        $("#updateUserFold").remove("click", this.onUpdateUserFold);
    }
    // 关心消息数组
    this.listNotificationInterests = [];
    // 关心的消息处理
    this.handleNotification = function (data) {

    }
    this.onUpdateUserFold = function () {
        var userFoldId = $("#userFoldId").val();
        var userFoldName = $("#userFoldName").val();
        var isUpdateUserFoldParent = $("#isUpdateUserFoldParent").val();
        var userFoldParentId = $("#userFoldParentId").val();
        var userFoldState = $("#userFoldState").val();
        $T.userFoldProxy.updateUserFold(userFoldId, userFoldName, userFoldState, isUpdateUserFoldParent, userFoldParentId);
    }
}
$T.updateUserFoldMediator = new UpdateUserFoldMediator();