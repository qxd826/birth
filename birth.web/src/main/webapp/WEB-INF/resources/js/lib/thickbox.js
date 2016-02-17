;
(function ($) {
    $.extend({
        artwl_bind: function (options) {
            options = $.extend({
                title: "",
                content: "",
                width: "500px"
            }, options);
            var mask = '<div id="artwl_mask"></div>';
            var boxcontain = '<div id="artwl_boxcontain">\
                                <a id="artwl_close" class="artwl_close" href="javascript:void(0);" title="Close"></a>\
                                    <div id="artwl_showbox">\
                                        <div id="artwl_title">\
                                            <h2>\
                                                正在加载，请稍后...</h2>\
                                        </div>\
                                        <div id="artwl_message">\
                                            正在加载，请稍后...<br />\
                                        </div>\
                                    </div>\
                                </div>';
            var cssCode = 'html, body, h1, h2, h3, h4, h5{margin: 0px;padding: 0px;}\
#artwl_mask{background-color: #000;position: fixed;top: 0px;left: 0px;width: 100%;height: 100%;opacity: 0.5;filter: alpha(opacity=50);display: none;}\
#artwl_boxcontain{margin: 0 auto;position: absolute;z-index: 2;line-height: 28px;display: none;}\
#artwl_showbox{padding: 10px;background: #FFF;margin: 20px;min-width:300px;min-height:150px;}\
#artwl_title{position: relative;height: 27px;color:#444;font-size:14px;text-align:center}\
#artwl_close{position: absolute;cursor: pointer;outline: none;top: 30px;right: 28px;z-index: 4;width: 10px;height: 10px;overflow: hidden;background-image: url(../resources/images/close_btn.png);_background: none;}\
#artwl_message{padding: 10px 0px;overflow: hidden;line-height: 19px;}';
            if ($("#artwl_mask").length == 0) {
                $("body").append(mask + boxcontain);
                $("head").append("<style type='text/css'>" + cssCode + "</style>");
            }
            else {
                $("#artwl_boxcontain").remove();
                $("body").append(boxcontain);
            }

            if (options.title != "") {
                $("#artwl_title").html(options.title);
            }
            if (options.content != "") {
                $("#artwl_message").html(options.content);
            }

            if ($(window).width() > parseInt(options.width)) {
                $("#artwl_boxcontain").css("width", options.width);
            }
            else {
                $("#artwl_boxcontain").css("width", $(window).width() - 40 + "px");
            }
            $(options.showbtnid).click(function () {
                var height = $("#artwl_boxcontain").height();
                var width = $("#artwl_boxcontain").width();
//                $("#artwl_mask").css("height", $("body").height() > $(window).height() ? $("body").height() : $(window).height() + "px").show();
                $("#artwl_mask").show();
                $("#artwl_boxcontain").css("top", ($(window).height() - height) / 2 + $(document).scrollTop()).css("left", ($(window).width() - width) / 2).show();
//            if ($.browser.msie && $.browser.version.substr(0, 1) < 7) {
//                width = $(window).width() > 600 ? 600 : $(window).width() - 40;
//                $("#artwl_boxcontain").css("width", width + "px").css("top", ($(window).height() - height) / 2).css("left", ($(window).width() - width) / 2).show();
//                $("#artwl_mask").css("width", $(window).width() + "px").css("height", $(window).height() + "px").css("background", "#888");
//                $("#artwl_close").css("top", "30px").css("right", "30px").css("font-size", "20px").text("关闭");
//            }
            });
            $(".artwl_close_cal").click(function () {
                $("#artwl_mask").hide();
                $("#artwl_boxcontain").hide();
            });

        },
        artwl_close: function (options) {
            options = $.extend({
                callback: null
            }, options);
            $("#artwl_mask").hide();
            $("#artwl_boxcontain").hide();
            if (options.callback != null) {
                options.callback();
            }
        }
    });
})(jQuery);
