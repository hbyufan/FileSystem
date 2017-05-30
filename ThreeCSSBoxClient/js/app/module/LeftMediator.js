function LeftMediator() {

    this.init = function (view) {
        $("#left_allfile").on("click", this.onOpenAllFile);
        $("#left_recyclebin").on("click", this.onOpenRecyclebin);
        this.percent();

    }
    // 注销方法
    this.dispose = function () {
        $("#left_allfile").remove("click", this.onOpenAllFile);
        $("#left_recyclebin").remove("click", this.onOpenRecyclebin);
    }
    // 关心消息数组
    this.listNotificationInterests = [];
    // 关心的消息处理
    this.handleNotification = function (data) {

    }
    this.onOpenAllFile = function () {
        $T.fileSystemStatus.goTopFold();
        $T.viewManager.notifyObservers($T.viewManager.getNotification($T.notificationExt.CHANGE_BODY, "allFile"));
        $("#left_allfile").addClass("on");
        $("#left_recyclebin").removeClass("on");

    }
    this.onOpenRecyclebin = function () {
        $T.viewManager.notifyObservers($T.viewManager.getNotification($T.notificationExt.CHANGE_BODY, "recyclebin"));
        $("#left_allfile").removeClass("on");
        $("#left_recyclebin").addClass("on");
    }
    this.percent = function () {
        var cur = parseFloat($(".process .curPer").html());
        var all = parseFloat($(".process .allPer").html());
        var per = cur / all;
        $(".process>div").css({width: per * 100 + "%"})
    }


}
$T.leftMediator = new LeftMediator();