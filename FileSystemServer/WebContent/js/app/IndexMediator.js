function IndexMediator() {

    this.init = function (view) {

        $("#uploadFiles").on("change", this.onChange);

        // 模块
        $T.moduleManager.loadModule("html/top.html", document.getElementById("index_top"), null, $T.topMediator);
        $T.moduleManager.loadModule("html/left.html", document.getElementById("index_left"), null, $T.leftMediator);
        $T.moduleManager.loadModule("html/body.html", document.getElementById("index_body"), null, $T.bodyMediator);
        $T.moduleManager.loadModule("html/bottom.html", document.getElementById("index_bottom"), null, $T.bottomMediator);
        $T.moduleManager.loadModule("html/file.html", document.getElementById("index_file"), null, $T.fileMediator);

    }
    // 注销方法
    this.dispose = function () {

        $("#uploadFiles").remove("change", this.onChange);
    }
    // 关心消息数组
    this.listNotificationInterests = [$T.notification.SEND_HTTP_START, $T.notification.SEND_HTTP_END, $T.notification.SYSTEM_ERROR];
    // 关心的消息处理
    this.handleNotification = function (data) {

    }
    this.advanceTime = function (passedTime) {

    }
    this.onChange = function (e) {
        if (!("FileReader" in window) || !("File" in window)) {
            alert("您的浏览器不支持html5，请使用google，firefox，ie10等浏览器");
            return;
        }
        var files = e.target.files;
        $T.viewManager.notifyObservers($T.viewManager.getNotification($T.notificationExt.UPLOAD_FILE, files));
    }
}
$T.indexMediator = new IndexMediator();