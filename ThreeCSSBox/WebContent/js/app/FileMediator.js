function FileMediator() {
    // 上传文件map，生命周期结束后移除
    this.uploadFileMap = [];
    // 等待md5生成数组
    this.waitMD5Array = [];
    // md5生成数组
    this.md5Array = [];
    // 等待校验数组
    this.waitCheckArray = [];
    // 校验数组
    this.checkArray = [];
    // 等待上传数组
    this.waitUploadArray = [];
    // 上传数组
    this.uploadArray = [];
    // 可以关闭的数组
    this.closeArray = [];
    this.init = function (view) {
        $T.fileSystemStatus.nowFoldId = $T.cookieParam.getCookieParam($T.cookieName.USER_FOLD_TOP_ID);
    }
    // 注销方法
    this.dispose = function () {

    }
    // 关心消息数组
    this.listNotificationInterests = [$T.notificationExt.UPLOAD_FILE, $T.notificationExt.MD5_CHECK_SUCCESS, $T.notificationExt.MD5_CHECK_FAIL, $T.notificationExt.UPLOAD_FILE_SUCCESS, $T.notificationExt.UPLOAD_FILE_FAIL];
    // 关心的消息处理
    this.handleNotification = function (data) {
        switch (data[0].name) {
            case $T.notificationExt.UPLOAD_FILE:
                this.upLoadFile(data[0].body);
                break;
            case $T.notificationExt.MD5_CHECK_SUCCESS:
                var result = data[0].body.result;
                var sendParam = data[0].body.sendParam;
                this.uploadFileMap[sendParam.uploadFileId].checkMD5Success(result);
                break;
            case $T.notificationExt.MD5_CHECK_FAIL:
                var result = data[0].body.result;
                var sendParam = data[0].body.sendParam;
                this.uploadFileMap[sendParam.uploadFileId].checkMD5Fail(result);
                break;
            case $T.notificationExt.UPLOAD_FILE_SUCCESS:
                var result = data[0].body.result;
                var sendParam = data[0].body.sendParam;
                this.uploadFileMap[sendParam.uploadFileId].uploadFileSuccess(result);
                break;
            case $T.notificationExt.UPLOAD_FILE_FAIL:
                var result = data[0].body.result;
                var sendParam = data[0].body.sendParam;
                this.uploadFileMap[sendParam.uploadFileId].uploadFileFail(result);
                break;
        }
    }
    this.advanceTime = function (passedTime) {
        if (this.md5Array.length < $T.fileConfig.MAX_MD5_MAKE_FILE_NUM) {
            for (var i = 0; i < this.waitMD5Array.length; i++) {
                var uploadFileObj = this.waitMD5Array.shift();
                if (uploadFileObj.isStop) {
                    uploadFileObj.stop();
                    i--;
                    // 放入可关闭数组
                    this.closeArray.push(uploadFileObj);
                    continue;
                }
                if (uploadFileObj.isCancel) {
                    this.removeUploadFile(uploadFileObj);
                    i--;
                    continue;
                }
                this.md5Array.push(uploadFileObj);
                uploadFileObj.startMD5Make();
                i--;
                if (this.md5Array.length == $T.fileConfig.MAX_MD5_MAKE_FILE_NUM) {
                    break;
                }

            }
        }
        for (var i = 0; i < this.md5Array.length; i++) {
            var uploadFileObj = this.md5Array[i];
            // 正在异步，不进行操作
            if (uploadFileObj.isLoad) {
                continue;
            }
            if (uploadFileObj.isCancel) {
                this.md5Array.splice(i, 1);
                this.removeUploadFile(uploadFileObj);
                i--;
                continue;
            }
            if (uploadFileObj.isStop) {
                this.md5Array.splice(i, 1);
                i--;
                if (uploadFileObj.status != $T.fileConfig.STATUS_READ_FILE_FAIL) {
                    uploadFileObj.stop();
                }
                // 放入可关闭数组
                this.closeArray.push(uploadFileObj);
                continue;
            }
            if (uploadFileObj.status == $T.fileConfig.STATUS_READ_FILE_FAIL) {
                // 失败，移出列表不用管了
                this.md5Array.splice(i, 1);
                i--;
                // 放入可关闭数组
                this.closeArray.push(uploadFileObj);
            } else if (uploadFileObj.status == $T.fileConfig.STATUS_MD5_SUCCESS) {
                this.waitCheckArray.push(uploadFileObj);
                uploadFileObj.enterMD5CheckArray();
                this.md5Array.splice(i, 1);
                i--;
            }
        }
        if (this.checkArray.length < $T.fileConfig.MAX_MD5_CHECK_FILE_NUM) {
            for (var i = 0; i < this.waitCheckArray.length; i++) {
                var uploadFileObj = this.waitCheckArray.shift();
                if (uploadFileObj.isStop) {
                    uploadFileObj.stop();
                    i--;
                    // 放入可关闭数组
                    this.closeArray.push(uploadFileObj);
                    continue;
                }
                if (uploadFileObj.isCancel) {
                    this.removeUploadFile(uploadFileObj);
                    i--;
                    continue;
                }
                this.checkArray.push(uploadFileObj);
                uploadFileObj.startMD5Check();
                i--;
                if (this.checkArray.length == $T.fileConfig.MAX_MD5_CHECK_FILE_NUM) {
                    break;
                }
            }
        }
        for (var i = 0; i < this.checkArray.length; i++) {
            var uploadFileObj = this.checkArray[i];
            if (uploadFileObj.isLoad) {
                continue;
            }
            if (uploadFileObj.isCancel) {
                this.checkArray.splice(i, 1);
                this.removeUploadFile(uploadFileObj);
                i--;
                continue;
            }
            if (uploadFileObj.isStop) {
                this.checkArray.splice(i, 1);
                i--;
                if (uploadFileObj.status != $T.fileConfig.STATUS_MD5_CHECK_FAIL && uploadFileObj.status != $T.fileConfig.STATUS_MD5_MOMENT_UPLOAD) {
                    uploadFileObj.stop();
                }
                // 放入可关闭数组
                this.closeArray.push(uploadFileObj);
                continue;
            }
            if (uploadFileObj.status == $T.fileConfig.STATUS_MD5_CHECK_SUCCESS) {
                this.waitUploadArray.push(uploadFileObj);
                uploadFileObj.enterWaitUploadArray();
                this.checkArray.splice(i, 1);
                i--;
            } else if (uploadFileObj.status == $T.fileConfig.STATUS_MD5_CHECK_FAIL) {
                this.checkArray.splice(i, 1);
                i--;
                // 放入可关闭数组
                this.closeArray.push(uploadFileObj);
            } else if (uploadFileObj.status == $T.fileConfig.STATUS_MD5_MOMENT_UPLOAD) {
                this.checkArray.splice(i, 1);
                i--;
                // 放入可关闭数组
                this.closeArray.push(uploadFileObj);
            }
        }
        if (this.uploadArray.length < $T.fileConfig.MAX_UPLOAD_FILE_NUM) {
            for (var i = 0; i < this.waitUploadArray.length; i++) {
                var uploadFileObj = this.waitUploadArray.shift();
                if (uploadFileObj.isStop) {
                    uploadFileObj.stop();
                    i--;
                    // 放入可关闭数组
                    this.closeArray.push(uploadFileObj);
                    continue;
                }
                if (uploadFileObj.isCancel) {
                    this.removeUploadFile(uploadFileObj);
                    i--;
                    continue;
                }
                this.uploadArray.push(uploadFileObj);
                uploadFileObj.startUploadFile();
                i--;
                if (this.uploadArray.length == $T.fileConfig.MAX_UPLOAD_FILE_NUM) {
                    break;
                }
            }
        }
        for (var i = 0; i < this.uploadArray.length; i++) {
            var uploadFileObj = this.uploadArray[i];
            if (uploadFileObj.isLoad) {
                continue;
            }
            if (uploadFileObj.isCancel) {
                this.uploadArray.splice(i, 1);
                this.removeUploadFile(uploadFileObj);
                i--;
                continue;
            }
            if (uploadFileObj.isStop) {
                this.uploadArray.splice(i, 1);
                i--;
                if (uploadFileObj.status != $T.fileConfig.STATUS_MD5_MOMENT_UPLOAD && uploadFileObj.status != $T.fileConfig.STATUS_RESPONSE_UPLOAD_FAIL && uploadFileObj.status != $T.fileConfig.STATUS_UPLOAD_SUCCESS) {
                    uploadFileObj.stop();
                }
                // 放入可关闭数组
                this.closeArray.push(uploadFileObj);
                continue;
            }
            if (uploadFileObj.status == $T.fileConfig.STATUS_RESPONSE_UPLOAD_FAIL) {
                this.uploadArray.splice(i, 1);
                i--;
                // 放入可关闭数组
                this.closeArray.push(uploadFileObj);
            } else if (uploadFileObj.status == $T.fileConfig.STATUS_MD5_MOMENT_UPLOAD) {
                this.uploadArray.splice(i, 1);
                i--;
                // 放入可关闭数组
                this.closeArray.push(uploadFileObj);
            } else if (uploadFileObj.status == $T.fileConfig.STATUS_UPLOAD_SUCCESS) {
                this.uploadArray.splice(i, 1);
                i--;
                // 放入可关闭数组
                this.closeArray.push(uploadFileObj);
            } else if (uploadFileObj.status == $T.fileConfig.STATUS_RESPONSE_UPLOAD_SUCCESS) {
                uploadFileObj.uploadFile(false);
            }
        }
        for (var i = 0; i < this.closeArray.length; i++) {
            var uploadFileObj = this.closeArray[i];
            if (uploadFileObj.isCancel) {
                this.closeArray.splice(i, 1);
                this.removeUploadFile(uploadFileObj);
                i--;
                continue;
            }
        }
    }
    this.upLoadFile = function (fileList) {
        if (fileList == null || fileList.length == 0) {
            return;
        }
        for (var i = 0; i < fileList.length; i++) {
            var file = fileList[i];
            var uploadFileObj = new UploadFileObj();
            uploadFileObj.init(file, $T.fileConfig.getIncrementId());
            $("#message").append(uploadFileObj.view);
            uploadFileObj.addListener();
            uploadFileObj.addEventListener($T.uploadEventType.ADD_WAIT_MD5_ARRAY, this.addWaitMd5Array, this);
            uploadFileObj.addEventListener($T.uploadEventType.ADD_MD5_CHECK_ARRAY_AND_LOAD, this.addMd5CheckArrayAndLoad, this);
            uploadFileObj.addEventListener($T.uploadEventType.ADD_MD5_CHECK_ARRAY, this.addMd5CheckArray, this);
            uploadFileObj.addEventListener($T.uploadEventType.ADD_WAIT_CHECK_ARRAY, this.addWaitCheckArray, this);
            uploadFileObj.addEventListener($T.uploadEventType.ADD_CHECK_ARRAY, this.addCheckArray, this);
            uploadFileObj.addEventListener($T.uploadEventType.ADD_WAIT_UPLOAD_ARRAY, this.addWaitUploadArray, this);
            uploadFileObj.addEventListener($T.uploadEventType.ADD_UPLOAD_ARRAY, this.addUploadArray, this);
            this.waitMD5Array.push(uploadFileObj);
            this.uploadFileMap[uploadFileObj.id] = uploadFileObj;
        }

    }
    this.removeUploadFile = function (uploadFileObj) {
        uploadFileObj.view.remove();
        delete this.uploadFileMap[uploadFileObj.id];
    }
    this.addWaitMd5Array = function (event) {
        var uploadFileObj = event.mTarget;
        this.removeCloseArray(uploadFileObj);
        this.waitMD5Array.push(uploadFileObj);
    }
    this.addMd5CheckArrayAndLoad = function (event) {
        var uploadFileObj = event.mTarget;
        this.removeCloseArray(uploadFileObj);
        this.md5Array.push(uploadFileObj);
        uploadFileObj.loadNext();

    }
    this.addMd5CheckArray = function (event) {
        var uploadFileObj = event.mTarget;
        this.removeCloseArray(uploadFileObj);
        this.md5Array.push(uploadFileObj);
    }
    this.addWaitCheckArray = function (event) {
        var uploadFileObj = event.mTarget;
        this.removeCloseArray(uploadFileObj);
        this.waitCheckArray.push(uploadFileObj);
    }
    this.addCheckArray = function (event) {
        var uploadFileObj = event.mTarget;
        this.removeCloseArray(uploadFileObj);
        this.checkArray.push(uploadFileObj);
    }
    this.addWaitUploadArray = function (event) {
        var uploadFileObj = event.mTarget;
        this.removeCloseArray(uploadFileObj);
        this.waitUploadArray.push(uploadFileObj);
    }
    this.addUploadArray = function (event) {
        var uploadFileObj = event.mTarget;
        this.removeCloseArray(uploadFileObj);
        this.uploadArray.push(uploadFileObj);

    }
    this.removeCloseArray = function (uploadFileObj) {
        for (var i = 0; i < this.closeArray.length; i++) {
            var uploadFileObj1 = this.closeArray[i];
            if (uploadFileObj1.id == uploadFileObj.id) {
                this.closeArray.splice(i, 1);
                break;
            }

        }
    }

}
$T.fileMediator = new FileMediator();