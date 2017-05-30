function LeftMediator() {

    this.init = function (view) {


        $("#left_button2").on("click", this.onClick2);
        $("#left_button3").on("click", this.onClick3);
        $("#left_button4").on("click", this.onClick4);
        $("#left_button5").on("click", this.onClick5);
    }
    // 注销方法
    this.dispose = function () {
        $("#left_button2").remove("click", this.onClick2);
        $("#left_button3").remove("click", this.onClick3);
        $("#left_button4").remove("click", this.onClick4);
        $("#left_button5").remove("click", this.onClick5);
    }
    // 关心消息数组
    this.listNotificationInterests = [];
    // 关心的消息处理
    this.handleNotification = function (data) {

    }

    this.onClick2 = function (event) {
        $T.viewManager.notifyObservers($T.viewManager.getNotification($T.notificationExt.CHANGE_BODY, "userfold"));
    }
    this.onClick3 = function (event) {
        $T.viewManager.notifyObservers($T.viewManager.getNotification($T.notificationExt.CHANGE_BODY, "updateUserfold"));
    }
    this.onClick4 = function (event) {
        $T.viewManager.notifyObservers($T.viewManager.getNotification($T.notificationExt.CHANGE_BODY, "userFile"));
    }
    this.onClick5 = function (event) {
        $T.viewManager.notifyObservers($T.viewManager.getNotification($T.notificationExt.CHANGE_BODY, "mutliOperate"));
    }
}
$T.leftMediator = new LeftMediator();