function BodyMediator() {

    this.init = function (view) {

        $T.moduleManager.loadModule("html/userfold.html", document.getElementById("body"), "bodyview", $T.userFoldMediator);
    }
    // 注销方法
    this.dispose = function () {

    }
    // 关心消息数组
    this.listNotificationInterests = [$T.notificationExt.CHANGE_BODY];
    // 关心的消息处理
    this.handleNotification = function (data) {
        switch (data[0].name) {
            case $T.notificationExt.CHANGE_BODY:
                if (data[0].body == "userfold") {
                    $T.moduleManager.loadModule("html/userfold.html", document.getElementById("body"), "bodyview", $T.userFoldMediator);
                } else if (data[0].body == "updateUserfold") {
                    $T.moduleManager.loadModule("html/updateUserfold.html", document.getElementById("body"), "bodyview", $T.updateUserFoldMediator);
                } else if (data[0].body == "userFile") {
                    $T.moduleManager.loadModule("html/userfile.html", document.getElementById("body"), "bodyview", $T.userFileMediator);
                } else if (data[0].body == "mutliOperate") {
                    $T.moduleManager.loadModule("html/mutlioperate.html", document.getElementById("body"), "bodyview", $T.mutliOperateMediator);
                }
                break;
        }
    }
}
$T.bodyMediator = new BodyMediator();