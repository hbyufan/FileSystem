function UploadEventType() {
    /** 加入等待md5队列 */
    this.ADD_WAIT_MD5_ARRAY = "addWaitMd5Array";

    this.ADD_MD5_CHECK_ARRAY_AND_LOAD = "addMd5CheckArrayAndLoad";
    this.ADD_MD5_CHECK_ARRAY = "addMd5CheckArray";
    this.ADD_WAIT_CHECK_ARRAY = "addWaitCheckArray";
    this.ADD_CHECK_ARRAY = "addCheckArray";
    this.ADD_WAIT_UPLOAD_ARRAY = "addWaitUploadArray";
    this.ADD_UPLOAD_ARRAY = "addUploadArray";
}
$T.uploadEventType = new UploadEventType();