function ImagePreviewMediator() {

    this.init = function (view, userFileId) {
        var result = this.initView(userFileId);
        if (!result) {
            return;
        }
        var w1 = $(window).width();
        var h1 = $(window).height();
        layer.open({
            type: 1,
            skin: "layer1_P",
            content: $('.yunPopBg'),
            area: [w1 + "px", h1 + "px"],
            offset: ['0', '0'],
            success: function (index, layero) {
                var self = this;
                $(window).resize(function () {
                    var w1 = $(window).width();
                    var h1 = $(window).height();
                    $(".layer1_P").css({width: w1, height: h1});
                    var windowH = $(window).height();
                    var sw = window.screen.width * 0.9;
                    var wrapH = windowH * 0.65;
                    $(".photoBox ").css("margin-left", -sw / 2);
                    $(".yunPhotoWrap").css("height", wrapH);
                    $(".photoBox,.photoBox >div").css({
                        "height": wrapH,
                        "width": sw
                    });
                    $(".photoBox").css({
                        "height": wrapH,
                        "width": sw,
                        "margin-top": -wrapH / 2 + "px"
                    });
                    for (var i = 0; i < $(".photoBox").length; i++) {
                        var imgW = $(".photoBox").eq(i).find("img").width();
                        var imgH = $(".photoBox").eq(i).find("img").height();
                        var outerW = $(".photoBox").width();
                        var outerH = $(".photoBox").height();
                        if (imgW >= imgH) {
                            if (outerW > outerH) {
                                $(".photoBox").eq(i).find("img").width(outerH);
                            } else {
                                $(".photoBox").eq(i).find("img").width(outerW);
                            }
                        } else {
                            if (outerW > outerH) {
                                $(".photoBox").eq(i).find("img").height(outerH);
                            } else {
                                $(".photoBox").eq(i).find("img").height(outerW);
                            }
                        }

                    }
                    $(".photoBox").css("display:none");
                })
                var windowH = $(window).height();
                var sw = window.screen.width * 0.9;
                var wrapH = windowH * 0.65;
                $(".photoBox ").css("margin-left", -sw / 2);
                $(".yunPhotoWrap").css("height", wrapH);
                $(".photoBox,.photoBox >div").css({
                    "height": wrapH,
                    "width": sw
                });
                $(".photoBox").css({
                    "height": wrapH,
                    "width": sw,
                    "margin-top": -wrapH / 2 + "px"
                });
                for (var i = 0; i < $(".photoBox").length; i++) {
                    var imgW = $(".photoBox").eq(i).find("img").width();
                    var imgH = $(".photoBox").eq(i).find("img").height();
                    var outerW = $(".photoBox").width();
                    var outerH = $(".photoBox").height();
                    if (imgW >= imgH) {
                        if (outerW > outerH) {
                            $(".photoBox").eq(i).find("img").width(outerH);
                        } else {
                            $(".photoBox").eq(i).find("img").width(outerW);
                        }
                    } else {
                        if (outerW > outerH) {
                            $(".photoBox").eq(i).find("img").height(outerH);
                        } else {
                            $(".photoBox").eq(i).find("img").height(outerW);
                        }
                    }

                }
                $(".photoBox").css("display:none");
                //点击剪头切换图片
                var len = $(".photoBox").length;
                $(".leftBtn,#yunLeft").click(function () {
                    console.log(1)
                    num = 0;
                    var index = $(".show").index();

                    if (index == 0) {
                        alert("已经是第一张");
                    }
                    else {
                        $(".photoBox").eq(index - 1).addClass("show").siblings().removeClass("show");

                    }
                    $(".photoBox.show>img").removeAttr("style"); //恢复原状
                });
                $(".rightBtn,#yunRight").click(function () {
                    num = 0;
                    var index = $(".show").index();
                    if (index == len - 1) {
                        alert("已经是最后一张");
                    }
                    else {
                        $(".photoBox").eq(index + 1).addClass("show").siblings().removeClass("show");
                    }
                    $(".photoBox.show>img").removeAttr("style"); //恢复原状

                });

                //关闭按钮
                $(".yunCloseBtn").on("click", function () {
                    $(".layer1_P .layui-layer-close1").trigger("click")
                });

                //底部操作按钮
                //左旋转
                var num = 0;
                var arr = [];
                $(".li1").on("click", function () {
                    var x = $(".photoBox.show").index();
                    if (arr[x] != undefined) {
                        num = arr[x] - 90
                    } else {
                        num -= 90;
                    }

                    arr[x] = num;

                    $(".photoBox.show .img").rotate(num);
                });
                //右旋转
                $(".li2").on("click", function () {
                    var x = $(".photoBox.show").index();
                    if (arr[x] != undefined) {
                        num = arr[x] + 90
                    } else {
                        num += 90;
                    }
                    arr[x] = num;
                    $(".photoBox.show .img").rotate(num);

                });
                //删除
                $(".li4").click(function () {
                    layer.confirm('<br>确认要把所选文件放入回收站吗？<br>删除的文件可在10天内通过回收站还原', {
                        title: "确认删除",
                        skin: "layer2_P",
                        type: 1,
                        area: ['460px', 'auto'],
                        btn1: function (index, layero) {
                            layer.close(index);
                            //按钮确定的回调
                            $(".photoBox.show").remove().siblings().removeClass("hide").addClass("show");
                        },
                        btn2: function (index, layero) {
                            //按钮取消的回调
                            alert("点击了取消");
                            //return false //开启该代码可禁止点击该按钮关闭
                        }
                    })
                });

                //左右剪头hover效果
                $(".leftBtn").hover(function () {
                    $("#yunLeft").css("background", "url(images/yunPrev_hover.png) no-repeat");
                }, function () {
                    $("#yunLeft").css("background", "url(images/yunPrev.png) no-repeat");
                });
                $(".rightBtn").hover(function () {
                    $("#yunRight").css("background", "url(images/yunNext_hover.png) no-repeat");
                }, function () {
                    $("#yunRight").css("background", "url(images/yunNext.png) no-repeat");
                })

                //比较函数
            }
        })
    }
    // 注销方法
    this.dispose = function () {

    }
    // 关心消息数组
    this.listNotificationInterests = [];
    // 关心的消息处理
    this.handleNotification = function (data) {

    }
    this.initView = function (userFileId) {
        if ($T.userFoldProxy.nowFoldChildren == null || $T.userFoldProxy.nowFoldChildren.userFileList == null) {
            return false;
        }
        var j = 0;
        for (var i = 0; i < $T.userFoldProxy.nowFoldChildren.userFileList.length; i++) {
            var userFile = $T.userFoldProxy.nowFoldChildren.userFileList[i];
            if (!$T.postfixUtil.isImage(userFile.userFileName)) {
                continue;
            }
            j++;
            var view = this.createImageView(userFile);
            view.addClass("photoBox");
            if (userFile.userFileId == userFileId) {
                view.addClass("show");
            }
            $("#image_preview_container").append(view);
        }
        if (j > 0) {
            return true;
        } else {
            return false;
        }

    }
    this.createImageView = function (userFile) {
        var view = document.createElement("div");
        var body =
            '<div>' +
            '<img src="' + $T.userFileProxy.getUserFileImage(userFile.userFileId) + '" class="img" num="0"/>' +

            '<span class="leftBtn "></span>' +
            '<span class="rightBtn "></span>' +
            '</div>';
        view.innerHTML = body;
        return $(view);
    }

}
$T.imagePreviewMediator = new ImagePreviewMediator();