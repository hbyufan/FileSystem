function MutliOperateMediator() {

    this.init = function (view) {


        $("#clearRecycleBin").on("click", this.onClearRecycleBin);
        $("#toRecycleBin").on("click", this.onToRecycleBin);
        $("#offRecycleBin").on("click", this.onOffRecycleBin);
        $("#remove").on("click", this.onRemove);
        $("#moveto").on("click", this.onMoveto);

    }
    // 注销方法
    this.dispose = function () {


        $("#clearRecycleBin").remove("click", this.onClearRecycleBin);
        $("#toRecycleBin").remove("click", this.onToRecycleBin);
        $("#offRecycleBin").remove("click", this.onOffRecycleBin);
        $("#remove").remove("click", this.onRemove);
        $("#moveto").remove("click", this.onMoveto);

    }
    // 关心消息数组
    this.listNotificationInterests = [];
    // 关心的消息处理
    this.handleNotification = function (data) {

    }
    this.onClearRecycleBin = function () {
        $T.mutliOperateProxy.clearRecyclebin($T.fileSystemStatus.nowFoldId);
    }
    this.onToRecycleBin = function () {
        var userFoldIdStr = $("#userFoldIdStr").val();
        var userFileIdStr = $("#userFileIdStr").val();
        var userFoldIdList = null;
        var userFileIdList = null;
        if (userFoldIdStr != "") {
            userFoldIdList = userFoldIdStr.split(",");
        }
        if (userFileIdStr != "") {
            userFileIdList = userFileIdStr.split(",");
        }
        $T.mutliOperateProxy.toRecyclebin(userFoldIdList, userFileIdList);
    }
    this.onOffRecycleBin = function () {
        var userFoldIdStr = $("#userFoldIdStr").val();
        var userFileIdStr = $("#userFileIdStr").val();
        var userFoldIdList = null;
        var userFileIdList = null;
        if (userFoldIdStr != "") {
            userFoldIdList = userFoldIdStr.split(",");
        }
        if (userFileIdStr != "") {
            userFileIdList = userFileIdStr.split(",");
        }
        $T.mutliOperateProxy.offRecyclebin(userFoldIdList, userFileIdList);
    }
    this.onRemove = function () {
        var userFoldIdStr = $("#userFoldIdStr").val();
        var userFileIdStr = $("#userFileIdStr").val();
        var userFoldIdList = null;
        var userFileIdList = null;
        if (userFoldIdStr != "") {
            userFoldIdList = userFoldIdStr.split(",");
        }
        if (userFileIdStr != "") {
            userFileIdList = userFileIdStr.split(",");
        }
        $T.mutliOperateProxy.remove(userFoldIdList, userFileIdList);
    }
    this.onMoveto = function () {
        var userFoldIdStr = $("#userFoldIdStr").val();
        var userFileIdStr = $("#userFileIdStr").val();
        var userFoldIdList = null;
        var userFileIdList = null;
        if (userFoldIdStr != "") {
            userFoldIdList = userFoldIdStr.split(",");
        }
        if (userFileIdStr != "") {
            userFileIdList = userFileIdStr.split(",");
        }
        var userFoldParentId = $("#userFoldParentId").val();
        $T.mutliOperateProxy.moveTo(userFoldIdList, userFileIdList, userFoldParentId);
    }
}
$T.mutliOperateMediator = new MutliOperateMediator();