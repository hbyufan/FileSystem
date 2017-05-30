function ByteUtil() {
    this.B_SIZE = 1024;
    this.KB_SIZE = 1048576;
    this.MB_SIZE = 1073741824;
    this.getSpeed = function (passedTime, bit) {
        var speed = bit / (passedTime * 0.001);
        if (speed < this.B_SIZE) {
            return speed.toFixed(2) + "B/S";
        } else if (speed < this.KB_SIZE) {
            return (speed / this.B_SIZE).toFixed(2) + "KB/S";
        } else {
            return (speed / this.KB_SIZE).toFixed(2) + "MB/S";
        }
    }
    this.getByte = function (bit) {
        if (bit < this.B_SIZE) {
            return bit + "B";
        } else if (bit < this.KB_SIZE) {
            return (bit / this.B_SIZE).toFixed(2) + "KB";
        } else if (bit < this.MB_SIZE) {
            return (bit / this.KB_SIZE).toFixed(2) + "MB";
        } else {
            return (bit / this.MB_SIZE).toFixed(2) + "GB";
        }
    }
}
$T.byteUtil = new ByteUtil();