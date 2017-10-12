function UploadFileObj() {
    this.file;// 文件
    this.id;// 唯一id
    this.view;// 视图
    this.sparkMD5;// md5工具
    this.fileReader;// 读取文件工具
    this.currentChunk;// 当前块
    this.totalChunk;// 总块数
    this.md5;// md5值说明完成
    this.onLoadFunc;// 加载完成函数
    this.onErrorFunc;// 报错函数
    this.status;// 状态 1：开始,2:读取文件失败，3读取文件成功
    this.userFileId = null;// 当前上传得文件id
    this.resultData;// 返回数据（fileBasePos、uploadMaxLength）
    this.lastTime;// 上一次时间
    this.nowFoldId;
    this.nowFoldName;
    this.isStop = false;// 是否暂停
    this.isCancel = false;// 是否关闭
    this.isLoad = false;// 是否正在异步
}
UploadFileObj.prototype.createView = function (text) {
    var view = document.createElement("div");
    var body = '<span>' + this.file.name + '</span>&nbsp&nbsp&nbsp&nbsp<span>' + $T.byteUtil.getByte(this.file.size) + '</span>&nbsp&nbsp&nbsp&nbsp<span>' + this.nowFoldName + '</span>&nbsp&nbsp&nbsp&nbsp<span id="' + this.id + '_text">' + text + '</span>&nbsp&nbsp&nbsp&nbsp<input id="' + this.id + '_stop" type="button" value="暂停"/><input id="' + this.id + '_start" type="button" value="开始"/><input id="' + this.id + '_retry" type="button" value="重试"/><input id="' + this.id + '_cancel" type="button" value="取消"/></br>';
    view.innerHTML = body;
    this.view = $(view);
}
UploadFileObj.prototype.updateView = function (text) {
    $("#" + this.id + "_text").text(text);
}
UploadFileObj.prototype.init = function (file, id) {
    this.file = file;
    this.id = id;
    this.sparkMD5 = new SparkMD5.ArrayBuffer();
    this.fileReader = new FileReader();
    this.currentChunk = 0;
    this.totalChunk = Math.ceil(this.file.size / $T.fileConfig.READ_CHUNK_SIZE);
    this.status = $T.fileConfig.STATUS_INIT;
    this.nowFoldId = $T.fileSystemStatus.nowFoldId;
    this.nowFoldName = $T.fileSystemStatus.nowFoldName;
    this.createView("等待生成md5");
    EventDispatcher.apply(this);
}
UploadFileObj.prototype.addListener = function () {
    this.onStartClickListener(this, this.onStartClick);
    this.onStopClickListener(this, this.onStopClick);
    this.onCancelClickListener(this, this.onCancelClick);
    this.onRetryClickListener(this, this.onRetryClick);
    $("#" + this.id + "_start").hide();
    $("#" + this.id + "_retry").hide();
}
UploadFileObj.prototype.onStartClickListener = function (uploadFileObj, call) {
    this.onLoadFunc = function (event) {
        call.call(uploadFileObj, event);
    }
    $("#" + this.id + "_start").on("click", this.onLoadFunc);
}
UploadFileObj.prototype.onStopClickListener = function (uploadFileObj, call) {
    this.onLoadFunc = function (event) {
        call.call(uploadFileObj, event);
    }
    $("#" + this.id + "_stop").on("click", this.onLoadFunc);
}
UploadFileObj.prototype.onCancelClickListener = function (uploadFileObj, call) {
    this.onLoadFunc = function (event) {
        call.call(uploadFileObj, event);
    }
    $("#" + this.id + "_cancel").on("click", this.onLoadFunc);
}
UploadFileObj.prototype.onRetryClickListener = function (uploadFileObj, call) {
    this.onLoadFunc = function (event) {
        call.call(uploadFileObj, event);
    }
    $("#" + this.id + "_retry").on("click", this.onLoadFunc);
}
UploadFileObj.prototype.onStartClick = function () {
    if (this.isStop) {
        this.isStop = false;
        $("#" + this.id + "_start").hide();
        $("#" + this.id + "_stop").show();
        if (this.status == $T.fileConfig.STATUS_INIT) {
            this.dispatchEventWith($T.uploadEventType.ADD_WAIT_MD5_ARRAY);
        } else if (this.status == $T.fileConfig.STATUS_START_MD5_CHECK) {
            this.dispatchEventWith($T.uploadEventType.ADD_MD5_CHECK_ARRAY_AND_LOAD);
        } else if (this.status == $T.fileConfig.STATUS_MD5_SUCCESS) {
            this.dispatchEventWith($T.uploadEventType.ADD_MD5_CHECK_ARRAY);
        } else if (this.status == $T.fileConfig.STATUS_ENTER_MD5CHECK_ARRAY) {
            this.dispatchEventWith($T.uploadEventType.ADD_WAIT_CHECK_ARRAY);
        } else if (this.status == $T.fileConfig.STATUS_MD5_CHECK_SUCCESS) {
            this.dispatchEventWith($T.uploadEventType.ADD_CHECK_ARRAY);
        } else if (this.status == $T.fileConfig.STATUS_ENTER_WAIT_UPLOAD_ARRAY) {
            this.dispatchEventWith($T.uploadEventType.ADD_WAIT_UPLOAD_ARRAY);
        } else if (this.status == $T.fileConfig.STATUS_RESPONSE_UPLOAD_SUCCESS) {
            this.dispatchEventWith($T.uploadEventType.ADD_UPLOAD_ARRAY);
        }

    }
}
UploadFileObj.prototype.onStopClick = function () {
    this.isStop = true;
}
UploadFileObj.prototype.onCancelClick = function () {
    this.isCancel = true;
}
UploadFileObj.prototype.onRetryClick = function () {

}
UploadFileObj.prototype.stop = function () {
    $("#" + this.id + "_start").show();
    $("#" + this.id + "_stop").hide();
}
UploadFileObj.prototype.clear = function () {
    this.file = null;
    this.id = null;
    this.view = null;
    this.sparkMD5 = null;
    this.fileReader.removeEventListener("load", this.onLoadFunc);
    this.fileReader.removeEventListener("error", this.onErrorFunc);
    this.fileReader = null;
}
UploadFileObj.prototype.startMD5Make = function () {
    this.onLoadListener(this, this.onLoad);
    this.onErrorListener(this, this.onError);
    this.loadNext();
    this.updateView("开始生成md5");
    this.status = $T.fileConfig.STATUS_START_MD5_CHECK;
}
UploadFileObj.prototype.onLoadListener = function (uploadFileObj, call) {
    this.onLoadFunc = function (event) {
        call.call(uploadFileObj, event);
    }
    this.fileReader.addEventListener("load", this.onLoadFunc);
}
UploadFileObj.prototype.onErrorListener = function (uploadFileObj, call) {
    this.onErrorFunc = function (event) {
        call.call(uploadFileObj, event);
    }
    this.fileReader.addEventListener("error", this.onErrorFunc);
}
UploadFileObj.prototype.loadNext = function () {
    var startPos = this.currentChunk * $T.fileConfig.READ_CHUNK_SIZE;
    var endPos = startPos + $T.fileConfig.READ_CHUNK_SIZE >= this.file.size ? this.file.size : startPos + $T.fileConfig.READ_CHUNK_SIZE;
    this.fileReader.readAsArrayBuffer($T.fileConfig.SLICE.call(this.file, startPos, endPos));
    this.isLoad = true;
}
UploadFileObj.prototype.onLoad = function (event) {
    this.isLoad = false;
    this.sparkMD5.append(this.fileReader.result);
    this.currentChunk++;
    if (this.currentChunk < this.totalChunk) {
        if (this.isStop || this.isCancel) {
            return;
        }
        this.loadNext();
        this.updateView("开始生成md5，加载：" + parseInt(this.currentChunk / this.totalChunk * 100) + "%");
    } else {
        this.md5 = this.sparkMD5.end();
        this.updateView("生成md5成功，md5：" + this.md5);
        this.status = $T.fileConfig.STATUS_MD5_SUCCESS;
    }
}
UploadFileObj.prototype.onError = function (event) {
    this.isLoad = false;
    this.updateView("生成md5失败，读取文件异常");
    this.status = $T.fileConfig.STATUS_READ_FILE_FAIL;
    $("#" + this.id + "_start").hide();
    $("#" + this.id + "_stop").hide();
}
UploadFileObj.prototype.enterMD5CheckArray = function () {
    this.status = $T.fileConfig.STATUS_ENTER_MD5CHECK_ARRAY;
    this.updateView("加入等待校验列表");
}
UploadFileObj.prototype.startMD5Check = function () {
    this.updateView("开始md5校验");
    this.isLoad = true;
    this.status = $T.fileConfig.STATUS_START_MD5CHECK;
    $T.uploadFileProxy.checkMD5(this.id, this.md5, this.file.name, this.nowFoldId, this.file.size, this.userFileId);

}
UploadFileObj.prototype.checkMD5Success = function (result) {
    this.isLoad = false;
    if (result.result == 1) {
        // 秒传
        this.status = $T.fileConfig.STATUS_MD5_MOMENT_UPLOAD;
        this.updateView("秒传");
        $("#" + this.id + "_start").hide();
        $("#" + this.id + "_stop").hide();
    } else {
        // 可以上传
        this.status = $T.fileConfig.STATUS_MD5_CHECK_SUCCESS;
        this.resultData = result;
        this.userFileId = result.userFileId;
        this.updateView("md5校验成功");
    }

}
UploadFileObj.prototype.checkMD5Fail = function (result) {
    this.isLoad = false;
    this.status = $T.fileConfig.STATUS_MD5_CHECK_FAIL;
    this.updateView("md5校验失败");
    $("#" + this.id + "_start").hide();
    $("#" + this.id + "_stop").hide();
}
UploadFileObj.prototype.enterWaitUploadArray = function () {
    this.status = $T.fileConfig.STATUS_ENTER_WAIT_UPLOAD_ARRAY;
    this.updateView("加入等待上传列表");
}
UploadFileObj.prototype.startUploadFile = function () {
    this.updateView("开始上传文件");
    this.lastTime = new Date().getTime();
    this.uploadFile(true);

}
UploadFileObj.prototype.uploadFile = function (isStart) {
    if (!isStart) {
        var nowTime = new Date().getTime();
        if ((nowTime - this.lastTime) < this.resultData.waitTime) {
            return;
        }
    }
    if (this.resultData.fileBasePos == this.file.size) {
        // 上传已经完成了，服务器出问题了
        this.status = $T.fileConfig.STATUS_UPLOAD_SUCCESS;
        this.updateView("上传完成");
        $("#" + this.id + "_start").hide();
        $("#" + this.id + "_stop").hide();
        return;
    }
    var endPos = this.resultData.fileBasePos + this.resultData.uploadMaxLength >= this.file.size ? this.file.size : this.resultData.fileBasePos + this.resultData.uploadMaxLength;
    var filePart = $T.fileConfig.SLICE.call(this.file, this.resultData.fileBasePos, endPos);
    this.status = $T.fileConfig.STATUS_REQUEST_UPLOAD;
    this.isLoad = true;
    $T.uploadFileProxy.uploadFile(this.id, this.userFileId, this.resultData.fileBasePos, (endPos - this.resultData.fileBasePos), [filePart]);

}
UploadFileObj.prototype.uploadFileSuccess = function (result) {
    this.isLoad = false;
    if (result.result == 1) {
        // 秒传
        this.status = $T.fileConfig.STATUS_MD5_MOMENT_UPLOAD;
        this.updateView("秒传");
        $("#" + this.id + "_start").hide();
        $("#" + this.id + "_stop").hide();
    } else if (result.result == 2) {
        var endPos = this.resultData.fileBasePos + this.resultData.uploadMaxLength >= this.file.size ? this.file.size : this.resultData.fileBasePos + this.resultData.uploadMaxLength;
        // 可以上传
        this.status = $T.fileConfig.STATUS_RESPONSE_UPLOAD_SUCCESS;
        var nowTime = new Date().getTime();
        var passedTime = nowTime - this.lastTime;
        this.lastTime = nowTime;
        this.updateView("上传速度：" + $T.byteUtil.getSpeed(passedTime, (endPos - this.resultData.fileBasePos)));
        this.resultData = result;
    } else {
        // 秒传
        this.status = $T.fileConfig.STATUS_UPLOAD_SUCCESS;
        this.updateView("上传完成");
        $("#" + this.id + "_start").hide();
        $("#" + this.id + "_stop").hide();
    }
}
UploadFileObj.prototype.uploadFileFail = function (result) {
    this.isLoad = false;
    this.status = $T.fileConfig.STATUS_RESPONSE_UPLOAD_FAIL;
    this.updateView("md5校验失败");
    $("#" + this.id + "_start").hide();
    $("#" + this.id + "_stop").hide();
}